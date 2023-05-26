package modeloDAO;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import modeloDTO.DepositoDTO;
import programas.conexion;
import interfaces.DepositoINT;
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

public class DepositoDAO implements DepositoINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public DepositoDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(DepositoDTO dto) {
        try {
            sql = "INSERT INTO `deposito`(`dep_descri`) VALUES (?);";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getDep_descri().toUpperCase());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(DepositoDTO dto) {
        try {
            sql = "UPDATE `deposito` SET `dep_descri`=? WHERE `id_deposito`=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setString(1, dto.getDep_descri().toUpperCase());
            ps.setInt(2, dto.getId_deposito());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(DepositoDTO dto) {
        try {
            sql = "DELETE FROM `deposito` WHERE `id_deposito`=?;";
//            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
//            System.out.println(ps);
            ps.setInt(1, dto.getId_deposito());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<DepositoDTO> seleccionarTodos() {
        try {
            List<DepositoDTO> lista;
            DepositoDTO dto;
            sql = "SELECT `id_deposito`, `dep_descri` FROM `deposito`;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new DepositoDTO();
                dto.setId_deposito(rs.getInt("id_deposito"));
                dto.setDep_descri(rs.getString("dep_descri"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<DepositoDTO> seleccionarSegunFiltro(DepositoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DepositoDTO seleccionarSegunId(DepositoDTO dto) {
        try {
            DepositoDTO dtoLocal = null;
            sql = "SELECT `id_deposito`, `dep_descri` FROM `deposito` WHERE `id_deposito`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_deposito());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new DepositoDTO();
                dtoLocal.setId_deposito(rs.getInt("id_deposito"));
                dtoLocal.setDep_descri(rs.getString("dep_descri"));
            }
            return dtoLocal;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }


    @Override
    public String generarPDFSegunParametro(DepositoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String generarPDF(HttpServletRequest request) {
        List<DepositoDTO> lista = seleccionarTodos();
        String serverPath = request.getServletContext().getRealPath("/");
        String ruta = serverPath + "pdf/reporteDeposito.pdf";
        String route = "pdf/reporteDeposito.pdf";

        try {
            com.lowagie.text.Document documento = new com.lowagie.text.Document();
            try {
                File file = new File(ruta);
                file.getParentFile().mkdirs();

                PdfWriter.getInstance(documento, new FileOutputStream(file));
            } catch (Exception ex) {
                Logger.getLogger(DepositoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            documento.open();

            // Crear una fuente
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);

            // Usar la fuente al crear los párrafos
            Paragraph title = new Paragraph("Reporte de Deposito", fontTitle);
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

            for (DepositoDTO dto : lista) {
                PdfPCell cellData1 = new PdfPCell(new Paragraph(String.valueOf(dto.getId_deposito()), fontData));
                PdfPCell cellData2 = new PdfPCell(new Paragraph(dto.getDep_descri(), fontData));

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
            Logger.getLogger(DepositoDAO.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

        return route;
    }

}
