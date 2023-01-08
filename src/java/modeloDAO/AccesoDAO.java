package modeloDAO;

import modeloDTO.AccesoDTO;
import programas.conexion;
import interfaces.AccesoINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccesoDAO implements AccesoINT {

    private conexion conexion;
    private String sql;
    private ResultSet rs;
    private PreparedStatement ps;
    private List<String> bloquesConstruidos;
    private HashMap<String, List<String>> gestor;

    public AccesoDAO() {
        conexion = new conexion();
    }

    @Override
    public boolean validarUsuario(AccesoDTO dto) {
        try {
            sql = "SELECT p.cod_permiso, pe.cod_perfil, pe.descripcion AS perfil, mi.cod_menu_item, mi.descripcion AS item, ms.cod_menu, ms.descripcion AS menu\n"
                    + "FROM permisos p\n"
                    + "INNER JOIN perfil pe ON p.cod_perfil=pe.cod_perfil\n"
                    + "INNER JOIN menu_item_sistema mi ON p.cod_menu_item=mi.cod_menu_item\n"
                    + "INNER JOIN menu_sistema ms ON mi.cod_menu=ms.cod_menu\n"
                    + "WHERE pe.cod_perfil=(select cod_perfil From usuarios WHERE usuario= ? AND clave= ? AND cod_estado=1);";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getUsuario());
            ps.setString(2, dto.getClave());
            rs = ps.executeQuery();
            Integer idMenuAnt = 0;
            List<String> menuI = null;
            gestor = new HashMap<>();
            while (rs.next()) {
                if ((idMenuAnt == 0) || (!idMenuAnt.equals(rs.getInt("cod_menu")))) {
                    menuI = new ArrayList<>();
                    menuI.add(addMenuItem(rs.getString("cod_menu_item"),
                            rs.getString("url")));
                } else {
                    menuI.add(addMenuItem(rs.getString("cod_menu_item"),
                            rs.getString("url")));
                }
                gestor.put(addMenu(rs.getString("menu")), menuI);
                idMenuAnt = rs.getInt("id_menu");
            }
            nucleoMenuDinamicoFinal();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error en consular Usuario " + ex.getMessage());
            return false;
        }

    }

   
    private void nucleoMenuDinamicoFinal() {
        bloquesConstruidos = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : gestor.entrySet()) {
            String menu = entry.getKey();
            List<String> menuItem = entry.getValue();
            bloquesConstruidos.add(construirBloque(menu, menuItem));
        }
    }

    private String construirBloque(String menu, List<String> listMenuItem) {
        String cadenaBloque, bloqueMenuItem = "";
        for (String menuItem : listMenuItem) {
            bloqueMenuItem += menuItem;
        }
        cadenaBloque = "<div class=\"w3-dropdown-hover\">\n"
                + menu
                + "<div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + bloqueMenuItem
                + "</div>\n"
                + "</div>";
        return cadenaBloque;
    }

    private String addMenu(String menu) {
        return "<button class=\"w3-button\">" + menu + "</button>\n";
    }

    private String addMenuItem(String titulo, String url) {
        return "<a href=\"" + url + "\" target=\"contenedor_paginas\" class=\"w3-bar-item w3-button\">" + titulo + "</a>\n";
    }
}
