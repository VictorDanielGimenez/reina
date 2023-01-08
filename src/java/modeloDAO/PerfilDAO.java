package modeloDAO;

import modeloDTO.Menu_Item_SistemaDTO;
import modeloDTO.PerfilDTO;
import programas.conexion;
import interfaces.PerfilINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAO implements PerfilINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public PerfilDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(PerfilDTO dto) {
        try {
            Integer idPerfil;
            sql = "INSERT INTO perfil (cod_perfil, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_perfil),0)+1 from perfil), ?);";
            ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idPerfil = rs.getInt(1);
                    for (Menu_Item_SistemaDTO item : dto.getListaMenuitem()) {
                        sql = "INSERT INTO permisos(cod_permiso, cod_perfil, cod_menu_item)\n"
                                + "    VALUES ((select coalesce (max(cod_permiso),0)+1 from permisos), ?, ?);";
                        ps = conexion.getConnection().prepareStatement(sql);
                        ps.setInt(1, idPerfil);
                        ps.setInt(2, item.getCod_menu_item());
                        if (ps.executeUpdate() <= 0) {
                            //manejo de transaccion sql
                        }
                    }
                }
            } else {
                //manejo de transaccion sql
            }
            //transaccion exitosa
            return false;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("msg: " + msg);
            return false;
        }
    }
//    try {
//            sql = "INSERT INTO perfil (cod_perfil, descripcion)\n"
//                    + "VALUES ((select coalesce (max(cod_perfil),0)+1 from perfil), ?);";
//            System.out.println(sql);
//            ps = conexion.getConnection().prepareStatement(sql);
////          ps.setInt(1, dto.getCod_ciudad());
//            ps.setString(1, dto.getDescripcion().toUpperCase());
//            return ps.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            msg = ex.getMessage();
//            return false;
//        }
//    }

    @Override
    public Boolean modificar(PerfilDTO dto) {
        try {
            sql = "UPDATE perfil\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_perfil=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_perfil());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(PerfilDTO dto) {
        try {
            sql = "DELETE FROM perfil\n"
                    + "WHERE cod_perfil=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_perfil());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<PerfilDTO> seleccionarTodos() {
        try {
            List<PerfilDTO> lista;
            PerfilDTO dto;
            sql = "SELECT cod_perfil, descripcion\n"
                    + "FROM perfil;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new PerfilDTO();
                dto.setCod_perfil(rs.getInt("cod_perfil"));
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
    public List<PerfilDTO> seleccionarSegunFiltro(PerfilDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PerfilDTO seleccionarSegunId(PerfilDTO dto) {
        try {
            PerfilDTO dtoLocal = null;
            sql = "SELECT cod_perfil, descripcion FROM perfil WHERE cod_perfil=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_perfil());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new PerfilDTO();
                dtoLocal.setCod_perfil(rs.getInt("cod_perfil"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
