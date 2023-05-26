package modeloDAO;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import modeloDTO.UsuarioDTO;
import programas.conexion;
import interfaces.UsuarioINT;
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

public class UsuarioDAO implements UsuarioINT {

    private ResultSet rs;
    private PreparedStatement ps;
    private final conexion conexion;
    private String sql;
    private String msg;

    public UsuarioDAO() {
        conexion = new conexion();
    }

    @Override
    public Boolean agregar(UsuarioDTO dto) {
        try {
            sql = "INSERT INTO `usuario` (`usu_nombre`, `usuemail`, `usu_clave`, `id_estado`)"
                    + "VALUES (?, ?, ?, 2);";
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setString(1, dto.getUsu_nombre().toUpperCase().trim());
            ps.setString(2, dto.getUsuemail());
            ps.setString(3, dto.getUsu_clave());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public Boolean modificar(UsuarioDTO dto) {
        // si la clave es diferente de vacio, se modifica la clave
        if (!dto.getUsu_clave().equals("")) {
            try {
                sql = "UPDATE `usuario` SET `usu_nombre` = ?, `usuemail` = ?, `usu_clave` = ? WHERE `id_usuario` = ?;";
                ps = conexion.getConnection().prepareStatement(sql);
                ps.setString(1, dto.getUsu_nombre().toUpperCase().trim());
                ps.setString(2, dto.getUsuemail());
                ps.setString(3, dto.getUsu_clave());
                ps.setInt(4, dto.getId_usuario());
                System.out.println(ps);
                return ps.executeUpdate() > 0;
            } catch (SQLException ex) {
                msg = ex.getMessage();
                return false;
            }
        } else {
            try {
                sql = "UPDATE `usuario` SET `usu_nombre` = ?, `usuemail` = ? WHERE `id_usuario` = ?;";
                ps = conexion.getConnection().prepareStatement(sql);
                ps.setString(1, dto.getUsu_nombre().toUpperCase().trim());
                ps.setString(2, dto.getUsuemail());
                ps.setInt(3, dto.getId_usuario());
                System.out.println(ps);
                return ps.executeUpdate() > 0;
            } catch (SQLException ex) {
                msg = ex.getMessage();
                return false;
            }
        }

    }

    @Override
    public Boolean eliminar(UsuarioDTO dto) {
        try {
            sql = "DELETE FROM `usuario` WHERE `id_usuario`=?;";
            System.out.println(sql);
            ps = conexion.getConnection().prepareStatement(sql);
            ps.setInt(1, dto.getId_usuario());
            System.out.println(ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return false;
        }
    }

    @Override
    public List<UsuarioDTO> seleccionarTodos() {
        try {
            List<UsuarioDTO> lista;
            UsuarioDTO dto;
            sql = "SELECT a.id_usuario, a.usu_nombre, a.usuemail, e.est_descri, a.id_estado FROM usuario  a\n" +
                    "INNER JOIN estado e ON a.id_estado=e.id_estado;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new UsuarioDTO();
                dto.setId_usuario(rs.getInt("id_usuario"));
                dto.setUsu_nombre(rs.getString("usu_nombre"));
                dto.setUsuemail(rs.getString("usuemail"));
                dto.setEstado(rs.getString("est_descri"));
                dto.setId_estado(rs.getInt("id_estado"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<UsuarioDTO> seleccionarSegunFiltro(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public UsuarioDTO seleccionarSegunId(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<UsuarioDTO> UserLogueado() {
        try {
            List<UsuarioDTO> lista;
            UsuarioDTO dto;
            sql = "SELECT id_usuario, usu_nombre, SUBSTRING_INDEX(SUBSTRING_INDEX(usu_nombre, ' ', 1), ' ', -1) AS PrimerNombre, SUBSTRING_INDEX(SUBSTRING_INDEX(usu_nombre, ' ', 3), ' ', -1) AS PrimerApellido FROM usuario WHERE id_estado=1;";
            ps = conexion.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new UsuarioDTO();
                dto.setId_usuario(rs.getInt("id_usuario"));
                dto.setUsu_nombre(rs.getString("usu_nombre"));
                dto.setPrimernombre(rs.getString("PrimerNombre"));
                dto.setPrimerapellido(rs.getString("PrimerApellido"));
                lista.add(dto);
            }
            return lista;

        } catch (SQLException ex) {
            msg = ex.getMessage();
            return null;
        }
    }

    @Override
   public String generarPDF(HttpServletRequest request) {
        List< UsuarioDTO> lista = seleccionarTodos();
        String serverPath = request.getServletContext().getRealPath("/");
        String ruta = serverPath + "pdf/reporteUsuario.pdf";
        String route = "pdf/reporteUsuario.pdf";

        try {
            com.lowagie.text.Document documento = new com.lowagie.text.Document();
            try {
                File file = new File(ruta);
                file.getParentFile().mkdirs();

                PdfWriter.getInstance(documento, new FileOutputStream(file));
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            documento.open();

            // Crear una fuente
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);

            // Usar la fuente al crear los párrafos
            Paragraph title = new Paragraph("Reporte de Proveedores", fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            documento.add(title);

            documento.add(new Paragraph("\n"));
            PdfPTable tabla = new PdfPTable(4);

            // Crear una fuente
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);

            // Crear celdas para los encabezados de la tabla
            PdfPCell cell1 = new PdfPCell(new Paragraph("ID", font));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Nombre", font));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Email", font));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Estado", font));

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

            // Agregar las celdas a la tabla
            tabla.addCell(cell1);
            tabla.addCell(cell2);
            tabla.addCell(cell3);
            tabla.addCell(cell4);

            // Crear una fuente para los datos
            Font fontData = FontFactory.getFont(FontFactory.HELVETICA, 10);

            for ( UsuarioDTO dto : lista) {
                PdfPCell cellData1 = new PdfPCell(new Paragraph(String.valueOf(dto.getId_usuario()), fontData));
                PdfPCell cellData2 = new PdfPCell(new Paragraph(dto.getUsu_nombre(), fontData));
                PdfPCell cellData3 = new PdfPCell(new Paragraph(dto.getUsuemail(), fontData));
                PdfPCell cellData4 = new PdfPCell(new Paragraph(dto.getEstado(), fontData));
                
                // Aplicar estilo a las celdas
                cellData1.setBorderColor(Color.BLACK);
                cellData1.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData2.setBorderColor(Color.BLACK);
                cellData2.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData3.setBorderColor(Color.BLACK);
                cellData3.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellData4.setBorderColor(Color.BLACK);
                cellData4.setHorizontalAlignment(Element.ALIGN_CENTER);

                // Agregar las celdas a la tabla
                tabla.addCell(cellData1);
                tabla.addCell(cellData2);
                tabla.addCell(cellData3);
                tabla.addCell(cellData4);
                
            }

            documento.add(tabla);

            documento.close();

        } catch (Exception e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

        return route;
        
    }

    @Override
    public String generarPDFSegunParametro(UsuarioDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
