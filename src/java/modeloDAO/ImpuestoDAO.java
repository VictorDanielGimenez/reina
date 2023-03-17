package modeloDAO;

import modeloDTO.ImpuestoDTO;
import programas.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.ImpuestoINT;

public class ImpuestoDAO implements ImpuestoINT {

    private ResultSet rs; //permite trabajar con select
    private PreparedStatement ps; //ejecuta query de todas las sentencias
    private final conexion conexion;
    private String sql;
    private String msg;

    public ImpuestoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(ImpuestoDTO dto) {
        try {
            sql = "INSERT INTO `impuesto`(`imp_descri`) VALUES (?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(1, dto.getImp_descri());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }

    }

    @Override
    public Boolean modificar(ImpuestoDTO dto) {
        try {
            sql = "UPDATE `impuesto` SET `imp_descri`=? WHERE `id_impuesto`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getImp_descri());
            ps.setInt(2, dto.getId_impuesto());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(ImpuestoDTO dto) {
        try {
            sql = "DELETE FROM `impuesto` WHERE `id_impuesto`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getId_impuesto());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<ImpuestoDTO> seleccionarTodos() {
        try {
            List<ImpuestoDTO> lista;
            ImpuestoDTO dto;
            sql = "SELECT `id_impuesto`, `imp_descri` FROM `impuesto`;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ImpuestoDTO();
                dto.setId_impuesto(rs.getInt("id_impuesto"));
                dto.setImp_descri(rs.getString("imp_descri"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<ImpuestoDTO> seleccionarSegunFiltro(ImpuestoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ImpuestoDTO seleccionarSegunId(ImpuestoDTO dto) {
        try {
            ImpuestoDTO dtoLocal = null;
            sql = "SELECT `id_impuesto`, `imp_descri` FROM `impuesto` WHERE `id_impuesto`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_impuesto());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new ImpuestoDTO();
                dtoLocal.setId_impuesto(rs.getInt("id_impuesto"));
                dtoLocal.setImp_descri(rs.getString("imp_descri"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
