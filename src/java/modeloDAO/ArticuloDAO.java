package modeloDAO;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import modeloDTO.ArticuloDTO;
import programas.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.ArticuloINT;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

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

   
    @Override
    public String generarPDFSegunParametro(ArticuloDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String generarPDF(HttpServletRequest request) {
        List< ArticuloDTO> lista = seleccionarTodos();
        String serverPath = request.getServletContext().getRealPath("/");
        String ruta = serverPath + "pdf/reporteArticulo.pdf";
        String route = "pdf/reporteArticulo.pdf";

        try {
            com.lowagie.text.Document documento = new com.lowagie.text.Document();
            try {
                File file = new File(ruta);
                file.getParentFile().mkdirs();

                PdfWriter.getInstance(documento, new FileOutputStream(file));
            } catch (Exception ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            documento.open();

            // Crear una fuente
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);

            // Usar la fuente al crear los párrafos
            Paragraph title = new Paragraph("Reporte de Articulos", fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            documento.add(title);

            documento.add(new Paragraph("\n"));
            PdfPTable tabla = new PdfPTable(7);

            // Crear una fuente
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);

            // Crear celdas para los encabezados de la tabla
            PdfPCell cell1 = new PdfPCell(new Paragraph("ID", font));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Articulo", font));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Marca", font));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Precio Compra", font));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Precio Venta", font));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Cantidad", font));
            PdfPCell cell7 = new PdfPCell(new Paragraph("Impuesto", font));

            // Aplicar estilo a las celdas
            cell1.setBorderColor(Color.BLACK);
            cell1.setBackgroundColor(Color.GRAY);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell2.setBorderColor(Color.BLACK);
            cell2.setBackgroundColor(Color.GRAY);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell3.setBorderColor(Color.BLACK);
            cell3.setBackgroundColor(Color.GRAY);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell4.setBorderColor(Color.BLACK);
            cell4.setBackgroundColor(Color.GRAY);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell5.setBorderColor(Color.BLACK);
            cell5.setBackgroundColor(Color.GRAY);
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell6.setBorderColor(Color.BLACK);
            cell6.setBackgroundColor(Color.GRAY);
            cell6.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell7.setBorderColor(Color.BLACK);
            cell7.setBackgroundColor(Color.GRAY);
            cell7.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Agregar las celdas a la tabla
            tabla.addCell(cell1);
            tabla.addCell(cell2);
            tabla.addCell(cell3);
            tabla.addCell(cell4);
            tabla.addCell(cell5);
            tabla.addCell(cell6);
            tabla.addCell(cell7);

            // Crear una fuente para los datos
            Font fontData = FontFactory.getFont(FontFactory.HELVETICA, 10);

            for ( ArticuloDTO dto : lista) {
                PdfPCell cellData1 = new PdfPCell(new Paragraph(String.valueOf(dto.getId_articulo()), fontData));
                PdfPCell cellData2 = new PdfPCell(new Paragraph(dto.getArt_descri(), fontData));
                PdfPCell cellData3 = new PdfPCell(new Paragraph(dto.getMar_descri(), fontData));
                PdfPCell cellData4 = new PdfPCell(new Paragraph(dto.getArt_precioc(), fontData));
                PdfPCell cellData5 = new PdfPCell(new Paragraph(dto.getArt_preciov(), fontData));
                PdfPCell cellData6 = new PdfPCell(new Paragraph(String.valueOf(dto.getStock_cant()), fontData));
                PdfPCell cellData7 = new PdfPCell(new Paragraph(dto.getImp_descri(), fontData));

                // Aplicar estilo a las celdas
                cellData1.setBorderColor(Color.BLACK);
                cellData1.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData2.setBorderColor(Color.BLACK);
                cellData2.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData3.setBorderColor(Color.BLACK);
                cellData3.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData4.setBorderColor(Color.BLACK);
                cellData4.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData5.setBorderColor(Color.BLACK);
                cellData5.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData6.setBorderColor(Color.BLACK);
                cellData6.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData7.setBorderColor(Color.BLACK);
                cellData7.setHorizontalAlignment(Element.ALIGN_CENTER);

                // Agregar las celdas a la tabla
                tabla.addCell(cellData1);
                tabla.addCell(cellData2);
                tabla.addCell(cellData3);
                tabla.addCell(cellData4);
                tabla.addCell(cellData5);
                tabla.addCell(cellData6);
                tabla.addCell(cellData7);
            }

            documento.add(tabla);

            documento.close();

        } catch (Exception e) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

        return route;
        
    }
}


        

    

