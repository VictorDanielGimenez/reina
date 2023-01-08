package modeloDAO;

import modeloDTO.MotivoDTO;
import programas.conexion;
import interfaces.MotivoINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MotivoDAO implements MotivoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public MotivoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(MotivoDTO dto) {
        try {
            sql = "INSERT INTO motivo(cod_motivo, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_motivo),0)+1 from motivo), ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(1, dto.getDescripcion());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(MotivoDTO dto) {
        try {
            sql = "UPDATE motivo\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_motivo=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion());
            ps.setInt(2, dto.getCod_motivo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(MotivoDTO dto) {
        try {
            sql = "DELETE FROM motivo\n"
                    + " WHERE cod_motivo=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_motivo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<MotivoDTO> seleccionarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MotivoDTO> seleccionarSegunFiltro(MotivoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MotivoDTO seleccionarSegunId(MotivoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
