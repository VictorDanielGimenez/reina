package modeloDAO;

import modeloDTO.Factura_CompraDTO;
import modeloDTO.MercaderiaDTO;
import modeloDTO.Orden_CompraDTO;
import programas.conexion;
import programas.Genericos_fecha;
import interfaces.Factura_CompraINT;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Factura_CompraDAO implements Factura_CompraINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;
    programas.Genericos_fecha util = new Genericos_fecha();
    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat fecha2 = new SimpleDateFormat("yyyy/MM/dd");
    DecimalFormat formato = new DecimalFormat("###,###");

    public Factura_CompraDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(Factura_CompraDTO dto) {
        try {
            int idFacturaCompra;
            sql = "INSERT INTO factura_compra(cod_factura, nro_orden, cod_tipofactura, nro_proveedor, fecha_creacion, \n"
                    + "fac_timbrado, fac_vtotimbrado, nro_factura, fecha_compra, fac_total, fac_totaliva, \n"
                    + "cod_empleado, cod_deposito, estado)\n"
                    + "VALUES ((select coalesce (max(cod_factura),0)+1 from factura_compra), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getNro_orden());
            ps.setInt(2, dto.getCod_tipofactura());
            ps.setInt(3, dto.getNro_proveedor());
            ps.setDate(4, Date.valueOf(dto.getFecha_creacion().trim()));
            ps.setString(5, dto.getFac_timbrado());
            ps.setDate(6, Date.valueOf(dto.getFecha_creacion().trim()));
            ps.setString(7, dto.getNro_factura().trim());
            ps.setDate(8, Date.valueOf(dto.getFecha_compra().trim()));
            ps.setInt(9, Integer.parseInt(dto.getFac_total().replace(".", "")));
            ps.setInt(10, Integer.parseInt(dto.getFac_totaliva().replace(".", "")));

            if (ps.executeUpdate() > 0) {
                System.out.println(ps);
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idFacturaCompra = rs.getInt(1);
                    for (MercaderiaDTO item : dto.getLista_mercaderia()) {
                        sql = "INSERT INTO factura_compra_detalle(cod_factura, cod_mercaderiaw, cod_stok, det_cantidad, det_monto, det_iva, det_exenta, det_gravada)\n"
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                        ps = conexion.getConnection().prepareStatement(sql);
                        System.out.println(ps);
                        ps.setInt(1, idFacturaCompra);
                        ps.setInt(2, item.getCod_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, Integer.parseInt(item.getPrecio_unitario().replace(".", "")));
                        if (ps.executeUpdate() <= 0) {
                            System.out.println(ps);
                            //manejo de transaccion sql
                            return false;
                        }
                    }
                }
            } else {
                //manejo de transaccion sql
                return false;
            }
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error en agregar " + msg);
            return false;
        }
        //operacion exitosa...
