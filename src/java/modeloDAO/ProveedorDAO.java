package modeloDAO;

import modeloDTO.ProveedorDTO;
import programas.conexion;
import interfaces.ProveedorINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            sql = "INSERT INTO proveedor(nro_proveedor, razon_social, ruc, telefono, direccion, cod_ciudad, cod_nacionalidad)\n"
                    + "VALUES ((select coalesce (max(nro_proveedor),0)+1 from proveedor), ?, ?, ?, ?, ?, ?);";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getRazon_social().toUpperCase());
            ps.setString(2, dto.getRuc().toUpperCase());
            ps.setString(3, dto.getTelefono().toUpperCase());
            ps.setString(4, dto.getDireccion().toUpperCase());
            ps.setInt(5, dto.getCod_ciudad());
            ps.setInt(6, dto.getCod_nacionalidad());
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
            sql = "UPDATE proveedor\n"
                    + "SET razon_social=?, ruc=?, telefono=?, direccion=?, cod_ciudad=?, cod_nacionalidad=?\n"
                    + "WHERE nro_proveedor=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getRazon_social().toUpperCase());
            ps.setString(2, dto.getRuc().toUpperCase());
            ps.setString(3, dto.getTelefono().toUpperCase());
            ps.setString(4, dto.getDireccion().toUpperCase());
            ps.setInt(5, dto.getCod_ciudad());
            ps.setInt(6, dto.getCod_nacionalidad());
            ps.setInt(7, dto.getNro_proveedor());
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
            sql = "DELETE FROM proveedor\n"
                    + "WHERE nro_proveedor=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getNro_proveedor());
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
            sql = "SELECT p.nro_proveedor, p.razon_social as proveedor, p.ruc, p.telefono, p.direccion, \n"
                    + "c.cod_ciudad, c.descripcion AS ciudad, \n"
                    + "n.cod_nacionalidad, n.descripcion AS nacionalidad\n"
                    + "FROM proveedor p\n"
                    + "INNER JOIN ciudad c ON p.cod_ciudad=c.cod_ciudad\n"
                    + "INNER JOIN nacionalidad n ON p.cod_nacionalidad=n.cod_nacionalidad\n"
                    + "ORDER BY nro_proveedor ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ProveedorDTO();
                dto.setNro_proveedor(rs.getInt("nro_proveedor"));
                dto.setRazon_social(rs.getString("proveedor"));
                dto.setRuc(rs.getString("ruc"));
                dto.setTelefono(rs.getString("telefono"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setCod_ciudad(rs.getInt("cod_ciudad"));
                dto.setDescripcion_ciudad(rs.getString("ciudad"));
                dto.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
                dto.setDescripcion_nacionalidad(rs.getString("nacionalidad"));
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
            sql = "SELECT nro_proveedor, razon_social, ruc, telefono, direccion, cod_ciudad, cod_nacionalidad\n"
                    + "FROM proveedor WHERE nro_proveedor=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getNro_proveedor());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new ProveedorDTO();
                dtoLocal.setNro_proveedor(rs.getInt("nro_proveedor"));
                dtoLocal.setRazon_social(rs.getString("razon_social"));
                dtoLocal.setRuc(rs.getString("ruc"));
                dtoLocal.setTelefono(rs.getString("telefono"));
                dtoLocal.setDireccion(rs.getString("direccion"));
                dtoLocal.setCod_ciudad(rs.getInt("cod_ciudad"));
                dtoLocal.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }
}
