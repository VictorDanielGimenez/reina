package modeloDAO;

import modeloDTO.EmpleadoDTO;
import programas.conexion;
import programas.Genericos_fecha;
import interfaces.EmpleadoINT;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO implements EmpleadoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;
    programas.Genericos_fecha util = new Genericos_fecha();
    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat fecha2 = new SimpleDateFormat("yyyy/MM/dd");
    DecimalFormat formato = new DecimalFormat("###,###,###");

    public EmpleadoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(EmpleadoDTO dto) {
        try {
            System.out.println("llega al agregar");
            sql = "INSERT INTO empleado(cod_empleado, emp_ci, nombre, apellido, direccion, telefono, cod_ciudad, fecha_nac, cod_nacionalidad)\n"
                    + "VALUES ((select coalesce (max(cod_empleado),0)+1 from empleado), ?, ?, ?, ?, ?, ?, ?, ?);";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
//            ps.setInt(1, dto.getCod_empleado());
            ps.setString(1, dto.getEmp_ci().trim());
            ps.setString(2, dto.getNombre().toUpperCase().trim());
            ps.setString(3, dto.getApellido().toUpperCase().trim());
            ps.setString(4, dto.getDireccion().toUpperCase().trim());
            ps.setString(5, dto.getTelefono().toUpperCase().trim());
            ps.setInt(6, dto.getCod_ciudad());
            ps.setDate(7, Date.valueOf(dto.getFecha_nac().trim()));
            ps.setInt(8, dto.getCod_nacionalidad());
//            System.out.println("insercion: "+ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error al agregar: " + msg);
            return false;
        }
    }

    @Override
    public Boolean modificar(EmpleadoDTO dto) {
        try {
            sql = "UPDATE empleado\n"
                    + "SET emp_ci=?, nombre=?, apellido=?, direccion=?, telefono=?, cod_ciudad=?, cod_nacionalidad=?, fecha_nac=?\n"
                    + "WHERE cod_empleado=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setString(1, dto.getEmp_ci().trim());
            ps.setString(2, dto.getNombre().toUpperCase().trim());
            ps.setString(3, dto.getApellido().toUpperCase().trim());
            ps.setString(4, dto.getDireccion().toUpperCase().trim());
            ps.setString(5, dto.getTelefono().toUpperCase().trim());
            ps.setInt(6, dto.getCod_ciudad());
            ps.setInt(7, dto.getCod_nacionalidad());
            ps.setDate(8, Date.valueOf(dto.getFecha_nac().trim()));
            ps.setInt(9, dto.getCod_empleado());
//            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error al modificar: " + msg);
            return false;
        }
    }

    @Override
    public Boolean eliminar(EmpleadoDTO dto) {
        try {
            sql = "DELETE FROM empleado\n"
                    + " WHERE cod_empleado=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getCod_empleado());
//            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<EmpleadoDTO> seleccionarTodos() {
        try {
            List<EmpleadoDTO> lista;
            EmpleadoDTO dto;
            sql = "SELECT e.cod_empleado, e.emp_ci, e.nombre, e.apellido, e.direccion, e.telefono, e.fecha_nac,\n"
                    + "c.cod_ciudad, c.descripcion AS ciudad, n.cod_nacionalidad, n.descripcion As nacionalidad \n"
                    + "FROM empleado e\n"
                    + "INNER JOIN ciudad c ON e.cod_ciudad=c.cod_ciudad\n"
                    + "INNER JOIN nacionalidad n ON e.cod_nacionalidad=n.cod_nacionalidad\n"
                    + "ORDER BY cod_empleado ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new EmpleadoDTO();
                dto.setCod_empleado(rs.getInt("cod_empleado"));
                dto.setEmp_ci(rs.getString("emp_ci"));
                dto.setNombre(rs.getString("nombre"));
                dto.setApellido(rs.getString("apellido"));
                dto.setFecha_nac(fecha.format(Date.valueOf(rs.getString("fecha_nac"))));
                dto.setTelefono(rs.getString("telefono"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setCod_ciudad(rs.getInt("cod_ciudad"));
                dto.setCiudad_descripcion(rs.getString("ciudad"));
                dto.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
                dto.setNacionalidad_descripcion(rs.getString("nacionalidad"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<EmpleadoDTO> seleccionarSegunFiltro(EmpleadoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmpleadoDTO seleccionarSegunId(EmpleadoDTO dto) {
        try {
            EmpleadoDTO dtoLocal = null;
            sql = "SELECT cod_empleado, "
                    + "emp_ci, "
                    + "nombre, "
                    + "apellido, "
                    + "direccion, "
                    + "telefono, "
                    + "cod_ciudad, "
                    + "cod_nacionalidad, "
                    + "fecha_nac\n"
                    + "FROM empleado \n"
                    + "WHERE cod_empleado= ?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_empleado());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new EmpleadoDTO();
                dtoLocal.setCod_empleado(rs.getInt("cod_empleado"));
                dtoLocal.setEmp_ci(rs.getString("emp_ci"));
                dtoLocal.setNombre(rs.getString("nombre"));
                dtoLocal.setApellido(rs.getString("apellido"));
                dtoLocal.setFecha_nac(rs.getString("fecha_nac"));
                dtoLocal.setTelefono(rs.getString("telefono"));
                dtoLocal.setDireccion(rs.getString("direccion"));
                dtoLocal.setCod_ciudad(rs.getInt("cod_ciudad"));
                dtoLocal.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    public List<EmpleadoDTO> SeleccionarEmpleado() {
        try {
            List<EmpleadoDTO> lista;
            EmpleadoDTO dto;
            sql = "SELECT e.cod_empleado, e.emp_ci, e.nombre, e.apellido, e.direccion, e.telefono, e.fecha_nac,\n"
                    + "c.cod_ciudad, c.descripcion AS ciudad, n.cod_nacionalidad, n.descripcion As nacionalidad \n"
                    + "FROM empleado e\n"
                    + "INNER JOIN ciudad c ON e.cod_ciudad=c.cod_ciudad\n"
                    + "INNER JOIN nacionalidad n ON e.cod_nacionalidad=n.cod_nacionalidad\n"
                    + "ORDER BY cod_empleado ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new EmpleadoDTO();
                dto.setCod_empleado(rs.getInt("cod_empleado"));
                dto.setEmp_ci(rs.getString("emp_ci"));
                dto.setNombre(rs.getString("nombre"));
                dto.setApellido(rs.getString("apellido"));
                dto.setFecha_nac(fecha.format(Date.valueOf(rs.getString("fecha_nac"))));
                dto.setTelefono(rs.getString("telefono"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setCod_ciudad(rs.getInt("cod_ciudad"));
                dto.setCiudad_descripcion(rs.getString("ciudad"));
                dto.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
                dto.setNacionalidad_descripcion(rs.getString("nacionalidad"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }
}
