package controlador;

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
import modeloDAO.AccesoDAO;
import modeloDTO.AccesoDTO;

public class AccesoCRTL extends HttpServlet {

    private String json;
    private PrintWriter out;
    private Gson gson;
    private AccesoDAO accesoDAO;
    private AccesoDTO accesoDTO;

    public AccesoCRTL() {
        json = "";
    
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        if (br.ready()) {
            json = br.readLine();
        }
        System.out.println("Json c/ TRES datos " + json);
        gson = new Gson();
        accesoDTO = gson.fromJson(json, AccesoDTO.class);      
        System.out.println("Exitoso " + accesoDTO.getEmail());
        System.out.println("Exitoso " + accesoDTO.getClave());
        System.out.println("Exitoso " + accesoDTO.getBandera());
        accesoDAO = new AccesoDAO();        
        switch (accesoDTO.getBandera()) {
            case 1:
                if (accesoDAO.CerrarSession(accesoDTO) ) {
                    System.out.println("ssss");
                    out.println("1");
                } else {
                      System.out.println("ggggg");
                    out.println("0");
                }
                break;
           case 2:
              if (accesoDAO.validarUsuario(accesoDTO)) {
               out.println("1");
              } else {
                   out.println("2");
              }
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
