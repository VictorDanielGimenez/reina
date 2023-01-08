package modeloDAO;

import modeloDTO.ImpuestoDTO;
import programas.conexion;
import interfaces.ImpuestoINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpuestoDAO implements ImpuestoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public ImpuestoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(ImpuestoDTO dto) {
        try {
            sql = "INSERT INTO tipo_impuesto(cod_impuesto, descripcion, porcentaje)\n"
                    + "VALUES ((select coalesce (max(cod_impuesto),0)+1 from tipo_impuesto), ?, ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion());
            ps.setString(2, dto.getPorcentaje());
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
            sql = "UPDATE tipo_impuesto\n"
                    + "SET descripcion=?, porcentaje=?\n"
                    + "WHERE cod_impuesto=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion());
            ps.setString(2, dto.getPorcentaje());
            ps.setInt(3, dto.getCod_impuesto());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(ImpuestoDTO dto) {
        try {
            sql = "DELETE FROM tipo_impuesto\n"
                    + " WHERE cod_impuesto=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_impuesto());
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
            sql = "SELECT cod_impuesto, descripcion, porcentaje FROM tipo_impuesto ORDER BY cod_impuesto ASC;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println(rs);
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ImpuestoDTO();
                dto.setCod_impuesto(rs.getInt("cod_impuesto"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setPorcentaje(rs.getString("porcentaje"));
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
            sql = "SELECT cod_impuesto, descripcion, porcentaje FROM tipo_impuesto WHERE cod_impuesto=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_impuesto());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new ImpuestoDTO();
                dtoLocal.setCod_impuesto(rs.getInt("cod_impuesto"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                dtoLocal.setPorcentaje(rs.getString("porcentaje"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
