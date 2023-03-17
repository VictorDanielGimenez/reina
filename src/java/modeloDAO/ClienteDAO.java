package modeloDAO;

import modeloDTO.ClienteDTO;
import programas.conexion;
import interfaces.ClienteINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO implements ClienteINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public ClienteDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(ClienteDTO dto) {
        try {
            sql = "INSERT INTO `cliente` (`cli_nombre`, `cli_ci`, `cli_ruc`, `cli_direc`, `cli_telef`, `id_ciudad`, `id_estado`)  VALUES (?,?,?,?,?,?,1);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getCli_nombre().toUpperCase());
            ps.setString(2, dto.getCli_ci());
            ps.setString(3, dto.getCli_ruc());
            ps.setString(4, dto.getCli_direc().toUpperCase());
            ps.setString(5, dto.getCli_telef());
            ps.setInt(6, dto.getId_ciudad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(ClienteDTO dto) {
        try {
            sql = "UPDATE `cliente` SET`cli_nombre`=?, `cli_ci`=?, `cli_ruc`=?, `cli_direc`=?, `cli_telef`=?, `id_ciudad`=? WHERE `id_cliente`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getCli_nombre().toUpperCase());
            ps.setString(2, dto.getCli_ci());
            ps.setString(3, dto.getCli_ruc());
            ps.setString(4, dto.getCli_direc().toUpperCase());
            ps.setString(5, dto.getCli_telef());
            ps.setInt(6, dto.getId_ciudad());
            ps.setInt(7, dto.getId_cliente());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(ClienteDTO dto) {       
         try {
            sql = "DELETE FROM `cliente` WHERE `id_Cliente`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_cliente());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<ClienteDTO> seleccionarTodos() {
       try {
            List<ClienteDTO> lista;
            ClienteDTO dto;
            sql = "SELECT c.id_cliente,c.cli_nombre, c.cli_ruc, c.cli_ci, c.cli_direc,c.cli_telef,s.ciu_descri, c.id_ciudad FROM cliente c INNER JOIN ciudad s ON c.id_ciudad=s.id_ciudad;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ClienteDTO();
                dto.setId_cliente(rs.getInt("id_cliente"));
                dto.setCli_nombre(rs.getString("cli_nombre"));
                dto.setCli_ruc(rs.getString("cli_ruc"));
                dto.setCli_ci(rs.getString("cli_ci"));
                dto.setCli_direc(rs.getString("cli_direc"));
                dto.setCli_telef(rs.getString("cli_telef"));
                dto.setCiu_descri(rs.getString("ciu_descri"));
                dto.setId_ciudad(rs.getInt("id_ciudad"));                    
                    
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<ClienteDTO> seleccionarSegunFiltro(ClienteDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClienteDTO seleccionarSegunId(ClienteDTO dto) {
        try {
            ClienteDTO dtoLocal = null;
            sql = "SELECT c.id_cliente,c.cli_nombre, c.cli_ruc, c.cli_ci, c.cli_direc,c.cli_telef,s.ciu_descri, c.id_ciudad FROM cliente c INNER JOIN ciudad s ON c.id_ciudad=s.id_ciudad WHERE id_cliente=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_cliente());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new ClienteDTO();
                 dtoLocal.setId_cliente(rs.getInt("id_cliente"));
                dtoLocal.setCli_nombre(rs.getString("cli_nombre"));
                dtoLocal.setCli_ruc(rs.getString("cli_ruc"));
                dtoLocal.setCli_ci(rs.getString("cli_ci"));
                dtoLocal.setCli_direc(rs.getString("cli_direc"));
                dtoLocal.setCli_telef(rs.getString("cli_telef"));
                dtoLocal.setCiu_descri(rs.getString("ciu_descri"));
                dtoLocal.setId_ciudad(rs.getInt("id_ciudad"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

   
}
