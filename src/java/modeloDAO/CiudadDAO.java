package modeloDAO;

import modeloDTO.CiudadDTO;
import programas.conexion;
import interfaces.CiudadINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CiudadDAO implements CiudadINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public CiudadDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(CiudadDTO dto) {
        try {
            sql = "INSERT INTO `ciudad` (`ciu_descri`) VALUES (?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getCiu_descri().toUpperCase());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(CiudadDTO dto) {
        try {
            sql = "UPDATE `ciudad` SET `ciu_descri`=? WHERE `id_ciudad`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getCiu_descri().toUpperCase());
            ps.setInt(2, dto.getId_ciudad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(CiudadDTO dto) {       
         try {
            sql = "DELETE FROM `ciudad` WHERE `id_ciudad`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_ciudad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<CiudadDTO> seleccionarTodos() {
       try {
            List<CiudadDTO> lista;
            CiudadDTO dto;
            sql = "SELECT `id_ciudad`,`ciu_descri` FROM `ciudad`;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CiudadDTO();
                dto.setId_ciudad(rs.getInt("id_ciudad"));
                dto.setCiu_descri(rs.getString("ciu_descri"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<CiudadDTO> seleccionarSegunFiltro(CiudadDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CiudadDTO seleccionarSegunId(CiudadDTO dto) {
        try {
            CiudadDTO dtoLocal = null;
            sql = "SELECT `id_ciudad`,`ciu_descri` FROM `ciudad` WHERE `id_ciudad`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_ciudad());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new CiudadDTO();
                dtoLocal.setId_ciudad(rs.getInt("id_ciudad"));
                dtoLocal.setCiu_descri(rs.getString("ciu_descri"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

   
}
