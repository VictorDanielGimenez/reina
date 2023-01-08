package modeloDAO;

import modeloDTO.Menu_Item_SistemaDTO;
import modeloDTO.PerfilDTO;
import modeloDTO.PermisoDTO;
import programas.conexion;
import interfaces.PermisoINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PermisoDAO implements PermisoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public PermisoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(PermisoDTO dto) {
        try {
            int idPerfil;
            sql = "INSERT INTO perfil(cod_perfil, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_perfil),0)+1 from perfil), ?);";
            ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getPerfil_descripcion());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idPerfil = rs.getInt(1);
                    for (Menu_Item_SistemaDTO item : dto.getLista_menu_item()) {
                        sql = "INSERT INTO permisos(cod_permiso, cod_perfil, cod_menu_item)\n"
                                + "VALUES ((select coalesce (max(cod_permiso),0)+1 from permisos), ?, ?);";
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
            //operacion exitosa...

            return false;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error en agregar " + msg);
            return false;
        }
    }

    @Override
    public Boolean modificar(PermisoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean eliminar(PermisoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PermisoDTO> seleccionarTodos() {
        try {
            List<PermisoDTO> listaID;
            PermisoDTO permisoDTO = null;
            PerfilDTO perfilDTO;
            sql = "SELECT p.cod_permiso, p.cod_perfil, pe.descripcion AS perfil,\n"
                    + "p.cod_menu_item\n"
                    + "FROM permisos p\n"
                    + "INNER JOIN perfil pe ON pe.cod_perfil=p.cod_perfil;";
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println("PS: " + ps);
            rs = ps.executeQuery();
            listaID = new ArrayList<>();
            while (rs.next()) {
                permisoDTO = new PermisoDTO();
                permisoDTO.setCod_permiso(rs.getInt("cod_permiso"));
                permisoDTO.setCod_perfil(rs.getInt("cod_perfil"));
                permisoDTO.setPerfil_descripcion(rs.getString("perfil"));
                permisoDTO.setCod_menu_item(rs.getInt("cod_menu_item"));
                listaID.add(permisoDTO);
            }
            return listaID;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error al recuperar permisos: " + msg);
            return null;
        }
    }

    @Override
    public List<PermisoDTO> seleccionarSegunFiltro(PermisoDTO dto) {
        try {
            List<PermisoDTO> listaID;
            PermisoDTO permisoDTO = null;
            PerfilDTO perfilDTO;
            sql = "SELECT p.cod_permiso, p.cod_perfil, pe.descripcion AS perfil,\n"
                    + "p.cod_menu_item\n"
                    + "FROM permisos p\n"
                    + "INNER JOIN perfil pe ON pe.cod_perfil=p.cod_perfil\n"
                    + "WHERE p.cod_perfil=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_perfil());
            System.out.println("PS: " + ps);
            rs = ps.executeQuery();
            listaID = new ArrayList<>();
            while (rs.next()) {
                permisoDTO = new PermisoDTO();
                permisoDTO.setCod_permiso(rs.getInt("cod_permiso"));
                
                perfilDTO = new PerfilDTO();
                perfilDTO.setCod_perfil(rs.getInt("cod_perfil"));
                perfilDTO.setDescripcion(rs.getString("perfil"));
                permisoDTO.setCod_menu_item(rs.getInt("cod_menu_item"));
                listaID.add(permisoDTO);
            }
            return listaID;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error al recuperar permisos: " + msg);
            return null;
        }
    }

    @Override
    public PermisoDTO seleccionarSegunId(PermisoDTO dto) {
        try {
            List<PermisoDTO> listaID;
            PermisoDTO permisoDTO = null;
            PerfilDTO perfilDTO;
            sql = "SELECT p.cod_permiso, p.cod_perfil, pe.descripcion AS perfil,\n"
                    + "p.cod_menu_item\n"
                    + "FROM permisos p\n"
                    + "INNER JOIN perfil pe ON pe.cod_perfil=p.cod_perfil\n"
                    + "WHERE p.cod_perfil=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_perfil());
            System.out.println("PS: " + ps);
            rs = ps.executeQuery();
            listaID = new ArrayList<>();
            while (rs.next()) {
                permisoDTO = new PermisoDTO();
                permisoDTO.setCod_permiso(rs.getInt("cod_permiso"));
                
                perfilDTO = new PerfilDTO();
                perfilDTO.setCod_perfil(rs.getInt("cod_perfil"));
                perfilDTO.setDescripcion(rs.getString("perfil"));
                permisoDTO.setCod_menu_item(rs.getInt("cod_menu_item"));
                listaID.add(permisoDTO);
            }
            return (PermisoDTO) listaID;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error al recuperar permisos: " + msg);
            return null;
        }
    }
}
