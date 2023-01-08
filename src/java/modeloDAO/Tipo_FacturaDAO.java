package modeloDAO;

import modeloDTO.Tipo_EmbarcacionDTO;
import modeloDTO.Tipo_FacturaDTO;
import programas.conexion;
import interfaces.Tipo_EmbarcacionINT;
import interfaces.Tipo_FacturaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tipo_FacturaDAO implements Tipo_FacturaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public Tipo_FacturaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(Tipo_FacturaDTO dto) {
        try {
            sql = "INSERT INTO tipo_factura(cod_tipofactura, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_tipofactura),0)+1 from tipo_factura), ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(Tipo_FacturaDTO dto) {
        try {
            sql = "UPDATE tipo_factura\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_tipofactura=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_tipofactura());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(Tipo_FacturaDTO dto) {
        try {
            sql = "DELETE FROM tipo_factura\n"
                    + "WHERE cod_tipofactura=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getCod_tipofactura());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<Tipo_FacturaDTO> seleccionarSegunFiltro(Tipo_FacturaDTO dto) {
        try {
            List<Tipo_FacturaDTO> lista;
            Tipo_FacturaDTO dtoLocal;
            sql = "SELECT cod_tipofactura, descripcion\n"
                    + "FROM tipo_factura"
                    + "WHERE cod_tipofactura=? and descripcion= ?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_tipofactura());
            ps.setString(2, dto.getDescripcion());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dtoLocal = new Tipo_FacturaDTO();
                dtoLocal.setCod_tipofactura(rs.getInt("cod_tipofactura"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                lista.add(dtoLocal);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public Tipo_FacturaDTO seleccionarSegunId(Tipo_FacturaDTO dto) {
        try {
            Tipo_FacturaDTO dtoLocal = null;
            sql = "SELECT cod_tipofactura, descripcion FROM tipo_factura WHERE cod_tipofactura=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_tipofactura());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new Tipo_FacturaDTO();
                dtoLocal.setCod_tipofactura(rs.getInt("cod_tipofactura"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<Tipo_FacturaDTO> seleccionarTodos() {
        try {
            List<Tipo_FacturaDTO> lista;
            Tipo_FacturaDTO dto;
            sql = "SELECT cod_tipofactura, descripcion\n"
                    + "  FROM tipo_factura ORDER BY cod_tipofactura ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Tipo_FacturaDTO();
                dto.setCod_tipofactura(rs.getInt("cod_tipofactura"));
                dto.setDescripcion(rs.getString("descripcion"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }
}
