package modeloDAO;

import modeloDTO.MercaderiaDTO;
import modeloDTO.Solicitud_CompraDTO;
import programas.conexion;
import programas.Genericos_fecha;
import interfaces.Solicitud_CompraINT;
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

public class Solicitud_CompraDAO implements Solicitud_CompraINT {

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

    public Solicitud_CompraDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(Solicitud_CompraDTO dto) {
        try {
            int idPedido;
            sql = "INSERT INTO solicitud_compra(nro_solicitud, descripcion, fecha, prioridad, cod_departamento, cod_empleado, estado, cod_deposito)\n"
                    + "VALUES ((select coalesce (max(nro_solicitud),0)+1 from solicitud_compra), ?, ?, ?::type_prioridad, ?, ?, 'TRUE', ?);";
            ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getDescripcion().toUpperCase().trim());
            ps.setDate(2, Date.valueOf(dto.getFecha()));
            ps.setString(3, dto.getPrioridad());
            ps.setInt(4, dto.getCod_departamento());
            ps.setInt(5, dto.getCod_empleado());
            ps.setInt(6, dto.getCod_deposito());
            if (ps.executeUpdate() > 0) {
                System.out.println(ps);
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idPedido = rs.getInt(1);
                    for (MercaderiaDTO item : dto.getLista_mercaderia()) {
                        sql = "INSERT INTO solicitud_mercaderia(nro_solicitud, cod_mercaderia, cantidad)\n"
                                + "VALUES (?, ?, ?);";
                        ps = conexion.getConnection().prepareStatement(sql);
                        System.out.println(ps);
                        ps.setInt(1, idPedido);
                        ps.setInt(2, item.getCod_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        System.out.println(ps);
                        if (ps.executeUpdate() <= 0) {
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
    public Boolean modificar(Solicitud_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean eliminar(Solicitud_CompraDTO dto) {
        try {
            sql = "update solicitud_compra set estado = 'FALSE' Where nro_solicitud=?";
            //UPDATE public.pedidos set estado=True WHERE id=3;
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getNro_solicitud());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Solicitud_CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Solicitud_CompraDTO> seleccionarTodos() {
        try {
            List<Solicitud_CompraDTO> lista;
            Solicitud_CompraDTO dto;
            sql = "SELECT s.nro_solicitud, s.descripcion, s.fecha, s.prioridad, d.cod_departamento,\n"
                    + "d.descripcion AS departamento,\n"
                    + "s.cod_empleado, (RTRIM(e.nombre)||' '|| LTRIM(e.apellido)) AS empleado,\n"
                    + "de.cod_deposito, de.descripcion AS deposito, su.descripcion AS sucursal\n"
                    + "FROM solicitud_compra s\n"
                    + "INNER JOIN departamento d ON s.cod_departamento=d.cod_departamento\n"
                    + "INNER JOIN empleado e ON s.cod_empleado=e.cod_empleado\n"
                    + "INNER JOIN deposito de ON s.cod_deposito= de.cod_deposito\n"
                    + "INNER JOIN sucursal su ON de.cod_sucursal = su.cod_sucursal\n"
                    + "WHERE s.estado='TRUE'\n"
                    + "ORDER BY nro_solicitud ASC;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println(rs);
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Solicitud_CompraDTO();
                dto.setNro_solicitud(rs.getInt("nro_solicitud"));
                dto.setFecha(rs.getDate("fecha").toString());
                dto.setCod_empleado(rs.getInt("cod_empleado"));
                dto.setEmpleado_descripcion(rs.getString("empleado"));
                dto.setCod_departamento(rs.getInt("cod_departamento"));
                dto.setDepartamento_descripcion(rs.getString("departamento"));
                dto.setCod_deposito(rs.getInt("cod_deposito"));
                dto.setDeposito_descripcion(rs.getString("deposito"));
                dto.setSucursal_descripcion(rs.getString("sucursal"));
                dto.setPrioridad(rs.getString("prioridad"));
                dto.setDescripcion(rs.getString("descripcion"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error al recuperar cabecera: " + msg);
            return null;
        }
    }

    @Override
    public List<Solicitud_CompraDTO> seleccionarSegunFiltro(Solicitud_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Solicitud_CompraDTO> recuperarPedido(Solicitud_CompraDTO dto) {
        //System.out.println("Get Id " + dto.getNro_solicitud());
        List<Solicitud_CompraDTO> lista;
        Solicitud_CompraDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {
            sql = "SELECT s.nro_solicitud, s.descripcion, s.fecha, s.prioridad,\n"
                    + "d.cod_departamento, d.descripcion AS departamento,\n"
                    + "e.cod_empleado, (RTRIM(e.nombre)||' '|| LTRIM(e.apellido)) AS empleado,\n"
                    + "s.estado,\n"
                    + "m.cod_mercaderia, m.descripcion AS mercaderia, sm.cantidad, m.precio_unitario,\n"
                    + "u.cod_medida, u.descripcion AS medida,\n"
                    + "ma.cod_marca, ma.descripcion AS marca,\n"
                    + "(sm.cantidad * m.precio_unitario) as subtotal,\n"
                    + "de.cod_deposito, de.descripcion AS deposito,\n"
                    + "su.cod_sucursal, su.descripcion AS sucursal\n"
                    + "FROM solicitud_compra s\n"
                    + "INNER JOIN departamento d ON s.cod_departamento=d.cod_departamento\n"
                    + "INNER JOIN empleado e ON s.cod_empleado=e.cod_empleado\n"
                    + "INNER JOIN solicitud_mercaderia sm ON s.nro_solicitud=sm.nro_solicitud\n"
                    + "INNER JOIN mercaderia m ON sm.cod_mercaderia=m.cod_mercaderia\n"
                    + "INNER JOIN marca ma ON m.cod_marca=ma.cod_marca\n"
                    + "INNER JOIN unidad_medida u ON m.cod_medida=u.cod_medida\n"
                    + "INNER JOIN deposito de ON s.cod_deposito=de.cod_deposito\n"
                    + "INNER JOIN sucursal su ON de.cod_sucursal=su.cod_sucursal\n"
                    + "WHERE s.nro_solicitud= ? and s.estado='TRUE'";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getNro_solicitud());
            rs = ps.executeQuery();
            // System.out.println(rs);
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
//            item = new Solicitud_CompraDTO();
            while (rs.next()) {
                item = new Solicitud_CompraDTO();
                item.setNro_solicitud(rs.getInt("nro_solicitud"));
                item.setFecha(rs.getDate("fecha").toString());
                item.setCod_empleado(rs.getInt("cod_empleado"));
                item.setEmpleado_descripcion(rs.getString("empleado"));
                item.setCod_departamento(rs.getInt("cod_departamento"));
                item.setDepartamento_descripcion(rs.getString("departamento"));
                item.setCod_deposito(rs.getInt("cod_deposito"));
                item.setDeposito_descripcion(rs.getString("deposito"));
                item.setCod_sucursal(rs.getInt("cod_sucursal"));
                item.setSucursal_descripcion(rs.getString("sucursal"));
                item.setPrioridad(rs.getString("prioridad"));
                item.setDescripcion(rs.getString("descripcion"));

                //para el reporte
                item.setCod_mercaderia(rs.getInt("cod_mercaderia"));
                item.setDescripcion_medida(rs.getString("medida"));
                item.setArticulo(rs.getString("mercaderia"));
                item.setMarca_descripcion(rs.getString("marca"));
                item.setCantidad(rs.getInt("cantidad"));
                item.setPrecio_unitario(formato.format(rs.getInt("precio_unitario")));
                item.setSubtotal(formato.format(rs.getInt("subtotal")));

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

    @Override
    public Solicitud_CompraDTO seleccionarSegunId(Solicitud_CompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
