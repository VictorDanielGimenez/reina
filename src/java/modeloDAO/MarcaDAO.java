package modeloDAO;

import modeloDTO.MarcaDTO;
import programas.conexion;
import interfaces.MarcaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class MarcaDAO implements MarcaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public MarcaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(MarcaDTO dto) {
        try {
            sql = "INSERT INTO `marca`(`mar_descri`) VALUES (?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(1, dto.getMarca_descri().toUpperCase());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(MarcaDTO dto) {
        try {
            sql = "UPDATE `marca` SET `mar_descri`=? WHERE `id_marca`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getMarca_descri().toUpperCase());
            ps.setInt(2, dto.getId_marca());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(MarcaDTO dto) {
        try {
            sql = "DELETE FROM `marca` WHERE `id_marca`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getId_marca());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<MarcaDTO> seleccionarTodos() {
        try {
            List<MarcaDTO> lista;
            MarcaDTO dto;
            sql = "SELECT `id_marca`, `mar_descri` FROM `marca`;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MarcaDTO();
                dto.setId_marca(rs.getInt("id_marca"));
                dto.setMarca_descri(rs.getString("mar_descri"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<MarcaDTO> seleccionarSegunFiltro(MarcaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MarcaDTO seleccionarSegunId(MarcaDTO dto) {
        try {
            MarcaDTO dtoLocal = null;
            sql = "SELECT `id_marca`, `mar_descri` FROM `marca` WHERE `id_marca`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_marca());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new MarcaDTO();
                dtoLocal.setId_marca(rs.getInt("id_marca"));
                dtoLocal.setMarca_descri(rs.getString("mar_descri"));
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
    public String generarPDFSegunParametro(MarcaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
