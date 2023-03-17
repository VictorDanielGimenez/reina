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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

}
