package modeloDAO;

import modeloDTO.ClienteDTO;
import programas.conexion;
import interfaces.ClienteINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements ClienteINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public ClienteDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(ClienteDTO dto) {
        try {
            sql = "INSERT INTO cliente(cod_cliente, razon_social, ruc, direccion, telefono, cod_nacionalidad, cod_ciudad)\n"
                    + "VALUES ((select coalesce (max(cod_cliente),0)+1 from cliente), ?, ?, ?, ?, ?, ?);";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getRazon_social().toUpperCase());
            ps.setString(2, dto.getRuc().toUpperCase());
            ps.setString(3, dto.getDireccion().toUpperCase());
            ps.setString(4, dto.getTelefono().toUpperCase());
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
    public Boolean modificar(ClienteDTO dto) {
        try {
            sql = "UPDATE cliente\n"
                    + "SET razon_social=?, ruc=?, telefono=?, direccion=?, cod_ciudad=?, cod_nacionalidad=?\n"
                    + "WHERE cod_cliente=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getRazon_social().toUpperCase());
            ps.setString(2, dto.getRuc().toUpperCase());
            ps.setString(3, dto.getTelefono().toUpperCase());
            ps.setString(4, dto.getDireccion().toUpperCase());
            ps.setInt(5, dto.getCod_ciudad());
            ps.setInt(6, dto.getCod_nacionalidad());
            ps.setInt(7, dto.getCod_cliente());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(ClienteDTO dto) {
        try {
            sql = "DELETE FROM cliente\n"
                    + "WHERE cod_cliente=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_cliente());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<ClienteDTO> seleccionarTodos() {
        try {
            List<ClienteDTO> lista;
            ClienteDTO dto;
            sql = "SELECT cl.cod_cliente, cl.razon_social, cl.ruc, cl.telefono, cl.direccion,\n"
                    + "c.cod_ciudad, c.descripcion AS ciudad,\n"
                    + "n.cod_nacionalidad, n.descripcion AS nacionalidad\n"
                    + "FROM cliente cl\n"
                    + "INNER JOIN ciudad c ON cl.cod_ciudad=c.cod_ciudad\n"
                    + "INNER JOIN nacionalidad n ON cl.cod_nacionalidad=n.cod_nacionalidad\n"
                    + "ORDER BY cod_cliente ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ClienteDTO();
                dto.setCod_cliente(rs.getInt("cod_cliente"));
                dto.setRazon_social(rs.getString("razon_social"));
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
    public List<ClienteDTO> seleccionarSegunFiltro(ClienteDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClienteDTO seleccionarSegunId(ClienteDTO dto) {
        try {
            ClienteDTO dtoLocal = null;
            sql = "SELECT cod_cliente, razon_social, ruc, direccion, telefono, cod_nacionalidad, cod_ciudad\n"
                    + "FROM cliente WHERE cod_cliente=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_cliente());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new ClienteDTO();
                dtoLocal.setCod_cliente(rs.getInt("cod_cliente"));
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
