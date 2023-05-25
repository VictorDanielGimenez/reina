package modeloDAO;

import modeloDTO.ProveedorDTO;
import programas.conexion;
import interfaces.ProveedorINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class ProveedorDAO implements ProveedorINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public ProveedorDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(ProveedorDTO dto) {
        try {
            sql = "INSERT INTO `proveedor`(`prov_razons`, `prov_ruc`, `prov_tel`, `prov_direc`, `prov_correo`,  `id_ciudad`,  `id_estado`)"
                    + "VALUES (?,?,?,?,?,?,1);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getProv_razons().toUpperCase());
            ps.setString(2, dto.getProv_ruc());
            ps.setString(3, dto.getProv_te());
            ps.setString(4, dto.getProv_direc().toUpperCase());
            ps.setString(5, dto.getProv_correo());
            ps.setInt(6, dto.getId_ciudad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(ProveedorDTO dto) {
        try {
            sql = "UPDATE `proveedor` SET `prov_razons`=?, `prov_ruc`=?, `prov_tel`=?, `prov_direc`=?, `prov_correo`=? ,`id_ciudad`=? WHERE `id_proveedor`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getProv_razons().toUpperCase());
            ps.setString(2, dto.getProv_ruc());
            ps.setString(3, dto.getProv_te());
            ps.setString(4, dto.getProv_direc().toUpperCase());
            ps.setString(5, dto.getProv_correo());
            ps.setInt(6, dto.getId_ciudad());
            ps.setInt(7, dto.getId_proveedor());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(ProveedorDTO dto) {       
         try {
            sql = "DELETE FROM `proveedor` WHERE `id_proveedor`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_proveedor());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<ProveedorDTO> seleccionarTodos() {
       try {
            List<ProveedorDTO> lista;
            ProveedorDTO dto;
            sql = "SELECT p.id_proveedor, p.prov_razons, p.prov_ruc, p.prov_tel, p.prov_direc, p.prov_correo, c.ciu_descri, p.id_ciudad FROM proveedor p INNER JOIN ciudad c ON p.id_ciudad=c.id_ciudad;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ProveedorDTO();
                dto.setId_proveedor(rs.getInt("id_proveedor"));
                dto.setProv_razons(rs.getString("prov_razons"));
                dto.setProv_ruc(rs.getString("prov_ruc"));
                dto.setProv_te(rs.getString("prov_tel"));
                dto.setProv_direc(rs.getString("prov_direc"));
                dto.setProv_correo(rs.getString("prov_correo"));
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
    public List<ProveedorDTO> seleccionarSegunFiltro(ProveedorDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProveedorDTO seleccionarSegunId(ProveedorDTO dto) {
        try {
            ProveedorDTO dtoLocal = null;
            sql = "SELECT p.id_proveedor, p.prov_razons, p.prov_ruc, p.prov_tel, p.prov_direc, p.prov_correo, p.id_ciudad, c.ciu_descri FROM proveedor p INNER JOIN ciudad c ON p.id_ciudad=c.id_ciudad WHERE p.id_proveedor=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_proveedor());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new ProveedorDTO();
                  dtoLocal.setId_proveedor(rs.getInt("id_proveedor"));
                dtoLocal.setProv_razons(rs.getString("prov_razons"));
                dtoLocal.setProv_ruc(rs.getString("prov_ruc"));
                dtoLocal.setProv_te(rs.getString("prov_tel"));
                dtoLocal.setProv_direc(rs.getString("prov_direc"));
                dtoLocal.setProv_correo(rs.getString("prov_correo"));
                dtoLocal.setCiu_descri(rs.getString("ciu_descri"));
                dtoLocal.setId_ciudad(rs.getInt("id_ciudad"));
            }
            return dtoLocal;
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
    public String generarPDFSegunParametro(ProveedorDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
}
