package modeloDAO;

import modeloDTO.Unidad_MedidaDTO;
import programas.conexion;
import interfaces.Unidad_MedidaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Unidad_MedidaDAO implements Unidad_MedidaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public Unidad_MedidaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(Unidad_MedidaDTO dto) {
        try {
            sql = "INSERT INTO unidad_medida(cod_medida, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_medida),0)+1 from unidad_medida), ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion().toUpperCase().trim());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(Unidad_MedidaDTO dto) {
        try {
            sql = "UPDATE unidad_medida\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_medida=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase().trim());
            ps.setInt(2, dto.getCod_medida());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(Unidad_MedidaDTO dto) {
        try {
            sql = "DELETE FROM unidad_medida\n"
                    + " WHERE cod_medida=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_medida());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<Unidad_MedidaDTO> seleccionarTodos() {
        try {
            List<Unidad_MedidaDTO> lista;
            Unidad_MedidaDTO dto;
            sql = "SELECT cod_medida, descripcion\n"
                    + "FROM unidad_medida;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Unidad_MedidaDTO();
                dto.setCod_medida(rs.getInt("cod_medida"));
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
    public List<Unidad_MedidaDTO> seleccionarSegunFiltro(Unidad_MedidaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Unidad_MedidaDTO seleccionarSegunId(Unidad_MedidaDTO dto) {
        try {
            Unidad_MedidaDTO dtoLocal = null;
            sql = "SELECT cod_medida, descripcion FROM unidad_medida WHERE cod_medida=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_medida());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new Unidad_MedidaDTO();
                dtoLocal.setCod_medida(rs.getInt("cod_medida"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
