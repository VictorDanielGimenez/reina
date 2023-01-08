package modeloDAO;

import modeloDTO.MercaderiaDTO;
import modeloDTO.Orden_CompraDTO;
import programas.conexion;
import programas.Genericos_fecha;
import interfaces.Orden_CompraINT;
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

public class Orden_CompraDAO implements Orden_CompraINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;
    programas.Genericos_fecha util = new Genericos_fecha();
    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat fecha2 = new SimpleDateFormat("yyyy/MM/dd");
    DecimalFormat formato = new DecimalFormat("###,###,###");
//    private int idPedido;

    public Orden_CompraDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(Orden_CompraDTO dto) {
        try {
            int idCompra;
            sql = "INSERT INTO orden_compra(nro_orden, fecha, nro_proveedor, cod_empleado, estado_compra, nro_solicitud, cod_empresa, condicion_compra)\n"
                    + "VALUES ((select coalesce (max(nro_orden),0)+1 from orden_compra), ?, ?, ?, 'TRUE', ?, ?, ?::type_condicion);";
            ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(dto.getFecha()));
            ps.setInt(2, dto.getNro_proveedor());
            ps.setInt(3, dto.getCod_empleado());
            ps.setInt(4, dto.getNro_solicitud());
            ps.setInt(5, dto.getCod_empresa());
            ps.setString(6, dto.getCondicion_compra());
            if (ps.executeUpdate() > 0) {
                System.out.println(ps);
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idCompra = rs.getInt("nro_orden");
                    for (MercaderiaDTO item : dto.getLista_mercaderia()) {
                        sql = "INSERT INTO ordencompra_mercaderia(nro_orden, cod_mercaderia, cantidad, precio_unitario)\n"
                                + "VALUES (?, ?, ?, ?);";
                        ps = conexion.getConnection().prepareStatement(sql);
                        System.out.println(ps);
                        ps.setInt(1, idCompra);
                        ps.setInt(2, item.getCod_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, Integer.parseInt(item.getPrecio_unitario().replace(".", "")));
                        if (ps.executeUpdate() <= 0) {
                            System.out.println(ps);
                            //manejo de transaccion sql
                            return false;
                        }
                        sql = "UPDATE solicitud_compra\n"
                                + "SET estado='FALSE'\n"
                                + "WHERE nro_solicitud=?";
                        ps = conexion.getConnection().prepareStatement(sql);
                        ps.setInt(1, dto.getNro_solicitud());
                        if (ps.executeUpdate() <= 0) {
//                            conexion.Transaccion(conexion.TR.CANCELAR);
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
    public Boolean modificar(Orden_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean eliminar(Orden_CompraDTO dto) {
        try {
            sql = "update orden_compra set estado_compra = 'FALSE' Where nro_orden=?";
            //UPDATE public.pedidos set estado=True WHERE id=3;
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getNro_orden());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Solicitud_CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Orden_CompraDTO> seleccionarTodos() {
        try {
            List<Orden_CompraDTO> lista;
            Orden_CompraDTO dto;
            sql = "SELECT o.nro_orden, o.fecha, \n"
                    + "p.nro_proveedor, p.razon_social,\n"
                    + "(RTRIM(e.nombre)||' '|| LTRIM(e.apellido)) AS empleado,\n"
                    + "o.nro_solicitud, s.descripcion AS solicitud_compra,\n"
                    + "em.descripcion AS empresa,\n"
                    + "o.condicion_compra,\n"
                    + "(select coalesce (sum(om.cantidad * om.precio_unitario))as total from ordencompra_mercaderia om where o.nro_orden=om.nro_orden)\n"
                    + "FROM orden_compra o\n"
                    + "INNER JOIN proveedor p ON o.nro_proveedor = p.nro_proveedor\n"
                    + "INNER JOIN empleado e ON o.cod_empleado = e.cod_empleado\n"
                    + "INNER JOIN solicitud_compra s ON o.nro_solicitud = s.nro_solicitud\n"
                    + "INNER JOIN empresa em ON o.cod_empresa = em.cod_empresa\n"
                    + "WHERE estado_compra = 'TRUE' ORDER BY nro_orden ASC";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println(rs);
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Orden_CompraDTO();
                dto.setNro_orden(rs.getInt("nro_orden"));
                dto.setFecha(rs.getDate("fecha").toString());
                dto.setNro_proveedor(rs.getInt("nro_proveedor"));
                dto.setProveedor_descripcion(rs.getString("razon_social"));
                dto.setEmpleado_descripcion(rs.getString("empleado"));
                dto.setNro_solicitud(rs.getInt("nro_solicitud"));
                dto.setSolicitud_descripcion(rs.getString("solicitud_compra"));
                dto.setEmpresa_descripcion(rs.getString("empresa"));
                dto.setCondicion_compra(rs.getString("condicion_compra"));
                dto.setTotal(formato.format(rs.getInt("total")));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error al recuperar cabecera: " + msg);
            return null;
        }
    }

    public List<Orden_CompraDTO> recuperarOrdenCompra(Orden_CompraDTO dto) {
        //System.out.println("Get Id " + dto.getNro_solicitud());
        List<Orden_CompraDTO> lista;
        Orden_CompraDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {
            sql = "SELECT o.nro_orden, o.fecha, o.nro_proveedor, p.razon_social,\n"
                    + "                    o.cod_empleado, (RTRIM(e.nombre)||' '|| LTRIM(e.apellido)) AS empleado, \n"
                    + "                    (select RTRIM(emp.nombre)||' '|| LTRIM(emp.apellido) from empleado emp where emp.cod_empleado=s.cod_empleado) as empleado_solicitante,\n"
                    + "                    o.nro_solicitud, s.descripcion AS solicitud_compra,\n"
                    + "                    o.cod_empresa, em.descripcion AS empresa, o.condicion_compra,\n"
                    + "                    m.cod_mercaderia, m.descripcion AS mercaderia, om.cantidad, m.precio_unitario,\n"
                    + "                    u.cod_medida, u.descripcion AS medida,\n"
                    + "                    ma.cod_marca, ma.descripcion AS marca,\n"
                    + "                    (om.cantidad * m.precio_unitario) as subtotal,\n"
                    + "                    (select coalesce (sum(om.cantidad * om.precio_unitario))as total from ordencompra_mercaderia om where o.nro_orden=om.nro_orden),\n"
                    + "                    case \n"
                    + "                    when (i.porcentaje=10) then ((m.precio_unitario*om.cantidad) * i.porcentaje/100) else 0 end impuesto_10,\n"
                    + "                    case \n"
                    + "                    when (i.porcentaje=5) then ((m.precio_unitario*om.cantidad) * i.porcentaje/100) else 0 end impuesto_5,\n"
                    + "                    case \n"
                    + "                    when (i.porcentaje=0) then ((m.precio_unitario*om.cantidad) * i.porcentaje/100) else 0  end excento\n"
                    + "                    FROM orden_compra o\n"
                    + "                    INNER JOIN proveedor p ON o.nro_proveedor = p.nro_proveedor\n"
                    + "                    INNER JOIN empleado e ON o.cod_empleado = e.cod_empleado\n"
                    + "                    INNER JOIN solicitud_compra s ON o.nro_solicitud = s.nro_solicitud\n"
                    + "                    INNER JOIN empresa em ON o.cod_empresa = em.cod_empresa\n"
                    + "                    INNER JOIN ordencompra_mercaderia om ON om.nro_orden=o.nro_orden\n"
                    + "                    INNER JOIN mercaderia m ON om.cod_mercaderia=m.cod_mercaderia\n"
                    + "                    inner join tipo_impuesto i on i.cod_impuesto=m.cod_impuesto\n"
                    + "                    INNER JOIN marca ma ON m.cod_marca=ma.cod_marca\n"
                    + "                    INNER JOIN unidad_medida u ON m.cod_medida=u.cod_medida\n"
                    + "                    WHERE o.nro_orden= ? AND estado_compra = 'TRUE'";
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
                item.setTotal(formato.format(rs.getInt("total")));
                item.setExento(formato.format(rs.getInt("excento")));
                item.setIva10(formato.format(rs.getInt("impuesto_10")));
                item.setIva5(formato.format(rs.getInt("impuesto_5")));
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

//    public List<Orden_CompraDTO> recuperarReporteOrdenCompra(Orden_CompraDTO dto) {
//        //System.out.println("Get Id " + dto.getNro_solicitud());
//        List<Orden_CompraDTO> lista;
//        Orden_CompraDTO item;
//        List<MercaderiaDTO> listaMercaderia;
//        MercaderiaDTO itemMercaderia;
//        try {
//            sql = "SELECT o.nro_orden, o.fecha, o.nro_proveedor, p.razon_social,\n"
//                    + "o.cod_empleado, (RTRIM(e.nombre)||' '|| LTRIM(e.apellido)) AS empleado, \n"
//                    + "(select RTRIM(emp.nombre)||' '|| LTRIM(emp.apellido) from empleado emp where emp.cod_empleado=s.cod_empleado) as empleado_solicitante,\n"
//                    + "o.nro_solicitud, s.descripcion AS solicitud_compra,\n"
//                    + "o.cod_empresa, em.descripcion AS empresa, o.condicion_compra,\n"
//                    + "m.cod_mercaderia, m.descripcion AS mercaderia, om.cantidad, m.precio_unitario,\n"
//                    + "u.cod_medida, u.descripcion AS medida,\n"
//                    + "ma.cod_marca, ma.descripcion AS marca,\n"
//                    + "(om.cantidad * m.precio_unitario) as subtotal,\n"
//                    + "(select sum(om.cantidad * om.precio_unitario) from ordencompra_mercaderia om) as total\n"
//                    + "FROM orden_compra o\n"
//                    + "INNER JOIN proveedor p ON o.nro_proveedor = p.nro_proveedor\n"
//                    + "INNER JOIN empleado e ON o.cod_empleado = e.cod_empleado\n"
//                    + "INNER JOIN solicitud_compra s ON o.nro_solicitud = s.nro_solicitud\n"
//                    + "INNER JOIN empresa em ON o.cod_empresa = em.cod_empresa\n"
//                    + "INNER JOIN ordencompra_mercaderia om ON om.nro_orden=o.nro_orden\n"
//                    + "INNER JOIN mercaderia m ON om.cod_mercaderia=m.cod_mercaderia\n"
//                    + "INNER JOIN marca ma ON m.cod_marca=ma.cod_marca\n"
//                    + "INNER JOIN unidad_medida u ON m.cod_medida=u.cod_medida\n"
//                    + "WHERE o.nro_orden= ? AND estado_compra = 'TRUE'";
//            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getNro_orden());
//            rs = ps.executeQuery();
//            // System.out.println(rs);
//            lista = new ArrayList<>();
//            listaMercaderia = new ArrayList<>();
//            item = new Orden_CompraDTO();
//            while (rs.next()) {
////                item = new Orden_CompraDTO();
//                item.setNro_orden(rs.getInt("nro_orden"));
//                item.setFecha(rs.getDate("fecha").toString());
//                item.setNro_solicitud(rs.getInt("nro_solicitud"));
//                item.setSolicitud_descripcion(rs.getString("solicitud_compra"));
//                item.setCod_empleado(rs.getInt("cod_empleado"));
//                item.setEmpleado_descripcion(rs.getString("empleado"));
//                item.setEmpleado_solicitante(rs.getString("empleado_solicitante"));
//                item.setCod_empresa(rs.getInt("cod_empresa"));
//                item.setEmpresa_descripcion(rs.getString("empresa"));
//                item.setNro_proveedor(rs.getInt("nro_proveedor"));
//                item.setProveedor_descripcion(rs.getString("razon_social"));
//                item.setCondicion_compra(rs.getString("condicion_compra"));
//
//                //para el reporte
//                item.setCod_mercaderia(rs.getInt("cod_mercaderia"));
//                item.setDescripcion_medida(rs.getString("medida"));
//                item.setArticulo(rs.getString("mercaderia"));
//                item.setMarca_descripcion(rs.getString("marca"));
//                item.setCantidad(rs.getInt("cantidad"));
//                item.setPrecio_unitario(formato.format(rs.getInt("precio_unitario")));
//                item.setSubtotal(formato.format(rs.getInt("subtotal")));
//                item.setTotal(formato.format(rs.getInt("total")));
//
//                //lista para js
////                itemMercaderia = new MercaderiaDTO();
////                itemMercaderia.setCod_mercaderia(rs.getInt("cod_mercaderia"));
////                itemMercaderia.setDescripcion_medida(rs.getString("medida"));
////                itemMercaderia.setArticulo(rs.getString("mercaderia"));
////                itemMercaderia.setDescripcion_marca(rs.getString("marca"));
////                itemMercaderia.setCantidad(rs.getInt("cantidad"));
////                itemMercaderia.setPrecio_unitario(formato.format(rs.getInt("precio_unitario")));
////                itemMercaderia.setSubtotal(formato.format(rs.getInt("subtotal")));
////                listaMercaderia.add(itemMercaderia);
////                item.setLista_mercaderia(listaMercaderia);
//
//            }
//            lista.add(item);
//            return lista;
//        } catch (SQLException ex) {
//            System.out.println("error al recuperar " + ex.getMessage());
//            Logger.getLogger(Solicitud_CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    @Override
    public List<Orden_CompraDTO> seleccionarSegunFiltro(Orden_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Orden_CompraDTO seleccionarSegunId(Orden_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
