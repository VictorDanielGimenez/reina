package modeloDAO;

import modeloDTO.PuertoDTO;
import programas.conexion;
import interfaces.PuertoINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PuertoDAO implements PuertoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public PuertoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(PuertoDTO dto) {
        try {
            sql = "INSERT INTO puerto(cod_puerto, descripcion, cod_ciudad, cod_nacionalidad)\n"
                    + "VALUES ((select coalesce (max(cod_puerto),0)+1 from puerto), ?, ?, ?);";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_empresa());
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_ciudad());
            ps.setInt(3, dto.getCod_nacionalidad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(PuertoDTO dto) {
        try {
            sql = "UPDATE puerto\n"
                    + "SET descripcion=?, cod_ciudad=?, cod_nacionalidad=?\n"
                    + "WHERE cod_puerto=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_ciudad());
            ps.setInt(3, dto.getCod_nacionalidad());
            ps.setInt(4, dto.getCod_puerto());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(PuertoDTO dto) {
        try {
            sql = "DELETE FROM puerto\n"
                    + "WHERE cod_puerto=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getCod_puerto());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<PuertoDTO> seleccionarTodos() {
        try {
            List<PuertoDTO> lista;
            PuertoDTO dto;
            sql = "SELECT p.cod_puerto, p.descripcion as puerto, \n"
                    + "c.cod_ciudad, c.descripcion as ciudad,\n"
                    + "n.cod_nacionalidad, n.descripcion as nacionalidad\n"
                    + "FROM puerto p\n"
                    + "INNER JOIN ciudad c ON p.cod_ciudad=c.cod_ciudad\n"
                    + "INNER JOIN nacionalidad n ON p.cod_nacionalidad=n.cod_nacionalidad\n"
                    + "ORDER BY cod_puerto ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new PuertoDTO();
                dto.setCod_puerto(rs.getInt("cod_puerto"));
                dto.setDescripcion(rs.getString("puerto"));
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
    public List<PuertoDTO> seleccionarSegunFiltro(PuertoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PuertoDTO seleccionarSegunId(PuertoDTO dto) {
        try {
            PuertoDTO dtoLocal = null;
            sql = "SELECT cod_puerto, descripcion, cod_ciudad, cod_nacionalidad\n"
                    + "FROM puerto WHERE cod_puerto=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_puerto());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new PuertoDTO();
                dtoLocal.setCod_puerto(rs.getInt("cod_puerto"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
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
