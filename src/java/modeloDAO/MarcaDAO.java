package modeloDAO;

import modeloDTO.MarcaDTO;
import programas.conexion;
import interfaces.MarcaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO implements MarcaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public MarcaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(MarcaDTO dto) {
        try {
            sql = "INSERT INTO marca(cod_marca, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_marca),0)+1 from marca), ?);";
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
    public Boolean modificar(MarcaDTO dto) {
        try {
            sql = "UPDATE marca\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_marca=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_marca());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(MarcaDTO dto) {
        try {
            sql = "DELETE FROM marca\n"
                    + "WHERE cod_marca=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_marca());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<MarcaDTO> seleccionarTodos() {
        try {
            List<MarcaDTO> lista;
            MarcaDTO dto;
            sql = "SELECT cod_marca, descripcion\n"
                    + "FROM marca ORDER BY cod_marca ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MarcaDTO();
                dto.setCod_marca(rs.getInt("cod_marca"));
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
    public List<MarcaDTO> seleccionarSegunFiltro(MarcaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MarcaDTO seleccionarSegunId(MarcaDTO dto) {
        try {
            MarcaDTO dtoLocal = null;
            sql = "SELECT cod_marca, descripcion FROM marca WHERE cod_marca=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_marca());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new MarcaDTO();
                dtoLocal.setCod_marca(rs.getInt("cod_marca"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
