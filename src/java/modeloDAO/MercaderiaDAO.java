package modeloDAO;

import modeloDTO.MercaderiaDTO;
import programas.conexion;
import interfaces.MercaderiaINT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MercaderiaDAO implements MercaderiaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;
    DecimalFormat formato = new DecimalFormat("###,###");
    // = new DecimalFormat("#####.00");

    public MercaderiaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(MercaderiaDTO dto) {
        try {
            sql = "INSERT INTO mercaderia(cod_mercaderia, descripcion, cantidad, precio_unitario, cod_impuesto, cod_sucursal, cod_deposito, cod_marca, cod_moneda, cod_medida)\n"
                    + "VALUES ((select coalesce (max(cod_mercaderia),0)+1 from mercaderia), ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
//            ps.setInt(1, dto.getCod_empleado());
            ps.setString(1, dto.getArticulo().toUpperCase());
            ps.setInt(2, dto.getCantidad());
            ps.setInt(3, Integer.parseInt(dto.getPrecio_unitario().replace(".", "")));
            ps.setInt(4, dto.getCod_impuesto());
            ps.setInt(5, dto.getCod_sucursal());
            ps.setInt(6, dto.getCod_deposito());
            ps.setInt(7, dto.getCod_marca());
            ps.setInt(8, dto.getCod_moneda());
            ps.setInt(9, dto.getCod_medida());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(MercaderiaDTO dto) {
        try {
            sql = "UPDATE mercaderia\n"
                    + "SET descripcion=?, cantidad=?, precio_unitario=?, \n"
                    + "cod_impuesto=?, cod_sucursal=?, cod_deposito=?, cod_marca=?, \n"
                    + "cod_moneda=?, cod_medida=?\n"
                    + "WHERE cod_mercaderia=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getArticulo().toUpperCase());
            ps.setInt(2, dto.getCantidad());
            ps.setInt(3, Integer.parseInt(dto.getPrecio_unitario().replace(".", "")));
            ps.setInt(4, dto.getCod_impuesto());
            ps.setInt(5, dto.getCod_sucursal());
            ps.setInt(6, dto.getCod_deposito());
            ps.setInt(7, dto.getCod_marca());
            ps.setInt(8, dto.getCod_moneda());
            ps.setInt(9, dto.getCod_medida());
            ps.setInt(10, dto.getCod_mercaderia());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(MercaderiaDTO dto) {
        try {
            sql = "DELETE FROM mercaderia\n"
                    + " WHERE cod_mercaderia=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getCod_mercaderia());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<MercaderiaDTO> seleccionarTodos() {
        try {
            List<MercaderiaDTO> lista;
            MercaderiaDTO dto;
            sql = "SELECT m.cod_mercaderia, m.descripcion AS articulo, m.cantidad, m.precio_unitario, \n"
                    + "i.cod_impuesto, i.descripcion AS impuesto, \n"
                    + "s.cod_sucursal, s.descripcion AS sucursal, \n"
                    + "d.cod_deposito, d.descripcion AS deposito,\n"
                    + "ma.cod_marca, ma.descripcion AS marca, \n"
                    + "mo.cod_moneda, mo.descripcion AS moneda,\n"
                    + "me.cod_medida, me.descripcion AS medida,\n"
                    + "case \n"
                    + "when (i.porcentaje=10) then (m.precio_unitario * i.porcentaje/100) else 0 end impuesto_10,\n"
                    + "case \n"
                    + "when (i.porcentaje=5) then (m.precio_unitario * i.porcentaje/100) else 0 end impuesto_5,\n"
                    + "case \n"
                    + "when (i.porcentaje=0) then (m.precio_unitario * i.porcentaje/100) else 0  end excento\n"
                    + "FROM mercaderia m\n"
                    + "INNER JOIN tipo_impuesto i ON m.cod_impuesto=i.cod_impuesto\n"
                    + "INNER JOIN sucursal s ON m.cod_sucursal=s.cod_sucursal\n"
                    + "INNER JOIN deposito d ON m.cod_deposito=d.cod_deposito\n"
                    + "INNER JOIN marca ma ON m.cod_marca=ma.cod_marca\n"
                    + "INNER JOIN moneda mo ON m.cod_moneda=mo.cod_moneda\n"
                    + "INNER JOIN unidad_medida me ON m.cod_medida=me.cod_medida ORDER BY cod_mercaderia ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println(ps);
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MercaderiaDTO();
                dto.setCod_mercaderia(rs.getInt("cod_mercaderia"));
                dto.setArticulo(rs.getString("articulo"));
                dto.setCantidad(rs.getInt("cantidad"));

                dto.setPrecio_unitario(formato.format(rs.getInt("precio_unitario")));
//                dto.setPrecio_unitario(rs.getString("precio_unitario"));
//                dto.setPrecio_unitario(rs.getString(Integer.parseInt("precio_unitario")).replace(".", ""));

                //Impuesto
                dto.setCod_deposito(rs.getInt("cod_impuesto"));
                dto.setDescripcion_impuesto(rs.getString("impuesto"));
                dto.setExento(rs.getString("excento"));
                dto.setIva10(rs.getString("impuesto_10"));
                dto.setIva5(rs.getString("impuesto_5"));
                //Sucursal
                dto.setCod_sucursal(rs.getInt("cod_sucursal"));
                dto.setDescripcion_sucursal(rs.getString("sucursal"));
                //Deposito
                dto.setCod_impuesto(rs.getInt("cod_deposito"));
                dto.setDescripcion_deposito(rs.getString("deposito"));
                //Marca
                dto.setCod_marca(rs.getInt("cod_marca"));
                dto.setDescripcion_marca(rs.getString("marca"));
                //Moneda
                dto.setCod_moneda(rs.getInt("cod_moneda"));
                dto.setDescripcion_moneda(rs.getString("moneda"));
                //Unidad_Medida
                dto.setCod_medida(rs.getInt("cod_medida"));
                dto.setDescripcion_medida(rs.getString("medida"));

                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<MercaderiaDTO> seleccionarSegunFiltro(MercaderiaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MercaderiaDTO seleccionarSegunId(MercaderiaDTO dto) {
        try {
            MercaderiaDTO dtoLocal = null;
            sql = "SELECT m.cod_mercaderia, m.descripcion AS articulo, m.cantidad, m.precio_unitario,\n"
                    + "i.cod_impuesto, i.descripcion AS impuesto,\n"
                    + "s.cod_sucursal, s.descripcion AS sucursal,\n"
                    + "d.cod_deposito, d.descripcion AS deposito,\n"
                    + "ma.cod_marca, ma.descripcion AS marca,\n"
                    + "mo.cod_moneda, mo.descripcion AS moneda,\n"
                    + "me.cod_medida, me.descripcion AS medida\n"
                    + "FROM mercaderia m\n"
                    + "INNER JOIN tipo_impuesto i ON m.cod_impuesto=i.cod_impuesto\n"
                    + "INNER JOIN sucursal s ON m.cod_sucursal=s.cod_sucursal\n"
                    + "INNER JOIN deposito d ON m.cod_deposito=d.cod_deposito\n"
                    + "INNER JOIN marca ma ON m.cod_marca=ma.cod_marca\n"
                    + "INNER JOIN moneda mo ON m.cod_moneda=mo.cod_moneda\n"
                    + "INNER JOIN unidad_medida me ON m.cod_medida=me.cod_medida\n"
                    + "WHERE m.cod_mercaderia=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getCod_mercaderia());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new MercaderiaDTO();
                dtoLocal.setCod_mercaderia(rs.getInt("cod_mercaderia"));
                dtoLocal.setArticulo(rs.getString("articulo"));
                dtoLocal.setCantidad(rs.getInt("cantidad"));
//                formato = new DecimalFormat("###,###");
                dtoLocal.setPrecio_unitario(formato.format(rs.getInt("precio_unitario")));
                dtoLocal.setCod_impuesto(rs.getInt("cod_impuesto"));
                dtoLocal.setDescripcion_impuesto(rs.getString("impuesto"));
                dtoLocal.setCod_sucursal(rs.getInt("cod_sucursal"));
                dtoLocal.setDescripcion_sucursal(rs.getString("sucursal"));
                dtoLocal.setCod_deposito(rs.getInt("cod_deposito"));
                dtoLocal.setDescripcion_deposito(rs.getString("deposito"));
                dtoLocal.setCod_marca(rs.getInt("cod_marca"));
                dtoLocal.setDescripcion_marca(rs.getString("marca"));
                dtoLocal.setCod_moneda(rs.getInt("cod_moneda"));
                dtoLocal.setDescripcion_moneda(rs.getString("moneda"));
                dtoLocal.setCod_medida(rs.getInt("cod_medida"));
                dtoLocal.setDescripcion_medida(rs.getString("medida"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
