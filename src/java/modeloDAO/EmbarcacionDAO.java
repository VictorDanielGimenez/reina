package modeloDAO;

import modeloDTO.EmbarcacionDTO;
import programas.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.EmbarcacionINT;

public class EmbarcacionDAO implements EmbarcacionINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public EmbarcacionDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(EmbarcacionDTO dto) {
        try {
            sql = "INSERT INTO embarcacion(cod_empuje, descripcion, cod_tipo, matricula, letras, eslora, manga, puntal, calado, cod_nacionalidad, cod_empresa)\n"
                    + "VALUES ((select coalesce (max(cod_empuje),0)+1 from embarcacion), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(1, dto.getDescripcion().toUpperCase().trim());
            ps.setInt(2, dto.getCod_tipo());
            ps.setString(3, dto.getMatricula().toUpperCase().trim());
            ps.setString(4, dto.getLetras().toUpperCase().trim());
            ps.setString(5, dto.getEslora().toUpperCase().trim());
            ps.setString(6, dto.getManga().toUpperCase().trim());
            ps.setString(7, dto.getPuntal().toUpperCase().trim());
            ps.setString(8, dto.getCalado().toUpperCase().trim());
            ps.setInt(9, dto.getCod_nacionalidad());
            ps.setInt(10, dto.getCod_empresa());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(EmbarcacionDTO dto) {
        try {
            sql = "UPDATE embarcacion SET descripcion=?, cod_tipo=?, matricula=?, letras=?, \n"
                    + "eslora=?, manga=?, puntal=?, calado=?, cod_nacionalidad=?, cod_empresa=?\n"
                    + "WHERE cod_empuje=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripcion().toUpperCase());
            ps.setInt(2, dto.getCod_tipo());
            ps.setString(3, dto.getMatricula());
            ps.setString(4, dto.getLetras());
            ps.setString(5, dto.getEslora());
            ps.setString(6, dto.getManga());
            ps.setString(7, dto.getPuntal());
            ps.setString(8, dto.getCalado());
            ps.setInt(9, dto.getCod_nacionalidad());
            ps.setInt(10, dto.getCod_empresa());
            ps.setInt(11, dto.getCod_empuje());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(EmbarcacionDTO dto) {
        try {
            sql = "DELETE FROM embarcacion\n"
                    + "WHERE cod_empuje=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_empuje());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<EmbarcacionDTO> seleccionarTodos() {
        try {
            List<EmbarcacionDTO> lista;
            EmbarcacionDTO dto;
            sql = "SELECT e.cod_empuje, e.descripcion AS embarcacion,  t.cod_tipo, t.descripcion AS tipo_empuje,\n"
                    + "e.matricula, e.letras, e.eslora, e.manga, e.puntal, e.calado,\n"
                    + "n.cod_nacionalidad, n.descripcion as bandera,\n"
                    + "em.cod_empresa, em.descripcion as empresa\n"
                    + "FROM embarcacion e\n"
                    + "INNER JOIN tipo_embarcacion t ON e.cod_tipo = t.cod_tipo\n"
                    + "INNER JOIN nacionalidad n ON e.cod_nacionalidad = n.cod_nacionalidad\n"
                    + "INNER JOIN empresa em ON e.cod_empresa = em.cod_empresa\n"
                    + "ORDER BY cod_empuje ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new EmbarcacionDTO();
                dto.setCod_empuje(rs.getInt("cod_empuje"));
                dto.setDescripcion(rs.getString("embarcacion"));
                dto.setCod_tipo(rs.getInt("cod_tipo"));
                dto.setDescripcion_tipo(rs.getString("tipo_empuje"));
                dto.setMatricula(rs.getString("matricula"));
                dto.setLetras(rs.getString("letras"));
                dto.setEslora(rs.getString("eslora"));
                dto.setManga(rs.getString("manga"));
                dto.setPuntal(rs.getString("puntal"));
                dto.setCalado(rs.getString("calado"));
                dto.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
                dto.setDescripcion_nacionalidad(rs.getString("bandera"));
                dto.setCod_empresa(rs.getInt("cod_empresa"));
                dto.setDescripcion_empresa(rs.getString("empresa"));
                
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<EmbarcacionDTO> seleccionarSegunFiltro(EmbarcacionDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmbarcacionDTO seleccionarSegunId(EmbarcacionDTO dto) {
        try {
            EmbarcacionDTO dtoLocal = null;
            sql = "SELECT cod_empuje, descripcion, cod_tipo FROM embarcacion WHERE embarcacion=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_empuje());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new EmbarcacionDTO();
                dtoLocal.setCod_empuje(rs.getInt("cod_empuje"));
                dtoLocal.setDescripcion(rs.getString("descripcion"));
                dtoLocal.setCod_tipo(rs.getInt("cod_tipo"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
