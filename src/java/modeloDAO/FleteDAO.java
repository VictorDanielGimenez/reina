package modeloDAO;

import modeloDTO.FleteDTO;
import programas.conexion;
import interfaces.FleteINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FleteDAO implements FleteINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public FleteDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(FleteDTO dto) {
        try {
            sql = "INSERT INTO flete(cod_flete, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_flete),0)+1 from flete), ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(1, dto.getDescripcion());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(FleteDTO dto) {
        try {
            sql = "UPDATE flete\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_flete=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion());
            ps.setInt(2, dto.getCod_flete());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(FleteDTO dto) {
        try {
            sql = "DELETE FROM flete\n"
                    + "WHERE cod_flete=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_flete());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<FleteDTO> seleccionarTodos() {
        try {
            List<FleteDTO> lista;
            FleteDTO dto;
            sql = "SELECT cod_flete, descripcion\n"
                    + "FROM flete;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new FleteDTO();
                dto.setCod_flete(rs.getInt("cod_flete"));
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
    public List<FleteDTO> seleccionarSegunFiltro(FleteDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FleteDTO seleccionarSegunId(FleteDTO dto) {
        try {
            FleteDTO dtoLocal = null;
            sql = "SELECT cod_flete, descripcion FROM flete WHERE cod_flete=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_flete());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new FleteDTO();
                dtoLocal.setCod_flete(rs.getInt("cod_flete"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
