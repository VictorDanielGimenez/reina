package Controlador;

import modeloDAO.UsuarioDAO;
import modeloDTO.UsuarioDTO;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccesoCRTL extends HttpServlet {

    private String json;
    private PrintWriter out;
    private Gson gson;
    private UsuarioDTO usuarioDTO;
    private UsuarioDAO usuarioDAO;

    private String bloqueArchivo, bloquePersonas, bloqueArticulos, bloqueCompra,
            bloqueFacturacion, bloqueServicio, bloqueEmbarcaciones, bloqueVarios, bloqueOpciones, bloqueAyuda, bloqueSalir;

    public AccesoCRTL() {
        json = "";
        bloqueArchivo = "";
        bloquePersonas = "";
        bloqueArticulos = "";
        bloqueCompra = "";
        bloqueFacturacion = "";
        bloqueServicio = "";
        bloqueEmbarcaciones = "";
        bloqueVarios = "";
        bloqueOpciones = "";
        bloqueAyuda = "";
        bloqueSalir = "";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        if (br.ready()) {
            json = br.readLine();
        }
        System.out.println("Json c/ tres datos " + json);
        gson = new Gson();
        usuarioDTO = gson.fromJson(json, UsuarioDTO.class);
        System.out.println("Exitoso " + usuarioDTO.getUsuemail());
        System.out.println("Exitoso " + usuarioDTO.getUsu_clave());
        usuarioDAO = new UsuarioDAO();
        System.out.println("id estado " + usuarioDAO.getPermiso(usuarioDTO));
        switch (usuarioDAO.getPermiso(usuarioDTO)) {
            case 1: //SUPER
                out.println(bloqueArchivo + bloquePersonas + bloqueArticulos + bloqueCompra + bloqueFacturacion + bloqueServicio + bloqueEmbarcaciones + bloqueVarios + bloqueOpciones + bloqueAyuda + bloqueSalir);
                out.close();
                break;
            case 2: //USER_FACT
                out.println(bloqueFacturacion + bloqueVarios + bloqueAyuda + bloqueSalir);
                out.close();
                break;
            case 3: //USER_COMP
                out.println(bloqueArticulos + bloqueCompra + bloqueAyuda + bloqueSalir);
                out.close();
                break;
            case 4: //USER_SERV
                out.println(bloqueServicio + bloqueEmbarcaciones + bloqueAyuda + bloqueSalir);
                out.close();
                break;
            case 5: // ADMIN
                out.println(bloqueArchivo + bloqueAyuda + bloqueSalir);
                out.close();
                break;
        }

    }

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
