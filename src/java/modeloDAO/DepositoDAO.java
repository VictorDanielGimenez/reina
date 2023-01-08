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
            sql = "INSERT INTO deposito(cod_deposito, descripcion, cod_sucursal)\n"
                    + "VALUES ((select coalesce (max(cod_deposito),0)+1 from deposito), ?, ?);";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_empresa());
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_sucursal());
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
            sql = "UPDATE deposito\n"
                    + "SET descripcion=?, cod_sucursal=?\n"
                    + "WHERE cod_deposito=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_sucursal());
            ps.setInt(3, dto.getCod_deposito());
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
            sql = "DELETE FROM deposito\n"
                    + "WHERE cod_deposito=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getCod_deposito());
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
            sql = "SELECT d.cod_deposito, d.descripcion, s.cod_sucursal, s.descripcion AS sucursal\n"
                    + "FROM deposito d\n"
                    + "INNER JOIN sucursal s ON d.cod_sucursal = s.cod_sucursal\n"
                    + "ORDER BY cod_deposito ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new DepositoDTO();
                dto.setCod_deposito(rs.getInt("cod_deposito"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setCod_sucursal(rs.getInt("cod_sucursal"));
                dto.setSucursal_descripcion(rs.getString("sucursal"));
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
            sql = "SELECT cod_deposito, descripcion, cod_sucursal\n"
                    + "  FROM deposito WHERE cod_deposito=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_deposito());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new DepositoDTO();
                dtoLocal.setCod_deposito(rs.getInt("cod_empresa"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                dtoLocal.setCod_sucursal(rs.getInt("cod_sucursal"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
