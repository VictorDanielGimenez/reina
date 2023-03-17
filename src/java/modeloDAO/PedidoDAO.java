package modeloDAO;

import modeloDTO.PedidoDTO;
import programas.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.PedidoINT;
import java.sql.Date;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import modeloDTO.ArticuloDTO;
import programas.Genericos_fecha;

public class PedidoDAO implements PedidoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;
    programas.Genericos_fecha util = new Genericos_fecha();
    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat fecha2 = new SimpleDateFormat("yyyy/MM/dd");
    DecimalFormat formato = new DecimalFormat("###,###,###");

    public PedidoDAO() {
        conexion = new conexion();
    }

    @Override
    public List<PedidoDTO> UltimoID() {
        try {
            List<PedidoDTO> lista;
            PedidoDTO dto;
            sql = "SELECT (coalesce (max(`id_pedidocompra`),0)+1) id FROM `pedido_compra`;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new PedidoDTO();
                dto.setId_pedidocompra(rs.getInt("id"));
                lista.add(dto);
            }
            return lista;

        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }

    }

    @Override
    public Boolean agregar(PedidoDTO dto) {
        try {
            System.out.println("sddsfsdffgd");
            int idPedido;
            sql = "INSERT INTO `pedido_compra`(`ped_fecha_`, `ped_monto`, `id_estado`, `id_usuario`)"
                    + " VALUES(?,?,3,?);";
            ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, dto.getPed_fecha());
            ps.setString(2, dto.getPed_monto());
            ps.setInt(3, dto.getId_usuario());
            System.out.println("sddsfsdffgd");
            if (ps.executeUpdate() > 0) {
                System.out.println(ps);
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idPedido = rs.getInt(1);
                                  

                   

                  

                        for (PedidoDTO item : dto.getLista_articulo()) {                        

                            System.out.println("entra aca");
                         
                          
                            
                            sql = "INSERT INTO `detalle_pedido` (`id_articulo`, `id_pedidocompra`, `det_cant`, `det_precio`) VALUES (?, ?, ?, ?);";
                            ps = conexion.getConnection().prepareStatement(sql);

                            System.out.println(ps);
                            ps.setInt(1, item.getId_articulo());
                            ps.setInt(2, idPedido);
                            ps.setInt(3, item.getDet_cant());
                            ps.setInt(4, item.getDet_precio());
                            System.out.println(ps);
                            if (ps.executeUpdate() <= 0) {
                                //manejo de transaccion sql
                                return false;
                            }
                        }
                        return false;
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
    public Boolean modificar(PedidoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminar(PedidoDTO dto) {
       try {
            sql = "UPDATE `pedido_compra` SET `id_estado`= 4 WHERE `id_pedidocompra`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getId_pedidocompra());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<PedidoDTO> seleccionarTodos() {
        try {
            System.out.println("entra aca");
            List<PedidoDTO> lista;
            PedidoDTO dto;
            sql = "SELECT p.id_pedidocompra, p.ped_fecha_, p.ped_monto, e.est_descri, u.usu_nombre FROM pedido_compra p INNER JOIN estado e ON p.id_estado=e.id_estado INNER JOIN usuario u ON p.id_usuario=u.id_usuario;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new PedidoDTO();
                dto.setId_pedidocompra(rs.getInt("id_pedidocompra"));
                //convertir la fecha a dd/mm/yyyy

                dto.setPed_fecha(rs.getString("ped_fecha_"));
                dto.setPed_monto(rs.getString("ped_monto"));
                dto.setEst_descri(rs.getString("est_descri"));
                dto.setUsu_nombre(rs.getString("usu_nombre"));
                lista.add(dto);
            }

            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }

    }

    @Override
    public List<PedidoDTO> seleccionarSegunFiltro(PedidoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PedidoDTO seleccionarSegunId(PedidoDTO dto) {
        try {
            PedidoDTO dtoLocal = null;
            sql = "SELECT a.`id_articulo`, s.art_descri, a.`det_cant`, a.`det_precio` FROM `detalle_pedido` a INNER JOIN articulos s ON a.id_articulo=s.id_articulo WHERE a.`id_pedidocompra`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_pedidocompra());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new PedidoDTO();
                dtoLocal.setId_articulo(rs.getInt("id_articulo"));
                dtoLocal.setDet_cant(rs.getInt("art_descri"));
                dtoLocal.setDet_cant(rs.getInt("det_cant"));
                dtoLocal.setDet_precio(rs.getInt("det_precio"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
        
        
    }

    @Override
    public List<PedidoDTO> PedidoPendientes() {
         try {
            System.out.println("entra aca");
            List<PedidoDTO> lista;
            PedidoDTO dto;
            sql = "SELECT p.id_pedidocompra, p.ped_fecha_, p.ped_monto, e.est_descri, u.usu_nombre "
                    + "FROM pedido_compra p INNER JOIN estado e ON p.id_estado=e.id_estado INNER JOIN usuario u ON p.id_usuario=u.id_usuario"
                    + " WHERE p.id_estado=3;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new PedidoDTO();
                dto.setId_pedidocompra(rs.getInt("id_pedidocompra"));
                dto.setPed_fecha(rs.getString("ped_fecha_"));
                dto.setPed_monto(rs.getString("ped_monto"));
                dto.setEst_descri(rs.getString("est_descri"));
                dto.setUsu_nombre(rs.getString("usu_nombre"));
                lista.add(dto);
            }

            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

 

}
