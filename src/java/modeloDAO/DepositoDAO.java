package modeloDAO;

import modeloDTO.DepositoDTO;
import programas.conexion;
import interfaces.DepositoINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepositoDAO implements DepositoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public DepositoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(DepositoDTO dto) {
        try {
            sql = "INSERT INTO `deposito`(`dep_descri`) VALUES (?);";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDep_descri().toUpperCase());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(DepositoDTO dto) {
        try {
            sql = "UPDATE `deposito` SET `dep_descri`=? WHERE `id_deposito`=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setString(1, dto.getDep_descri().toUpperCase());
            ps.setInt(2, dto.getId_deposito());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(DepositoDTO dto) {
        try {
            sql = "DELETE FROM `deposito` WHERE `id_deposito`=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getId_deposito());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<DepositoDTO> seleccionarTodos() {
        try {
            List<DepositoDTO> lista;
            DepositoDTO dto;
            sql = "SELECT `id_deposito`, `dep_descri` FROM `deposito`;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new DepositoDTO();
                dto.setId_deposito(rs.getInt("id_deposito"));
                dto.setDep_descri(rs.getString("dep_descri"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<DepositoDTO> seleccionarSegunFiltro(DepositoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DepositoDTO seleccionarSegunId(DepositoDTO dto) {
        try {
            DepositoDTO dtoLocal = null;
            sql = "SELECT `id_deposito`, `dep_descri` FROM `deposito` WHERE `id_deposito`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_deposito());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new DepositoDTO();
                dtoLocal.setId_deposito(rs.getInt("id_deposito"));
                dtoLocal.setDep_descri(rs.getString("dep_descri"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
