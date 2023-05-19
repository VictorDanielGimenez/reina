package modeloDAO;

import modeloDTO.OrdenDTO;
import programas.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.OrdenINT;
import java.sql.Date;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import modeloDTO.ArticuloDTO;
import programas.Genericos_fecha;

public class OrdenDAO implements OrdenINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;
    programas.Genericos_fecha util = new Genericos_fecha();
    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat fecha2 = new SimpleDateFormat("yyyy/MM/dd");
    DecimalFormat formato = new DecimalFormat("###,###,###");

    public OrdenDAO() {
        conexion = new conexion();
    }

    @Override
    public List<OrdenDTO> UltimoID() {
        try {
            List<OrdenDTO> lista;
            OrdenDTO dto;
            sql = "SELECT (coalesce (max(`id_orden`),0)+1) id FROM `orden_compra`;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new OrdenDTO();
                dto.setId_orden(rs.getInt("id"));
                lista.add(dto);
            }
            return lista;

        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }

    }

    @Override
    public Boolean agregar(OrdenDTO dto) {
        try {
            int idOrden;
            sql = "INSERT INTO `orden_compra`(`id_usuario`, `id_pedidocompra`, `id_proveedor`, `orden_fecha`, `orden_monto`, `id_estado`) "
                    + "VALUES (?,?,?,?,?,3);";
            ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getId_usuario());
            ps.setInt(2, dto.getId_pedidocompra());
            ps.setInt(3, dto.getId_proveedor());
            ps.setString(4, dto.getOrden_fecha());
            ps.setString(5, dto.getOrden_monto());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idOrden = rs.getInt(1);

                    for (OrdenDTO item : dto.getLista_articulo()) {

                        sql = "INSERT INTO `detalle_orden` (`id_articulo`, `id_orden`, `det_cant`, `det_precio`) VALUES (?, ?, ?, ?);";
                        ps = conexion.getConnection().prepareStatement(sql);
                        ps.setInt(1, item.getId_articulo());
                        ps.setInt(2, idOrden);
                        ps.setInt(3, item.getDet_cant());
                        ps.setInt(4, item.getDet_precio());
                        if (ps.executeUpdate() <= 0) {
                            // manejo de transaccion sql
                            return false;
                        }

                    }

                    return false;
                }

            } else {
                // manejo de transaccion sql
                return false;
            }

        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Error en agregar " + msg);
            return false;
        }
        // operacion exitosa...
        // setIdPedido(idPedido);
        return true;
    }

    @Override
    public Boolean modificar(OrdenDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminar(OrdenDTO dto) {
        try {
            sql = "UPDATE `orden_compra` SET `id_estado`= 4 WHERE `id_orden`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getId_orden());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<OrdenDTO> seleccionarTodos() {
        try {
            System.out.println("entra aca");
            List<OrdenDTO> lista;
            OrdenDTO dto;
            sql = "SELECT o.id_orden, u.usu_nombre, CONCAT(p.prov_razons,' - ', p.prov_ruc) prove, o.orden_fecha, o.id_pedidocompra, o.orden_monto, e.est_descri FROM orden_compra o INNER JOIN usuario u ON o.id_usuario=u.id_usuario INNER JOIN proveedor p ON o.id_proveedor=p.id_proveedor INNER JOIN estado e ON o.id_estado=e.id_estado;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new OrdenDTO();
                dto.setId_orden(rs.getInt("id_orden"));
                dto.setUsu_nombre(rs.getString("usu_nombre"));
                dto.setRazon(rs.getString("prove"));
                dto.setOrden_fecha(rs.getString("orden_fecha"));
                dto.setOrden_monto(rs.getString("orden_monto"));
                dto.setId_pedidocompra(rs.getInt("id_pedidocompra"));
                dto.setEst_descri(rs.getString("est_descri"));
                lista.add(dto);
            }

            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }

    }

    @Override
    public List<OrdenDTO> seleccionarSegunFiltro(OrdenDTO dto) {
        try {
            List<OrdenDTO> lista;
            OrdenDTO dtoLocal = null;
            sql = "SELECT p.id_proveedor, p2.prov_razons, p2.prov_ruc FROM orden_compra p INNER JOIN proveedor p2 ON p.id_proveedor = p2.id_proveedor WHERE p.id_orden =?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_orden());       
            rs = ps.executeQuery();          
            lista = new ArrayList<>();
            while (rs.next()) {
                dtoLocal = new OrdenDTO();
                dtoLocal.setId_proveedor(rs.getInt("id_proveedor"));
                dtoLocal.setProveedor(rs.getString("prov_razons") + " - " + rs.getString("prov_ruc"));               
                lista.add(dtoLocal);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Json " + msg);
            return null;
        }
        
    }

    @Override
    public OrdenDTO seleccionarSegunId(OrdenDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Date sdf(Date ped_fecha) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<OrdenDTO> DetallePedido(OrdenDTO dto) {
        try {
            List<OrdenDTO> lista;
            OrdenDTO dtoLocal = null;
            sql = "SELECT d.`id_articulo`,a.`art_descri`, d.`det_cant`,d.`det_precio` "
                    + "FROM `detalle_pedido` d INNER JOIN `articulos` a ON d.`id_articulo`=a.`id_articulo` "
                    + "WHERE d.`id_pedidocompra`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_pedidocompra());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dtoLocal = new OrdenDTO();
                dtoLocal.setId_articulo(rs.getInt("id_articulo"));
                dtoLocal.setArt_descri(rs.getString("art_descri"));
                dtoLocal.setDet_cant(rs.getInt("det_cant"));
                dtoLocal.setDet_precio(rs.getInt("det_precio"));
                lista.add(dtoLocal);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    public List<OrdenDTO> DetalleOrden(OrdenDTO dto) {  
        try {
            List<OrdenDTO> lista;
            OrdenDTO dtoLocal = null;
            sql = "SELECT a.`id_articulo`, \n"
                    + "       s.art_descri, \n"
                    + "       a.`det_cant`, \n"
                    + "       a.`det_precio`,\n"
                    + "       CASE \n"
                    + "           WHEN s.`id_impuesto` = 3 THEN CAST(0 AS DECIMAL) \n"
                    + "           ELSE CAST(0 AS DECIMAL) \n"
                    + "       END AS exenta,\n"
                    + "       CASE \n"
                    + "           WHEN s.`id_impuesto` = 1 THEN CAST((a.`det_precio` * a.`det_cant`)*0.05 AS DECIMAL)\n"
                    + "           ELSE CAST(0 AS DECIMAL) \n"
                    + "       END AS iva5,\n"
                    + "       CASE \n"
                    + "           WHEN s.`id_impuesto` = 2 THEN CAST((a.`det_precio` * a.`det_cant`)*0.10 AS DECIMAL)\n"
                    + "           ELSE CAST(0 AS DECIMAL) \n"
                    + "       END AS iva10\n"
                    + "FROM `detalle_orden` a \n"
                    + "INNER JOIN articulos s ON a.`id_articulo`=s.`id_articulo`\n"
                    + "WHERE a.`id_orden` =?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_orden());       
            rs = ps.executeQuery();          
            lista = new ArrayList<>();
            while (rs.next()) {
                dtoLocal = new OrdenDTO();
                dtoLocal.setId_articulo(rs.getInt("id_articulo"));
                dtoLocal.setArt_descri(rs.getString("art_descri"));
                dtoLocal.setDet_cant(rs.getInt("det_cant"));
                dtoLocal.setDet_precio(rs.getInt("det_precio"));
                dtoLocal.setExenta(rs.getInt("exenta"));
                dtoLocal.setIva5(rs.getInt("iva5"));
                dtoLocal.setIva10(rs.getInt("iva10"));
                lista.add(dtoLocal);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            System.out.println("Json " + msg);
            return null;
        }
    }

    public List<OrdenDTO> OrdenPendientes() {
        try {
            System.out.println("entra aca");
            List<OrdenDTO> lista;
            OrdenDTO dto;
            sql = "SELECT p.id_orden , p.orden_fecha, p.orden_monto, e.est_descri, u.usu_nombre, p.id_proveedor, p2.prov_razons, p2.prov_ruc  \n"
                    + "FROM orden_compra p \n"
                    + "INNER JOIN estado e ON p.id_estado=e.id_estado \n"
                    + "INNER JOIN usuario u ON p.id_usuario=u.id_usuario\n"
                    + "INNER JOIN proveedor p2 ON p.id_proveedor = p2.id_proveedor \n"
                    + "WHERE p.id_estado=3";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new OrdenDTO();
                dto.setId_orden(rs.getInt("id_orden"));
                dto.setOrden_fecha(rs.getString("orden_fecha"));
                dto.setOrden_monto(rs.getString("orden_monto"));
                dto.setEst_descri(rs.getString("est_descri"));
                dto.setUsu_nombre(rs.getString("usu_nombre"));
                dto.setId_proveedor(rs.getInt("id_proveedor"));
                dto.setProveedor(rs.getString("prov_razons") + " - " + rs.getString("prov_ruc"));
                lista.add(dto);
            }

            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

}
