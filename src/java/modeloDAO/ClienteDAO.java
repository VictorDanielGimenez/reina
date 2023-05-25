package modeloDAO;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import modeloDTO.ClienteDTO;
import programas.conexion;
import interfaces.ClienteINT;
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

public class ClienteDAO implements ClienteINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public ClienteDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(ClienteDTO dto) {
        try {
            sql = "INSERT INTO `cliente` (`cli_nombre`, `cli_ci`, `cli_ruc`, `cli_direc`, `cli_telef`, `id_ciudad`, `id_estado`)  VALUES (?,?,?,?,?,?,1);";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getCli_nombre().toUpperCase());
            ps.setString(2, dto.getCli_ci());
            ps.setString(3, dto.getCli_ruc());
            ps.setString(4, dto.getCli_direc().toUpperCase());
            ps.setString(5, dto.getCli_telef());
            ps.setInt(6, dto.getId_ciudad());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(ClienteDTO dto) {
        try {
            sql = "UPDATE `cliente` SET`cli_nombre`=?, `cli_ci`=?, `cli_ruc`=?, `cli_direc`=?, `cli_telef`=?, `id_ciudad`=? WHERE `id_cliente`=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getCli_nombre().toUpperCase());
            ps.setString(2, dto.getCli_ci());
            ps.setString(3, dto.getCli_ruc());
            ps.setString(4, dto.getCli_direc().toUpperCase());
            ps.setString(5, dto.getCli_telef());
            ps.setInt(6, dto.getId_ciudad());
            ps.setInt(7, dto.getId_cliente());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean eliminar(ClienteDTO dto) {       
         try {
            sql = "DELETE FROM `cliente` WHERE `id_Cliente`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_cliente());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<ClienteDTO> seleccionarTodos() {
       try {
            List<ClienteDTO> lista;
            ClienteDTO dto;
            sql = "SELECT c.id_cliente,c.cli_nombre, c.cli_ruc, c.cli_ci, c.cli_direc,c.cli_telef,s.ciu_descri, c.id_ciudad FROM cliente c INNER JOIN ciudad s ON c.id_ciudad=s.id_ciudad;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ClienteDTO();
                dto.setId_cliente(rs.getInt("id_cliente"));
                dto.setCli_nombre(rs.getString("cli_nombre"));
                dto.setCli_ruc(rs.getString("cli_ruc"));
                dto.setCli_ci(rs.getString("cli_ci"));
                dto.setCli_direc(rs.getString("cli_direc"));
                dto.setCli_telef(rs.getString("cli_telef"));
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
    public List<ClienteDTO> seleccionarSegunFiltro(ClienteDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClienteDTO seleccionarSegunId(ClienteDTO dto) {
        try {
            ClienteDTO dtoLocal = null;
            sql = "SELECT c.id_cliente,c.cli_nombre, c.cli_ruc, c.cli_ci, c.cli_direc,c.cli_telef,s.ciu_descri, c.id_ciudad FROM cliente c INNER JOIN ciudad s ON c.id_ciudad=s.id_ciudad WHERE id_cliente=?;";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_cliente());
            rs = ps.executeQuery();
            if (rs.next()) {
                dtoLocal = new ClienteDTO();
                 dtoLocal.setId_cliente(rs.getInt("id_cliente"));
                dtoLocal.setCli_nombre(rs.getString("cli_nombre"));
                dtoLocal.setCli_ruc(rs.getString("cli_ruc"));
                dtoLocal.setCli_ci(rs.getString("cli_ci"));
                dtoLocal.setCli_direc(rs.getString("cli_direc"));
                dtoLocal.setCli_telef(rs.getString("cli_telef"));
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
        List<ClienteDTO> lista = seleccionarTodos();
        String serverPath = request.getServletContext().getRealPath("/");
        String ruta = serverPath + "pdf/reporteCliente.pdf";
        String route = "pdf/reporteCliente.pdf";

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
            Paragraph title = new Paragraph("Reporte de Clientes", fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            documento.add(title);

            documento.add(new Paragraph("\n"));
            PdfPTable tabla = new PdfPTable(7);

            // Crear una fuente
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);

            // Crear celdas para los encabezados de la tabla
            PdfPCell cell1 = new PdfPCell(new Paragraph("ID", font));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Nombre y Apellido", font));
            PdfPCell cell3 = new PdfPCell(new Paragraph("R.U", font));
            PdfPCell cell4 = new PdfPCell(new Paragraph("C.I", font));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Dirección", font));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Teléfono", font));
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
            Font fontData = FontFactory.getFont(FontFactory.HELVETICA, 12);

            for (ClienteDTO dto : lista) {
                PdfPCell cellData1 = new PdfPCell(new Paragraph(String.valueOf(dto.getId_cliente()), fontData));
                PdfPCell cellData2 = new PdfPCell(new Paragraph(dto.getCli_nombre(), fontData));
                PdfPCell cellData3 = new PdfPCell(new Paragraph(dto.getCli_ruc(), fontData));
                PdfPCell cellData4 = new PdfPCell(new Paragraph(dto.getCli_ci(), fontData));
                PdfPCell cellData5 = new PdfPCell(new Paragraph(dto.getCli_direc(), fontData));
                PdfPCell cellData6 = new PdfPCell(new Paragraph(dto.getCli_telef(), fontData));
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
    public String generarPDFSegunParametro(ClienteDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
}
