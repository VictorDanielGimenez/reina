package modeloDAO;

import modeloDTO.SucursalDTO;
import programas.conexion;
import interfaces.SucursalINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SucursalDAO implements SucursalINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public SucursalDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(SucursalDTO dto) {
        try {
            sql = "INSERT INTO sucursal(cod_sucursal, nro_sucursal, descripcion, direccion, cod_empresa, cod_ciudad)\n"
                    + "VALUES ((select coalesce (max(cod_sucursal),0)+1 from sucursal), ?, ?, ?, ?, ?);";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getNro_sucursal());
            ps.setString(2, dto.getDescripcion().toUpperCase());
            ps.setString(3, dto.getDireccion().toUpperCase());
            ps.setInt(4, dto.getCod_empresa());
            ps.setInt(5, dto.getCod_ciudad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(SucursalDTO dto) {
        try {
            sql = "UPDATE sucursal\n"
                    + "SET nro_sucursal=?, descripcion=?, direccion=?, cod_empresa=?, cod_ciudad=?\n"
                    + "WHERE cod_sucursal=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getNro_sucursal());
            ps.setString(2, dto.getDescripcion().toUpperCase());
            ps.setString(3, dto.getDireccion().toUpperCase());
            ps.setInt(4, dto.getCod_empresa());
            ps.setInt(5, dto.getCod_ciudad());
            ps.setInt(6, dto.getCod_sucursal());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(SucursalDTO dto) {
        try {
            sql = "DELETE FROM sucursal\n"
                    + " WHERE cod_sucursal=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_sucursal());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<SucursalDTO> seleccionarTodos() {
        try {
            List<SucursalDTO> lista;
            SucursalDTO dto;
            sql = "SELECT s.cod_sucursal, s.nro_sucursal, s.descripcion, s.direccion,\n"
                    + "e.cod_empresa, e.descripcion As empresa, c.cod_ciudad, c.descripcion AS ciudad\n"
                    + "FROM sucursal s\n"
                    + "INNER JOIN empresa e ON s.cod_empresa=e.cod_empresa\n"
                    + "INNER JOIN ciudad c ON s.cod_ciudad=c.cod_ciudad\n"
                    + "ORDER BY cod_sucursal ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new SucursalDTO();
                dto.setCod_sucursal(rs.getInt("cod_sucursal"));
                dto.setNro_sucursal(rs.getString("nro_sucursal"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setCod_empresa(rs.getInt("cod_empresa"));
                dto.setDescripcion_empresa(rs.getString("empresa"));
                dto.setCod_ciudad(rs.getInt("cod_ciudad"));
                dto.setDescripcion_ciudad(rs.getString("ciudad"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<SucursalDTO> seleccionarSegunFiltro(SucursalDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SucursalDTO seleccionarSegunId(SucursalDTO dto) {
        try {
            SucursalDTO dtoLocal = null;
            sql = "SELECT cod_sucursal, nro_sucursal, descripcion, direccion, cod_empresa, cod_ciudad\n"
                    + "FROM sucursal WHERE cod_sucursal=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_sucursal());
            rs = ps.executeQuery();
            System.out.println(rs);
            if (rs.next()) {
                dtoLocal = new SucursalDTO();
                dtoLocal.setCod_sucursal(rs.getInt("cod_sucursal"));
                dtoLocal.setNro_sucursal(rs.getString("nro_sucursal"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                dtoLocal.setDireccion(rs.getString("direccion"));
                dtoLocal.setCod_empresa(rs.getInt("cod_empresa"));
                dtoLocal.setCod_ciudad(rs.getInt("cod_ciudad"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }
}
