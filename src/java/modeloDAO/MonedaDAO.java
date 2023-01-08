package modeloDAO;

import modeloDTO.MonedaDTO;
import programas.conexion;
import interfaces.MonedaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonedaDAO implements MonedaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public MonedaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(MonedaDTO dto) {
        try {
            sql = "INSERT INTO moneda(cod_moneda, descripcion, simbolo)\n"
                    + "VALUES ((select coalesce (max(cod_moneda),0)+1 from moneda), ?, ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
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
    public Boolean modificar(MonedaDTO dto) {
        try {
            sql = "UPDATE moneda\n"
                    + "SET descripcion=?, simbolo=?\n"
                    + "WHERE cod_moneda=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setString(2, dto.getSimbolo().toUpperCase());
            ps.setInt(3, dto.getCod_moneda());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(MonedaDTO dto) {
        try {
            sql = "DELETE FROM moneda\n"
                    + "WHERE cod_moneda=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_moneda());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<MonedaDTO> seleccionarTodos() {
        try {
            List<MonedaDTO> lista;
            MonedaDTO dto;
            sql = "SELECT cod_moneda, descripcion, simbolo FROM moneda ORDER BY cod_moneda ASC;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println(rs);
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MonedaDTO();
                dto.setCod_moneda(rs.getInt("cod_moneda"));
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
    public List<MonedaDTO> seleccionarSegunFiltro(MonedaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MonedaDTO seleccionarSegunId(MonedaDTO dto) {
       try {
            MonedaDTO dtoLocal = null;
            sql = "SELECT cod_moneda, descripcion, simbolo FROM moneda WHERE cod_moneda=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_moneda());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new MonedaDTO();
                dtoLocal.setCod_moneda(rs.getInt("cod_moneda"));
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
