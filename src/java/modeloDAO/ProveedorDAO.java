package modeloDAO;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import modeloDTO.ProveedorDTO;
import programas.conexion;
import interfaces.ProveedorINT;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import modeloDTO.ClienteDTO;

public class ProveedorDAO implements ProveedorINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public ProveedorDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(ProveedorDTO dto) {
        try {
            sql = "INSERT INTO `proveedor`(`prov_razons`, `prov_ruc`, `prov_tel`, `prov_direc`, `prov_correo`,  `id_ciudad`,  `id_estado`)"
                    + "VALUES (?,?,?,?,?,?,1);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getProv_razons().toUpperCase());
            ps.setString(2, dto.getProv_ruc());
            ps.setString(3, dto.getProv_te());
            ps.setString(4, dto.getProv_direc().toUpperCase());
            ps.setString(5, dto.getProv_correo());
            ps.setInt(6, dto.getId_ciudad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(ProveedorDTO dto) {
        try {
            sql = "UPDATE `proveedor` SET `prov_razons`=?, `prov_ruc`=?, `prov_tel`=?, `prov_direc`=?, `prov_correo`=? ,`id_ciudad`=? WHERE `id_proveedor`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getProv_razons().toUpperCase());
            ps.setString(2, dto.getProv_ruc());
            ps.setString(3, dto.getProv_te());
            ps.setString(4, dto.getProv_direc().toUpperCase());
            ps.setString(5, dto.getProv_correo());
            ps.setInt(6, dto.getId_ciudad());
            ps.setInt(7, dto.getId_proveedor());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(ProveedorDTO dto) {       
         try {
            sql = "DELETE FROM `proveedor` WHERE `id_proveedor`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_proveedor());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<ProveedorDTO> seleccionarTodos() {
       try {
            List<ProveedorDTO> lista;
            ProveedorDTO dto;
            sql = "SELECT p.id_proveedor, p.prov_razons, p.prov_ruc, p.prov_tel, p.prov_direc, p.prov_correo, c.ciu_descri, p.id_ciudad FROM proveedor p INNER JOIN ciudad c ON p.id_ciudad=c.id_ciudad;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ProveedorDTO();
                dto.setId_proveedor(rs.getInt("id_proveedor"));
                dto.setProv_razons(rs.getString("prov_razons"));
                dto.setProv_ruc(rs.getString("prov_ruc"));
                dto.setProv_te(rs.getString("prov_tel"));
                dto.setProv_direc(rs.getString("prov_direc"));
                dto.setProv_correo(rs.getString("prov_correo"));
                dto.setCiu_descri(rs.getString("ciu_descri"));
                dto.setId_ciudad(rs.getInt("id_ciudad"));
                     
                    
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<ProveedorDTO> seleccionarSegunFiltro(ProveedorDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProveedorDTO seleccionarSegunId(ProveedorDTO dto) {
        try {
            ProveedorDTO dtoLocal = null;
            sql = "SELECT p.id_proveedor, p.prov_razons, p.prov_ruc, p.prov_tel, p.prov_direc, p.prov_correo, p.id_ciudad, c.ciu_descri FROM proveedor p INNER JOIN ciudad c ON p.id_ciudad=c.id_ciudad WHERE p.id_proveedor=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_proveedor());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new ProveedorDTO();
                  dtoLocal.setId_proveedor(rs.getInt("id_proveedor"));
                dtoLocal.setProv_razons(rs.getString("prov_razons"));
                dtoLocal.setProv_ruc(rs.getString("prov_ruc"));
                dtoLocal.setProv_te(rs.getString("prov_tel"));
                dtoLocal.setProv_direc(rs.getString("prov_direc"));
                dtoLocal.setProv_correo(rs.getString("prov_correo"));
                dtoLocal.setCiu_descri(rs.getString("ciu_descri"));
                dtoLocal.setId_ciudad(rs.getInt("id_ciudad"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public String generarPDF(HttpServletRequest request) {
        List< ProveedorDTO> lista = seleccionarTodos();
        String serverPath = request.getServletContext().getRealPath("/");
        String ruta = serverPath + "pdf/reporteProveedor.pdf";
        String route = "pdf/reporteProveedor.pdf";

        try {
            com.lowagie.text.Document documento = new com.lowagie.text.Document();
            try {
                File file = new File(ruta);
                file.getParentFile().mkdirs();

                PdfWriter.getInstance(documento, new FileOutputStream(file));
            } catch (Exception ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            documento.open();

            // Crear una fuente
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);

            // Usar la fuente al crear los párrafos
            Paragraph title = new Paragraph("Reporte de Proveedores", fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            documento.add(title);

            documento.add(new Paragraph("\n"));
            PdfPTable tabla = new PdfPTable(7);

            // Crear una fuente
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);

            // Crear celdas para los encabezados de la tabla
            PdfPCell cell1 = new PdfPCell(new Paragraph("ID", font));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Razon Social", font));
            PdfPCell cell3 = new PdfPCell(new Paragraph("R.U.C", font));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Telefono", font));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Dirección", font));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Correo", font));
            PdfPCell cell7 = new PdfPCell(new Paragraph("Ciudad", font));

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

            for ( ProveedorDTO dto : lista) {
                PdfPCell cellData1 = new PdfPCell(new Paragraph(String.valueOf(dto.getId_proveedor()), fontData));
                PdfPCell cellData2 = new PdfPCell(new Paragraph(dto.getProv_razons(), fontData));
                PdfPCell cellData3 = new PdfPCell(new Paragraph(dto.getProv_ruc(), fontData));
                PdfPCell cellData4 = new PdfPCell(new Paragraph(dto.getProv_te(), fontData));
                PdfPCell cellData5 = new PdfPCell(new Paragraph(dto.getProv_direc(), fontData));
                PdfPCell cellData6 = new PdfPCell(new Paragraph(dto.getProv_correo(), fontData));
                PdfPCell cellData7 = new PdfPCell(new Paragraph(dto.getCiu_descri(), fontData));

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

    @Override
    public String generarPDFSegunParametro(ProveedorDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
}
