package modeloDAO;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import modeloDTO.MarcaDTO;
import programas.conexion;
import interfaces.MarcaINT;
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

public class MarcaDAO implements MarcaINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public MarcaDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(MarcaDTO dto) {
        try {
            sql = "INSERT INTO `marca`(`mar_descri`) VALUES (?);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            ps.setInt(1, dto.getCod_ciudad());
            ps.setString(1, dto.getMarca_descri().toUpperCase());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(MarcaDTO dto) {
        try {
            sql = "UPDATE `marca` SET `mar_descri`=? WHERE `id_marca`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setString(1, dto.getMarca_descri().toUpperCase());
            ps.setInt(2, dto.getId_marca());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(MarcaDTO dto) {
        try {
            sql = "DELETE FROM `marca` WHERE `id_marca`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            System.out.println(ps);
            ps.setInt(1, dto.getId_marca());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<MarcaDTO> seleccionarTodos() {
        try {
            List<MarcaDTO> lista;
            MarcaDTO dto;
            sql = "SELECT `id_marca`, `mar_descri` FROM `marca`;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MarcaDTO();
                dto.setId_marca(rs.getInt("id_marca"));
                dto.setMarca_descri(rs.getString("mar_descri"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<MarcaDTO> seleccionarSegunFiltro(MarcaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MarcaDTO seleccionarSegunId(MarcaDTO dto) {
        try {
            MarcaDTO dtoLocal = null;
            sql = "SELECT `id_marca`, `mar_descri` FROM `marca` WHERE `id_marca`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_marca());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new MarcaDTO();
                dtoLocal.setId_marca(rs.getInt("id_marca"));
                dtoLocal.setMarca_descri(rs.getString("mar_descri"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public String generarPDF(HttpServletRequest request) {
        List<MarcaDTO> lista = seleccionarTodos();
        String serverPath = request.getServletContext().getRealPath("/");
        String ruta = serverPath + "pdf/reporteMarca.pdf";
        String route = "pdf/reporteMarca.pdf";

        try {
            com.lowagie.text.Document documento = new com.lowagie.text.Document();
            try {
                File file = new File(ruta);
                file.getParentFile().mkdirs();

                PdfWriter.getInstance(documento, new FileOutputStream(file));
            } catch (Exception ex) {
                Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            documento.open();

            // Crear una fuente
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);

            // Usar la fuente al crear los párrafos
            Paragraph title = new Paragraph("Reporte de Marca", fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            documento.add(title);

            documento.add(new Paragraph("\n"));
            PdfPTable tabla = new PdfPTable(2);

            // Crear una fuente
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);

            // Crear celdas para los encabezados de la tabla
            PdfPCell cell1 = new PdfPCell(new Paragraph("ID", font));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Descripcion", font));

            // Aplicar estilo a las celdas
            cell1.setBorderColor(Color.BLACK);
            cell1.setBackgroundColor(Color.GRAY);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell2.setBorderColor(Color.BLACK);
            cell2.setBackgroundColor(Color.GRAY);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Agregar las celdas a la tabla
            tabla.addCell(cell1);
            tabla.addCell(cell2);

            // Crear una fuente para los datos
            Font fontData = FontFactory.getFont(FontFactory.HELVETICA, 12);

            for (MarcaDTO dto : lista) {
                PdfPCell cellData1 = new PdfPCell(new Paragraph(String.valueOf(dto.getId_marca()), fontData));
                PdfPCell cellData2 = new PdfPCell(new Paragraph(dto.getMarca_descri(), fontData));

                // Aplicar estilo a las celdas
                cellData1.setBorderColor(Color.BLACK);
                cellData1.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData2.setBorderColor(Color.BLACK);
                cellData2.setHorizontalAlignment(Element.ALIGN_CENTER);

                // Agregar las celdas a la tabla
                tabla.addCell(cellData1);
                tabla.addCell(cellData2);
            }

            documento.add(tabla);

            documento.close();

        } catch (Exception e) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

        return route;
    }

    @Override
    public String generarPDFSegunParametro(MarcaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


  
}
