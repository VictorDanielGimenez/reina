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

    public boolean validarUsuario(AccesoDTO dto) {
        boolean retorno = false;
        try {
           
            sql = "SELECT `id_usuario` FROM `usuario` WHERE `usuemail`=? AND `usu_clave`=?";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getEmail());
            ps.setString(2, dto.getClave());
            rs = ps.executeQuery();
            while (rs.next()) {
                sql = "UPDATE `usuario` SET `id_estado`=1 WHERE `id_usuario`=?";
                ps = conexion.getConnection().prepareStatement(sql);
                ps.setInt(1, rs.getInt("id_usuario"));
                ps.executeUpdate();
                retorno = true;
            }
              System.out.println("dd"+retorno);
                return retorno;
            
        } catch (SQLException ex) {
            System.out.println("Error al verificar usuario" + ex.getMessage());
              System.out.println("ff"+retorno);
            return retorno;
            
        }

    }

    @Override
    public boolean CerrarSession(AccesoDTO dto) {
        boolean retorno = false;
        try {
           
            sql = "SELECT `id_usuario` FROM `usuario` WHERE id_estado=1";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                sql = "UPDATE `usuario` SET `id_estado`=2 WHERE `id_usuario`=?";
                ps = conexion.getConnection().prepareStatement(sql);
                ps.setInt(1, rs.getInt("id_usuario"));
                ps.executeUpdate();
                retorno = true;
            }
              System.out.println("dd"+retorno);
                return retorno;
            
        } catch (SQLException ex) {
            System.out.println("Error al verificar usuario" + ex.getMessage());
              System.out.println("ff"+retorno);
            return retorno;
            
        }
    }

   
   

}
