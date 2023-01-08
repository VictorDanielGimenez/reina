package modeloDAO;

import modeloDTO.Tipo_BarcazaDTO;
import programas.conexion;
import interfaces.Tipo_BarcazaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tipo_BarcazaDAO implements Tipo_BarcazaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public Tipo_BarcazaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(Tipo_BarcazaDTO dto) {
        try {
            sql = "INSERT INTO tipo_barcaza(codigo, descripcion, simbolo)\n"
                    + "VALUES ((select coalesce (max(codigo),0)+1 from tipo_barcaza), ?, ?);";
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
    public Boolean modificar(Tipo_BarcazaDTO dto) {
        try {
            sql = "UPDATE tipo_barcaza\n"
                    + "SET descripcion=?, simbolo=?\n"
                    + "WHERE codigo=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setString(2, dto.getSimbolo().toUpperCase());
            ps.setInt(3, dto.getCodigo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(Tipo_BarcazaDTO dto) {
        try {
            sql = "DELETE FROM tipo_barcaza\n"
                    + " WHERE codigo=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCodigo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<Tipo_BarcazaDTO> seleccionarTodos() {
        try {
            List<Tipo_BarcazaDTO> lista;
            Tipo_BarcazaDTO dto;
            sql = "SELECT codigo, descripcion, simbolo\n"
                    + "FROM tipo_barcaza\n"
                    + "ORDER BY codigo ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Tipo_BarcazaDTO();
                dto.setCodigo(rs.getInt("codigo"));
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
    public List<Tipo_BarcazaDTO> seleccionarSegunFiltro(Tipo_BarcazaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tipo_BarcazaDTO seleccionarSegunId(Tipo_BarcazaDTO dto) {
        try {
            Tipo_BarcazaDTO dtoLocal = null;
            sql = "SELECT codigo, descripcion, simbolo\n"
                    + "FROM tipo_barcaza\n"
                    + "WHERE codigo = ?";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCodigo());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new Tipo_BarcazaDTO();
                dtoLocal.setCodigo(rs.getInt("codigo"));
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
