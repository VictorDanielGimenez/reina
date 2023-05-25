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
import javax.servlet.http.HttpServletRequest;

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
        try {
            sql = "INSERT INTO `usuario` (`usu_nombre`, `usuemail`, `usu_clave`, `id_estado`)"
                    + "VALUES (?, ?, ?, 2);";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getUsu_nombre().toUpperCase().trim());
            ps.setString(2, dto.getUsuemail());
            ps.setString(3, dto.getUsu_clave());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(UsuarioDTO dto) {
        // si la clave es diferente de vacio, se modifica la clave
        if (!dto.getUsu_clave().equals("")) {
            try {
                sql = "UPDATE `usuario` SET `usu_nombre` = ?, `usuemail` = ?, `usu_clave` = ? WHERE `id_usuario` = ?;";
                ps = conexion.getConnection().prepareStatement(sql);
                ps.setString(1, dto.getUsu_nombre().toUpperCase().trim());
                ps.setString(2, dto.getUsuemail());
                ps.setString(3, dto.getUsu_clave());
                ps.setInt(4, dto.getId_usuario());
                System.out.println(ps);
                return ps.executeUpdate() > 0;
            } catch (SQLException ex) {
                msg = ex.getMessage();
                return false;
            }
        } else {
            try {
                sql = "UPDATE `usuario` SET `usu_nombre` = ?, `usuemail` = ? WHERE `id_usuario` = ?;";
                ps = conexion.getConnection().prepareStatement(sql);
                ps.setString(1, dto.getUsu_nombre().toUpperCase().trim());
                ps.setString(2, dto.getUsuemail());
                ps.setInt(3, dto.getId_usuario());
                System.out.println(ps);
                return ps.executeUpdate() > 0;
            } catch (SQLException ex) {
                msg = ex.getMessage();
                return false;
            }
        }

    }

    @Override
    public Boolean eliminar(UsuarioDTO dto) {
        try {
            sql = "DELETE FROM `usuario` WHERE `id_usuario`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_usuario());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<UsuarioDTO> seleccionarTodos() {
        try {
            List<UsuarioDTO> lista;
            UsuarioDTO dto;
            sql = "SELECT a.id_usuario, a.usu_nombre, a.usuemail, e.est_descri, a.id_estado FROM usuario  a\n" +
                    "INNER JOIN estado e ON a.id_estado=e.id_estado;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new UsuarioDTO();
                dto.setId_usuario(rs.getInt("id_usuario"));
                dto.setUsu_nombre(rs.getString("usu_nombre"));
                dto.setUsuemail(rs.getString("usuemail"));
                dto.setEstado(rs.getString("est_descri"));
                dto.setId_estado(rs.getInt("id_estado"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<UsuarioDTO> seleccionarSegunFiltro(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public UsuarioDTO seleccionarSegunId(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<UsuarioDTO> UserLogueado() {
        try {
            List<UsuarioDTO> lista;
            UsuarioDTO dto;
            sql = "SELECT id_usuario, usu_nombre, SUBSTRING_INDEX(SUBSTRING_INDEX(usu_nombre, ' ', 1), ' ', -1) AS PrimerNombre, SUBSTRING_INDEX(SUBSTRING_INDEX(usu_nombre, ' ', 3), ' ', -1) AS PrimerApellido FROM usuario WHERE id_estado=1;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new UsuarioDTO();
                dto.setId_usuario(rs.getInt("id_usuario"));
                dto.setUsu_nombre(rs.getString("usu_nombre"));
                dto.setPrimernombre(rs.getString("PrimerNombre"));
                dto.setPrimerapellido(rs.getString("PrimerApellido"));
                lista.add(dto);
            }
            return lista;

        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public String generarPDF(HttpServletRequest reques) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String generarPDFSegunParametro(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
