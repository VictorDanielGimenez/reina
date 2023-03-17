package modeloDAO;

import modeloDTO.CompraDTO;
import programas.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.CompraINT;
import java.sql.Date;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import modeloDTO.ArticuloDTO;
import programas.Genericos_fecha;

public class CompraDAO implements CompraINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;
    programas.Genericos_fecha util = new Genericos_fecha();
    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat fecha2 = new SimpleDateFormat("yyyy/MM/dd");
    DecimalFormat formato = new DecimalFormat("###,###,###");

    public CompraDAO() {
        conexion = new conexion();
    }

    @Override
    public List<CompraDTO> UltimoID() {
        try {
            List<CompraDTO> lista;
            CompraDTO dto;
            sql = "SELECT (coalesce (max(`id_compra`),0)+1) id FROM `compra`;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CompraDTO();
                dto.setId_compra(rs.getInt("id"));
                lista.add(dto);
            }
            return lista;

        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<CompraDTO> DetalleOrden(CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean agregar(CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean modificar(CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminar(CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CompraDTO> seleccionarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CompraDTO> seleccionarSegunFiltro(CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CompraDTO seleccionarSegunId(CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

}
