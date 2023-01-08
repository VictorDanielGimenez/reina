package modeloDAO;

import modeloDTO.Tipo_EmbarcacionDTO;
import programas.conexion;
import interfaces.Tipo_EmbarcacionINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tipo_EmbarcacionDAO implements Tipo_EmbarcacionINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public Tipo_EmbarcacionDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(Tipo_EmbarcacionDTO dto) {
        try {
            sql = "INSERT INTO tipo_embarcacion(cod_tipo, descripcion, simbolo)\n"
                    + "VALUES ((select coalesce (max(cod_tipo),0)+1 from tipo_embarcacion), ?, ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setString(2, dto.getSimbolo().toUpperCase());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(Tipo_EmbarcacionDTO dto) {
        try {
            sql = "UPDATE tipo_embarcacion\n"
                    + "SET descripcion=?, simbolo=?\n"
                    + "WHERE cod_tipo=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setString(2, dto.getSimbolo().toUpperCase());
            ps.setInt(3, dto.getCod_tipo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(Tipo_EmbarcacionDTO dto) {
        try {
            sql = "DELETE FROM tipo_embarcacion\n"
                    + " WHERE cod_tipo=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_tipo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<Tipo_EmbarcacionDTO> seleccionarTodos() {
        try {
            List<Tipo_EmbarcacionDTO> lista;
            Tipo_EmbarcacionDTO dto;
            sql = "SELECT cod_tipo, descripcion, simbolo\n"
                    + "FROM tipo_embarcacion ORDER BY cod_tipo ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Tipo_EmbarcacionDTO();
                dto.setCod_tipo(rs.getInt("cod_tipo"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setSimbolo(rs.getString("simbolo"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<Tipo_EmbarcacionDTO> seleccionarSegunFiltro(Tipo_EmbarcacionDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tipo_EmbarcacionDTO seleccionarSegunId(Tipo_EmbarcacionDTO dto) {
        try {
            Tipo_EmbarcacionDTO dtoLocal = null;
            sql = "SELECT cod_tipo, descripcion, simbolo FROM tipo_embarcacion WHERE cod_tipo=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_tipo());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new Tipo_EmbarcacionDTO();
                dtoLocal.setCod_tipo(rs.getInt("cod_departamento"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                dtoLocal.setSimbolo(rs.getString("simbolo"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }
}