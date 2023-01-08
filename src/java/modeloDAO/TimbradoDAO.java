package modeloDAO;

import modeloDTO.TimbradoDTO;
import programas.conexion;
import programas.Genericos_fecha;
import interfaces.TimbradoINT;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TimbradoDAO implements TimbradoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    programas.Genericos_fecha util = new Genericos_fecha();
    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat fecha2 = new SimpleDateFormat("yyyy/MM/dd");
    DecimalFormat formato = new DecimalFormat("###,###,###");

    public TimbradoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(TimbradoDTO dto) {
        try {
            sql = "INSERT INTO timbrado(cod_timbrado, nro_timbrado, fecha_venc, fec_inicio, nro_proveedor)\n"
                    + "VALUES ((select coalesce (max(cod_timbrado),0)+1 from timbrado), ?, ?, ?, ?);";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getNro_timbrado());
            ps.setDate(2, Date.valueOf(dto.getFecha_venc()));
            ps.setDate(3, Date.valueOf(dto.getFec_inicio()));
            ps.setInt(4, dto.getNro_proveedor());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(TimbradoDTO dto) {
        try {
            sql = "UPDATE timbrado\n"
                    + "SET nro_timbrado=?, fecha_venc=?, fec_inicio=?, nro_proveedor=?\n"
                    + "WHERE cod_timbrado=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getNro_timbrado());
            ps.setDate(2, Date.valueOf(dto.getFecha_venc().trim()));
            ps.setDate(3, Date.valueOf(dto.getFec_inicio().trim()));
            ps.setInt(4, dto.getNro_proveedor());
            ps.setInt(5, dto.getCod_timbrado());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(TimbradoDTO dto) {
        try {
            sql = "DELETE FROM timbrado\n"
                    + "WHERE cod_timbrado=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getCod_timbrado());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<TimbradoDTO> seleccionarTodos() {
        try {
            List<TimbradoDTO> lista;
            TimbradoDTO dto;
            sql = "SELECT t.cod_timbrado as codigo, t.nro_timbrado as timbrado, t.fec_inicio as fecha_inicio, t.fecha_venc as fecha_vencimiento, \n"
                    + "p.nro_proveedor as nro_proveedor, p.razon_social as proveedor, p.ruc as ruc\n"
                    + "FROM timbrado t\n"
                    + "inner join proveedor p on p.nro_proveedor = t.nro_proveedor\n"
                    + "order by cod_timbrado asc";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new TimbradoDTO();
                dto.setCod_timbrado(rs.getInt("codigo"));
                dto.setNro_timbrado(rs.getInt("timbrado"));
                dto.setFec_inicio(fecha.format(Date.valueOf(rs.getString("fecha_inicio"))));
                dto.setFecha_venc(fecha.format(Date.valueOf(rs.getString("fecha_vencimiento"))));
                dto.setNro_proveedor(rs.getInt("nro_proveedor"));
                dto.setDescripcion_proveedor(rs.getString("proveedor"));
                dto.setRuc_proveedor(rs.getString("ruc"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<TimbradoDTO> seleccionarSegunFiltro(TimbradoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TimbradoDTO seleccionarSegunId(TimbradoDTO dto) {
        try {
            TimbradoDTO dtoLocal = null;
            sql = "SELECT t.cod_timbrado as codigo, t.nro_timbrado as timbrado, t.fec_inicio as fecha_inicio, t.fecha_venc as fecha_vencimiento, \n"
                    + "p.nro_proveedor as nro_proveedor, p.razon_social as proveedor, p.ruc as ruc\n"
                    + "FROM timbrado t\n"
                    + "inner join proveedor p on p.nro_proveedor = t.nro_proveedor\n"
                    + "where cod_timbrado=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_timbrado());
            System.out.println(ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new TimbradoDTO();
                dtoLocal.setCod_timbrado(rs.getInt("codigo"));
                dtoLocal.setNro_proveedor(rs.getInt("nro_proveedor"));
                dtoLocal.setDescripcion_proveedor(rs.getString("proveedor"));
                dtoLocal.setNro_timbrado(rs.getInt("timbrado"));
                dtoLocal.setFec_inicio(fecha.format(Date.valueOf(rs.getString("fecha_inicio"))));
                dtoLocal.setFecha_venc(fecha.format(Date.valueOf(rs.getString("fecha_vencimiento"))));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }
}
