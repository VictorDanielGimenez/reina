package modeloDAO;

import modeloDTO.EmpresaDTO;
import programas.conexion;
import interfaces.EmpresaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO implements EmpresaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public EmpresaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(EmpresaDTO dto) {
        try {
            sql = "INSERT INTO empresa(cod_empresa, telefono, descripcion, direccion, ruc, cod_ciudad)\n"
                    + "    VALUES ((select coalesce (max(cod_empresa),0)+1 from empresa), ?, ?, ?, ?, ?);";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_empresa());
            ps.setString(1, dto.getTelefono().toUpperCase());
            ps.setString(2, dto.getDescripcion().toUpperCase());
            ps.setString(3, dto.getDireccion().toUpperCase());
            ps.setString(4, dto.getRuc());
            ps.setInt(5, dto.getCod_ciudad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(EmpresaDTO dto) {
        try {
            sql = "UPDATE empresa\n"
                    + "SET telefono=?, descripcion=?, direccion=?, ruc=?, cod_ciudad=?\n"
                    + "WHERE cod_empresa=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setString(1, dto.getTelefono().toUpperCase());
            ps.setString(2, dto.getDescripcion().toUpperCase());
            ps.setString(3, dto.getDireccion().toUpperCase());
            ps.setString(4, dto.getRuc());
            ps.setInt(5, dto.getCod_ciudad());
            ps.setInt(6, dto.getCod_empresa());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(EmpresaDTO dto) {
        try {
            sql = "DELETE FROM empresa\n"
                    + "WHERE cod_empresa=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getCod_empresa());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<EmpresaDTO> seleccionarTodos() {
        try {
            List<EmpresaDTO> lista;
            EmpresaDTO dto;
            sql = "SELECT e.cod_empresa, e.telefono, e.descripcion, e.direccion, e.ruc, c.cod_ciudad, c.descripcion AS ciudad\n"
                    + "FROM empresa e\n"
                    + "INNER JOIN ciudad c ON e.cod_ciudad = c.cod_ciudad\n"
                    + "ORDER BY cod_empresa ASC;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println(rs);
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new EmpresaDTO();
                dto.setCod_empresa(rs.getInt("cod_empresa"));
                dto.setTelefono(rs.getString("telefono"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setRuc(rs.getString("ruc"));
                dto.setCod_ciudad(rs.getInt("cod_ciudad"));
                dto.setCiudad_descripcion(rs.getString("ciudad"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<EmpresaDTO> seleccionarSegunFiltro(EmpresaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmpresaDTO seleccionarSegunId(EmpresaDTO dto) {
        try {
            EmpresaDTO dtoLocal = null;
            sql = "SELECT cod_empresa, telefono, descripcion, direccion, ruc, cod_ciudad\n"
                    + "FROM empresa WHERE cod_empresa= ?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_empresa());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new EmpresaDTO();
                dtoLocal.setCod_empresa(rs.getInt("cod_empresa"));
                dtoLocal.setTelefono(rs.getString("telefono"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                dtoLocal.setDireccion(rs.getString("direccion"));
                dtoLocal.setRuc(rs.getString("ruc"));
                dtoLocal.setCod_ciudad(rs.getInt("cod_ciudad"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
