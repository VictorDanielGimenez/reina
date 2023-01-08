package modeloDAO;

import modeloDTO.BarcazaDTO;
import programas.conexion;
import interfaces.BarcazaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarcazaDAO implements BarcazaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public BarcazaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(BarcazaDTO dto) {
        try {
            sql = "INSERT INTO barcaza(cod_barcaza, descripcion, matricula, arqueo_bruto, arqueo_neto, \n"
                    + "eslora, manga, puntal, codigo, cod_empresa, cod_nacionalidad)\n"
                    + "VALUES ((select coalesce (max(cod_barcaza),0)+1 from barcaza), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(1, dto.getDescripicion().toUpperCase().trim());
            ps.setString(2, dto.getMatricula().toUpperCase().trim());
            ps.setString(3, dto.getArqueo_bruto().toUpperCase().trim());
            ps.setString(4, dto.getArqueo_neto().toUpperCase().trim());
            ps.setString(5, dto.getEslora().toUpperCase().trim());
            ps.setString(6, dto.getManga().toUpperCase().trim());
            ps.setString(7, dto.getPuntal().toUpperCase().trim());
            ps.setInt(8, dto.getCodigo());
            ps.setInt(9, dto.getCod_empresa());
            ps.setInt(10, dto.getCod_nacionalidad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(BarcazaDTO dto) {
        try {
            sql = "UPDATE barcaza\n"
                    + "SET descripcion=?, matricula=?, arqueo_bruto=?, arqueo_neto=?, \n"
                    + "eslora=?, manga=?, puntal=?, codigo=?, cod_empresa=?, cod_nacionalidad=?\n"
                    + "WHERE cod_barcaza=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDescripicion().toUpperCase());
            ps.setString(2, dto.getMatricula().toUpperCase());
            ps.setString(3, dto.getArqueo_bruto().toUpperCase());
            ps.setString(4, dto.getArqueo_neto().toUpperCase());
            ps.setString(5, dto.getEslora().toUpperCase());
            ps.setString(6, dto.getManga().toUpperCase());
            ps.setString(7, dto.getPuntal().toUpperCase());
            ps.setInt(8, dto.getCodigo());
            ps.setInt(9, dto.getCod_empresa());
            ps.setInt(10, dto.getCod_nacionalidad());
            ps.setInt(11, dto.getCod_barcaza());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(BarcazaDTO dto) {
        try {
            sql = "DELETE FROM barcaza\n"
                    + "WHERE cod_barcaza=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getCod_barcaza());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<BarcazaDTO> seleccionarTodos() {
        try {
            List<BarcazaDTO> lista;
            BarcazaDTO dto;
            sql = "SELECT b.cod_barcaza, b.descripcion as barcaza, b.matricula, b.arqueo_bruto, b.arqueo_neto, b.eslora, b.puntal, b.manga,\n"
                    + "ti.codigo, ti.descripcion as tipo_barcaza,\n"
                    + "e.cod_empresa, e.descripcion as empresa,\n"
                    + "n.cod_nacionalidad, n.descripcion as nacionalidad\n"
                    + "FROM barcaza b\n"
                    + "inner join tipo_barcaza ti on ti.codigo = b.codigo\n"
                    + "inner join empresa e on e.cod_empresa = b.cod_empresa\n"
                    + "inner join nacionalidad n on n.cod_nacionalidad = b.cod_nacionalidad\n"
                    + "order by cod_barcaza asc;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new BarcazaDTO();
                dto.setCod_barcaza(rs.getInt("cod_barcaza"));
                dto.setDescripicion(rs.getString("barcaza"));
                dto.setMatricula(rs.getString("matricula"));
                dto.setArqueo_bruto(rs.getString("arqueo_bruto"));
                dto.setArqueo_neto(rs.getString("arqueo_neto"));
                dto.setEslora(rs.getString("eslora"));
                dto.setPuntal(rs.getString("puntal"));
                dto.setManga(rs.getString("manga"));
                dto.setCodigo(rs.getInt("codigo"));
                dto.setTipo_barcaza(rs.getString("tipo_barcaza"));
                dto.setCod_empresa(rs.getInt("cod_empresa"));
                dto.setEmpresa(rs.getString("empresa"));
                dto.setCod_nacionalidad(rs.getInt("cod_nacionalidad"));
                dto.setNacionalidad(rs.getString("nacionalidad"));

                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<BarcazaDTO> seleccionarSegunFiltro(BarcazaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BarcazaDTO seleccionarSegunId(BarcazaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
