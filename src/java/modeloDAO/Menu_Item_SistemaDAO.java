package modeloDAO;

import modeloDTO.Menu_Item_SistemaDTO;
import programas.conexion;
import interfaces.Menu_Item_SistemaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Menu_Item_SistemaDAO implements Menu_Item_SistemaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public Menu_Item_SistemaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(Menu_Item_SistemaDTO dto) {
        try {
            sql = "INSERT INTO menu_item_sistema(cod_menu_item, descripcion, url, cod_menu)\n"
                    + "VALUES ((select coalesce (max(cod_menu_item),0)+1 from menu_item_sistema), ?, ?, ?);";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion());
            ps.setString(2, dto.getUrl());
            ps.setInt(3, dto.getCod_menu());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(Menu_Item_SistemaDTO dto) {
        try {
            sql = "UPDATE menu_item_sistema\n"
                    + "SET descripcion=?, url=?, cod_menu=?\n"
                    + "WHERE cod_menu_item=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setString(1, dto.getDescripcion());
            ps.setString(2, dto.getUrl());
            ps.setInt(3, dto.getCod_menu());
            ps.setInt(4, dto.getCod_menu_item());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(Menu_Item_SistemaDTO dto) {
        try {
            sql = "DELETE FROM menu_item_sistema\n"
                    + "WHERE cod_menu_item=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getCod_menu_item());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<Menu_Item_SistemaDTO> seleccionarTodos() {
        try {
            List<Menu_Item_SistemaDTO> lista;
            Menu_Item_SistemaDTO dto;
            sql = "SELECT m.cod_menu_item, m.descripcion AS menu_item_sistema, m.url, \n"
                    + "me.cod_menu, me.descripcion AS menu_sistema\n"
                    + "FROM menu_item_sistema m\n"
                    + "INNER JOIN menu_sistema me ON m.cod_menu=me.cod_menu\n"
                    + "ORDER BY cod_menu_item ASC;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println(rs);
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Menu_Item_SistemaDTO();
                dto.setCod_menu_item(rs.getInt("cod_menu_item"));
                dto.setDescripcion(rs.getString("menu_item_sistema"));
                dto.setUrl(rs.getString("url"));
                dto.setCod_menu(rs.getInt("cod_menu"));
                dto.setDescripcion_menu(rs.getString("menu_sistema"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<Menu_Item_SistemaDTO> seleccionarSegunFiltro(Menu_Item_SistemaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Menu_Item_SistemaDTO seleccionarSegunId(Menu_Item_SistemaDTO dto) {
        try {
            Menu_Item_SistemaDTO dtoLocal = null;
            sql = "SELECT cod_menu_item, descripcion, url, cod_menu\n"
                    + "FROM menu_item_sistema WHERE cod_menu_item=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_menu_item());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new Menu_Item_SistemaDTO();
                dtoLocal.setCod_menu_item(rs.getInt("cod_empresa"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                dtoLocal.setUrl(rs.getString("url"));
                dtoLocal.setCod_menu(rs.getInt("cod_menu"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
