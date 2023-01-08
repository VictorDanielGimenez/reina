package modeloDAO;

import modeloDTO.UsuarioDTO;
import programas.conexion;
import interfaces.UsuarioINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements UsuarioINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public UsuarioDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getPermiso(UsuarioDTO dto) {
       try {
            sql = "SELECT cod_perfil FROM usuarios where cod_estado = 1 AND usuario= ? AND clave= ?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getUsuemail().trim());
            ps.setString(2, dto.getUsu_clave().trim());
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cod_perfil");
            }
        } catch (SQLException ex) {

            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 0;
    }

    @Override
    public Boolean modificar(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminar(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<UsuarioDTO> seleccionarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<UsuarioDTO> seleccionarSegunFiltro(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public UsuarioDTO seleccionarSegunId(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