//        setIdPedido(idPedido);
        return true;
    }

    @Override
    public Boolean modificar(Factura_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean eliminar(Factura_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Factura_CompraDTO> seleccionarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Factura_CompraDTO> seleccionarSegunFiltro(Factura_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Factura_CompraDTO seleccionarSegunId(Factura_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Orden_CompraDTO> recuperarOrdenCompra(Orden_CompraDTO dto) {
        //System.out.println("Get Id " + dto.getNro_solicitud());
        List<Orden_CompraDTO> lista;
        Orden_CompraDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {
            sql = "SELECT o.nro_orden, o.fecha, o.nro_proveedor, p.razon_social,\n"
                    + "o.cod_empleado, (RTRIM(e.nombre)||' '|| LTRIM(e.apellido)) AS empleado, \n"
                    + "(select RTRIM(emp.nombre)||' '|| LTRIM(emp.apellido) from empleado emp where emp.cod_empleado=s.cod_empleado) as empleado_solicitante,\n"
                    + "o.nro_solicitud, s.descripcion AS solicitud_compra,\n"
                    + "o.cod_empresa, em.descripcion AS empresa, o.condicion_compra,\n"
                    + "m.cod_mercaderia, m.descripcion AS mercaderia, om.cantidad, m.precio_unitario,\n"
                    + "u.cod_medida, u.descripcion AS medida,\n"
                    + "ma.cod_marca, ma.descripcion AS marca,\n"
                    + "(om.cantidad * m.precio_unitario) as subtotal,\n"
                    + "(select (m.precio_unitario * i.porcentaje/100) as impuesto),\n"
                    + "(select coalesce (sum(om.cantidad * om.precio_unitario))as total from ordencompra_mercaderia om where o.nro_orden=om.nro_orden)\n"
                    + "FROM orden_compra o\n"
                    + "INNER JOIN proveedor p ON o.nro_proveedor = p.nro_proveedor\n"
                    + "INNER JOIN empleado e ON o.cod_empleado = e.cod_empleado\n"
                    + "INNER JOIN solicitud_compra s ON o.nro_solicitud = s.nro_solicitud\n"
                    + "INNER JOIN empresa em ON o.cod_empresa = em.cod_empresa\n"
                    + "INNER JOIN ordencompra_mercaderia om ON om.nro_orden=o.nro_orden\n"
                    + "INNER JOIN mercaderia m ON om.cod_mercaderia=m.cod_mercaderia\n"
                    + "inner join tipo_impuesto i on i.cod_impuesto=m.cod_impuesto\n"
                    + "INNER JOIN marca ma ON m.cod_marca=ma.cod_marca\n"
                    + "INNER JOIN unidad_medida u ON m.cod_medida=u.cod_medida\n"
                    + "WHERE o.nro_orden= ? AND estado_compra = 'TRUE'";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getNro_orden());
            rs = ps.executeQuery();
            // System.out.println(rs);
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
//            item = new Orden_CompraDTO();
            while (rs.next()) {
                item = new Orden_CompraDTO();
                item.setNro_orden(rs.getInt("nro_orden"));
                item.setFecha(rs.getDate("fecha").toString());
                item.setNro_solicitud(rs.getInt("nro_solicitud"));
                item.setSolicitud_descripcion(rs.getString("solicitud_compra"));
                item.setCod_empleado(rs.getInt("cod_empleado"));
                item.setEmpleado_descripcion(rs.getString("empleado"));
                item.setEmpleado_solicitante(rs.getString("empleado_solicitante"));
                item.setCod_empresa(rs.getInt("cod_empresa"));
                item.setEmpresa_descripcion(rs.getString("empresa"));
                item.setNro_proveedor(rs.getInt("nro_proveedor"));
                item.setProveedor_descripcion(rs.getString("razon_social"));
                item.setCondicion_compra(rs.getString("condicion_compra"));

                //para el reporte
                item.setCod_mercaderia(rs.getInt("cod_mercaderia"));
                item.setDescripcion_medida(rs.getString("medida"));
                item.setArticulo(rs.getString("mercaderia"));
                item.setMarca_descripcion(rs.getString("marca"));
                item.setCantidad(rs.getInt("cantidad"));
                item.setPrecio_unitario(formato.format(rs.getInt("precio_unitario")));
                item.setSubtotal(formato.format(rs.getInt("subtotal")));
                item.setImpuesto_descripcion(formato.format(rs.getString("impuesto")));
//                item.setTotal(formato.format(rs.getInt("total")));

                //lista para js
//                itemMercaderia = new MercaderiaDTO();
//                itemMercaderia.setCod_mercaderia(rs.getInt("cod_mercaderia"));
//                itemMercaderia.setDescripcion_medida(rs.getString("medida"));
//                itemMercaderia.setArticulo(rs.getString("mercaderia"));
//                itemMercaderia.setDescripcion_marca(rs.getString("marca"));
//                itemMercaderia.setCantidad(rs.getInt("cantidad"));
//                itemMercaderia.setPrecio_unitario(formato.format(rs.getInt("precio_unitario")));
//                itemMercaderia.setSubtotal(formato.format(rs.getInt("subtotal")));
//                listaMercaderia.add(itemMercaderia);
//                item.setLista_mercaderia(listaMercaderia);
                lista.add(item);
            }
//            lista.add(item);
            return lista;
        } catch (SQLException ex) {
            System.out.println("error al recuperar " + ex.getMessage());
            Logger.getLogger(Solicitud_CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
