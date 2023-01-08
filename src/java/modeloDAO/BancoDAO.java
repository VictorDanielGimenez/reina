package modeloDAO;

import modeloDTO.BancoDTO;
import programas.conexion;
import interfaces.BancoINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BancoDAO implements BancoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public BancoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(BancoDTO dto) {
        try {
            sql = "INSERT INTO banco(cod_banco, descripcion, ruc, telefono, direccion, correo, cod_ciudad, cod_nacionalidad)\n"
                    + "VALUES ((select coalesce (max(cod_banco),0)+1 from banco), ?, ?, ?, ?, ?, ?, ?);";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setString(2, dto.getRuc());
            ps.setString(3, dto.getTelefono());
            ps.setString(4, dto.getDireccion().toUpperCase());
            ps.setString(5, dto.getCorreo().toLowerCase());
            ps.setInt(6, dto.getCod_ciudad());
            ps.setInt(7, dto.getCod_nacionalidad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(BancoDTO dto) {
        try {
            sql = "UPDATE banco\n"
                    + "SET descripcion=?, ruc=?, telefono=?, direccion=?, correo=?, cod_ciudad=?, cod_nacionalidad=?\n"
                    + "WHERE cod_banco=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setString(2, dto.getRuc());
            ps.setString(3, dto.getTelefono());
            ps.setString(4, dto.getDireccion().toUpperCase());
            ps.setString(5, dto.getCorreo());
            ps.setInt(6, dto.getCod_ciudad());
            ps.setInt(7, dto.getCod_nacionalidad());
            ps.setInt(8, dto.getCod_banco());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(BancoDTO dto) {
        try {
            sql = "DELETE FROM banco\n"
                    + "WHERE cod_banco=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_banco());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<BancoDTO> seleccionarTodos() {
        try {
            List<BancoDTO> lista;
            BancoDTO dto;
            sql = "SELECT b.cod_banco, b.descripcion, b.ruc, b.telefono, b.direccion, b.correo, \n"
                    + "c.cod_ciudad, c.descripcion AS ciudad, n.cod_nacionalidad, n.descripcion AS nacionalidad\n"
                    + "FROM banco b\n"
                    + "INNER JOIN ciudad c ON b.cod_ciudad=c.cod_ciudad\n"
                    + "INNER JOIN nacionalidad n ON b.cod_nacionalidad=n.cod_nacionalidad\n"
                    + "ORDER BY cod_banco ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new BancoDTO();
                dto.setCod_banco(rs.getInt("cod_banco"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setRuc(rs.getString("ruc"));
                dto.setTelefono(rs.getString("telefono"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setCorreo(rs.getString("correo"));
                dto.setCod_ciudad(rs.getInt("cod_ciudad"));
                dto.setCiudad_descripcion(rs.getString("ciudad"));
                dto.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
                dto.setNacionalidad_descripcion(rs.getString("nacionalidad"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<BancoDTO> seleccionarSegunFiltro(BancoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BancoDTO seleccionarSegunId(BancoDTO dto) {
        try {
            BancoDTO dtoLocal = null;
            sql = "SELECT cod_banco, descripcion, ruc, telefono, direccion, correo, cod_ciudad, cod_nacionalidad\n"
                    + "FROM banco WHERE cod_banco=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_banco());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new BancoDTO();
                dtoLocal.setCod_banco(rs.getInt("cod_banco"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                dtoLocal.setRuc(rs.getString("ruc"));
                dtoLocal.setTelefono(rs.getString("telefono"));
                dtoLocal.setDireccion(rs.getString("direccion"));
                dtoLocal.setCorreo(rs.getString("correo"));
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
