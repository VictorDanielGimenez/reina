package modeloDAO;

import modeloDTO.Menu_SistemaDTO;
import programas.conexion;
import interfaces.Menu_SistemaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Menu_SistemaDAO implements Menu_SistemaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public Menu_SistemaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(Menu_SistemaDTO dto) {
        try {
            sql = "INSERT INTO menu_sistema(cod_menu, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_menu),0)+1 from menu_sistema), ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(Menu_SistemaDTO dto) {
        try {
            sql = "UPDATE menu_sistema\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_menu=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion());
            ps.setInt(2, dto.getCod_menu());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(Menu_SistemaDTO dto) {
        try {
            sql = "DELETE FROM menu_sistema\n"
                    + " WHERE cod_menu=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_menu());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<Menu_SistemaDTO> seleccionarTodos() {
        try {
            List<Menu_SistemaDTO> lista;
            Menu_SistemaDTO dto;
            sql = "SELECT cod_menu, descripcion\n"
                    + "FROM menu_sistema;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Menu_SistemaDTO();
                dto.setCod_menu(rs.getInt("cod_menu"));
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
    public List<Menu_SistemaDTO> seleccionarSegunFiltro(Menu_SistemaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Menu_SistemaDTO seleccionarSegunId(Menu_SistemaDTO dto) {
        try {
            Menu_SistemaDTO dtoLocal = null;
            sql = "SELECT cod_menu, descripcion\n"
                    + "FROM menu_sistema WHERE cod_menu=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_menu());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new Menu_SistemaDTO();
                dtoLocal.setCod_menu(rs.getInt("cod_menu"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
