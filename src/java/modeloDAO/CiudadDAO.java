package modeloDAO;

import modeloDTO.CiudadDTO;
import programas.conexion;
import interfaces.CiudadINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CiudadDAO implements CiudadINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public CiudadDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(CiudadDTO dto) {
        try {
            sql = "INSERT INTO ciudad (cod_ciudad, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_ciudad),0)+1 from ciudad), ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(1, dto.getDescripcion().toUpperCase());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(CiudadDTO dto) {
        try {
            sql = "UPDATE ciudad\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_ciudad=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_ciudad());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(CiudadDTO dto) {
        try {
            sql = "DELETE FROM ciudad\n"
                    + "WHERE cod_ciudad=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getCod_ciudad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<CiudadDTO> seleccionarTodos() {
        try {
            List<CiudadDTO> lista;
            CiudadDTO dto;
            sql = "SELECT cod_ciudad, descripcion\n"
                    + "  FROM ciudad ORDER BY cod_ciudad ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CiudadDTO();
                dto.setCod_ciudad(rs.getInt("cod_ciudad"));
                dto.setDescripcion(rs.getString("descripcion"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<CiudadDTO> seleccionarSegunFiltro(CiudadDTO dto) {
        try {
            List<CiudadDTO> lista;
            CiudadDTO dtoLocal;
            sql = "SELECT cod_ciudad, descripcion\n"
                    + "FROM ciudad"
                    + "WHERE cod_ciudad=? and descripcion= ?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(2, dto.getDescripcion());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dtoLocal = new CiudadDTO();
                dtoLocal.setCod_ciudad(rs.getInt("cod_ciudad"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                lista.add(dtoLocal);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public CiudadDTO seleccionarSegunId(CiudadDTO dto) {
        try {
            CiudadDTO dtoLocal = null;
            sql = "SELECT cod_ciudad, descripcion FROM ciudad WHERE cod_ciudad=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_ciudad());
            System.out.println(ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new CiudadDTO();
                dtoLocal.setCod_ciudad(rs.getInt("cod_ciudad"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }
}
