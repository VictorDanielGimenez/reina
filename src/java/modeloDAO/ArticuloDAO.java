package modeloDAO;

import modeloDTO.ArticuloDTO;
import programas.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.ArticuloINT;
import java.sql.Statement;

public class ArticuloDAO implements ArticuloINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String sql2;
    private String sql3;
    private String msg;

    public ArticuloDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(ArticuloDTO dto) {
        try {
            int idarticulo;
            sql = "INSERT INTO `articulos` (`id_impuesto`, `id_marca`, `art_descri`, `art_precioc`, `art_preciov`) VALUES (?, ?, ?, ?, ?);";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getId_impuesto());
            ps.setInt(2, dto.getId_marca());
            ps.setString(3, dto.getArt_descri());
            ps.setString(4, dto.getArt_precioc());
            ps.setString(5, dto.getArt_preciov());

            if (ps.executeUpdate() > 0) {
                System.out.println(ps);
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idarticulo = rs.getInt(1);

                    sql = "INSERT INTO `stock` (`id_articulo`, `id_deposito`, `stock_cant`) VALUES (?, ?, ?);";
                    ps = conexion.getConnection().prepareStatement(sql);
                    System.out.println(ps);
                    ps.setInt(1, idarticulo);
                    ps.setInt(2, dto.getId_deposito());
                    ps.setInt(3, dto.getStock_cant());
                    if (ps.executeUpdate() <= 0) {
                        System.out.println(ps);
                        //manejo de transaccion sql
                        return false;
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
        return true;
    }

    @Override
    public Boolean modificar(ArticuloDTO dto) {
     try {
            sql = "UPDATE `articulos` SET `id_impuesto`=?,`id_marca`=?,`art_descri`=?,`art_precioc`=?,`art_preciov`=? WHERE `id_articulo`=?;";
   
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_impuesto());
            ps.setInt(2, dto.getId_marca());
            ps.setString(3, dto.getArt_descri());
            ps.setString(4, dto.getArt_precioc());
            ps.setString(5, dto.getArt_preciov());
            ps.setInt(6, dto.getId_articulo());
            

            if (ps.executeUpdate() > 0) {
                System.out.println(ps);         


                    sql = "UPDATE `stock` SET `id_deposito`=?,`stock_cant`=? WHERE `id_articulo`=?;";
                    ps = conexion.getConnection().prepareStatement(sql);
                    System.out.println(ps);                
                    ps.setInt(1, dto.getId_deposito());
                    ps.setInt(2, dto.getStock_cant());
                     ps.setInt(3, dto.getId_articulo());
                    if (ps.executeUpdate() <= 0) {
                        System.out.println(ps);
                        //manejo de transaccion sql
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
        return true;
    }

    @Override
    public Boolean eliminar(ArticuloDTO dto) {
        try {
        
            sql = "DELETE FROM `stock` WHERE `id_articulo`=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_articulo());   


            if (ps.executeUpdate() > 0) {
                System.out.println(ps);
                

                    sql = "DELETE FROM `articulos` WHERE `id_articulo`=?;";
                    ps = conexion.getConnection().prepareStatement(sql);
                    System.out.println(ps);
                    ps.setInt(1, dto.getId_articulo());
                    if (ps.executeUpdate() <= 0) {
                        System.out.println(ps);
                        //manejo de transaccion sql
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
        return true;
    }

    @Override
    public List<ArticuloDTO> seleccionarTodos() {
         try {
            List<ArticuloDTO> lista;
            ArticuloDTO dto;
            sql = "SELECT a.id_articulo, a.art_descri, m.mar_descri, a.art_precioc, a.art_preciov,i.imp_descri,s.stock_cant,d.dep_descri, a.id_marca, a.id_impuesto, s.id_deposito "
                    + "FROM articulos a INNER JOIN impuesto i ON a.id_impuesto=i.id_impuesto INNER JOIN marca m ON a.id_marca=m.id_marca INNER JOIN stock s ON a.id_articulo=s.id_articulo INNER JOIN deposito d ON s.id_deposito=d.id_deposito;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ArticuloDTO();
                dto.setId_articulo(rs.getInt("id_articulo"));
                dto.setArt_descri(rs.getString("art_descri"));
                dto.setMar_descri(rs.getString("mar_descri"));
                dto.setArt_precioc(rs.getString("art_precioc"));
                dto.setArt_preciov(rs.getString("art_preciov"));
                dto.setImp_descri(rs.getString("imp_descri"));
                dto.setStock_cant(rs.getInt("stock_cant"));
                dto.setDep_descri(rs.getString("dep_descri"));
                dto.setId_marca(rs.getInt("id_marca"));
                dto.setId_impuesto(rs.getInt("id_impuesto"));
                dto.setId_deposito(rs.getInt("id_deposito"));
                
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<ArticuloDTO> seleccionarSegunFiltro(ArticuloDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArticuloDTO seleccionarSegunId(ArticuloDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


        

    

