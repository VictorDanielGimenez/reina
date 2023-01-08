package modeloDAO;

import modeloDTO.CiudadDTO;
import modeloDTO.DepartamentoDTO;
import programas.conexion;
import interfaces.DepartamentoINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO implements DepartamentoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public DepartamentoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(DepartamentoDTO dto) {
        try {
            sql = "INSERT INTO departamento(cod_departamento, descripcion)\n"
                    + "VALUES ((select coalesce (max(cod_departamento),0)+1 from departamento), ?);";
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
    public Boolean modificar(DepartamentoDTO dto) {
        try {
            sql = "UPDATE departamento\n"
                    + "SET descripcion=?\n"
                    + "WHERE cod_departamento=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_departamento());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(DepartamentoDTO dto) {
        try {
            sql = "DELETE FROM departamento\n"
                    + " WHERE cod_departamento=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_departamento());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<DepartamentoDTO> seleccionarTodos() {
        try {
            List<DepartamentoDTO> lista;
            DepartamentoDTO dto;
            sql = "SELECT cod_departamento, descripcion\n"
                    + "FROM departamento;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new DepartamentoDTO();
                dto.setCod_departamento(rs.getInt("cod_departamento"));
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
    public List<DepartamentoDTO> seleccionarSegunFiltro(DepartamentoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DepartamentoDTO seleccionarSegunId(DepartamentoDTO dto) {
        try {
            DepartamentoDTO dtoLocal = null;
            sql = "SELECT cod_departamento, descripcion FROM departamento WHERE cod_departamento=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_departamento());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new DepartamentoDTO();
                dtoLocal.setCod_departamento(rs.getInt("cod_departamento"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }
}