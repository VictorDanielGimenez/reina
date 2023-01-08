package modeloDAO;

import modeloDTO.NacionalidadDTO;
import programas.conexion;
import interfaces.NacionalidadINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NacionalidadDAO implements NacionalidadINT {

    private ResultSet rs; //permite trabajar con select
    private PreparedStatement ps; //ejecuta query de todas las sentencias
    private final conexion conexion;
    private String sql;
    private String msg;

    public NacionalidadDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(NacionalidadDTO dto) {
        try {
            sql = "INSERT INTO nacionalidad (cod_nacionalidad, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_nacionalidad),0)+1 from nacionalidad),?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(1, dto.getDescripcion().toUpperCase());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }

    }

    @Override
    public Boolean modificar(NacionalidadDTO dto) {
        try {
            sql = "UPDATE nacionalidad\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_nacionalidad=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_nacionalidad());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(NacionalidadDTO dto) {
        try {
            sql = "DELETE FROM nacionalidad\n"
                    + "WHERE cod_nacionalidad=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_nacionalidad());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<NacionalidadDTO> seleccionarTodos() {
        try {
            List<NacionalidadDTO> lista;
            NacionalidadDTO dto;
            sql = "SELECT cod_nacionalidad, descripcion\n"
                    + "  FROM nacionalidad ORDER BY cod_nacionalidad ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new NacionalidadDTO();
                dto.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
                dto.setDescripcion(rs.getString("descripcion"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<NacionalidadDTO> seleccionarSegunFiltro(NacionalidadDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NacionalidadDTO seleccionarSegunId(NacionalidadDTO dto) {
        try {
            NacionalidadDTO dtoLocal = null;
            sql = "SELECT cod_nacionalidad, descripcion FROM nacionalidad WHERE cod_nacionalidad=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_nacionalidad());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new NacionalidadDTO();
                dtoLocal.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
