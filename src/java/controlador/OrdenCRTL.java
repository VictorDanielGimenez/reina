/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modeloDAO.OrdenDAO;
import modeloDTO.OrdenDTO;
import programas.Genericos;

/**
 *
 * @author mike
 */
public class OrdenCRTL extends HttpServlet {

      private OrdenDTO OrdenDTO;
    private OrdenDAO OrdenDAO;
    private Gson gson;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println("Llegamos al controlador");
        String cadenaJSON = Genericos.deRequestToJson(request);
        System.out.println("JSON Obtenido" + cadenaJSON);
        gson = new Gson();
        OrdenDTO = gson.fromJson(cadenaJSON, OrdenDTO.class);
        OrdenDAO = new OrdenDAO();

        switch (OrdenDTO.getBandera()) {
            case 1:
                if (OrdenDAO.agregar(OrdenDTO)) {
                    String message = "Operación exitosa";
                    String status = "success";
                    String json = "{\"message\":\"" + message + "\",\"status\":\"" + status + "\"}";
                    System.out.println("Json " + json);
                    response.setContentType("application/json");
                    out.println(json);
                } else {
                    String message = "Operación exitosa";
                    String status = "success";
                    String json = "{\"message\":\"" + message + "\",\"status\":\"" + status + "\"}";
                    System.out.println("Json " + json);
                    response.setContentType("application/json");
                    out.println(json);
                }
                break;
            case 2:
                if (OrdenDAO.modificar(OrdenDTO)) {
                    String message = "Operación exitosa";
                    String status = "success";
                    String json = "{\"message\":\"" + message + "\",\"status\":\"" + status + "\"}";
                    System.out.println("Json " + json);
                    response.setContentType("application/json");
                    out.println(json);
                } else {
                    String message = "Operación errónea";
                    String status = "error";
                    String json = "{\"message\":\"" + message + "\",\"status\":\"" + status + "\"}";
                    System.out.println("Json " + json);
                    response.setContentType("application/json");
                    out.println(json);
                }
                break;
            case 3:
                if (OrdenDAO.eliminar(OrdenDTO)) {
                    String message = "Operación exitosa";
                    String status = "success";
                    String json = "{\"message\":\"" + message + "\",\"status\":\"" + status + "\"}";
                    System.out.println("Json " + json);
                    response.setContentType("application/json");
                    out.println(json);
                } else {
                    String message = "Operación errónea";
                    String status = "error";
                    String json = "{\"message\":\"" + message + "\",\"status\":\"" + status + "\"}";
                    System.out.println("Json " + json);
                    response.setContentType("application/json");
                    out.println(json);
                }
                break;
            case 4:
                String json = gson.toJson(OrdenDAO.seleccionarSegunId(OrdenDTO));
                if (json != null) {
                    System.out.println("Json " + json);
                    response.setContentType("application/json");
                    out.println("[" + json + "]");
                    out.close();
                } else {
                    out.println("");
                    out.close();
                }
                break;
            case 5:                
                //Cargar Tabla
               response.setContentType("application/json");//            
                String data = gson.toJson(OrdenDAO.seleccionarTodos());
                if (data != null) {                     
                    //enviar al js la cadena               
                    System.out.println("data " + data);
                    out.println(data);
                } else {
                    //enviar alguna respuesta para indicar error
                    out.println("ERROR");
                }
                break;
                 case 6:                
                //Cargar Tabla
               response.setContentType("application/json");//            
                String jso = gson.toJson(OrdenDAO.UltimoID());
                if (jso != null) {                     
                    //enviar al js la cadena               
                    System.out.println("data " + jso);
                    out.println(jso);
                } else {
                    //enviar alguna respuesta para indicar error
                    out.println("ERROR");
                }
                break;
                 case 7:
                String jsonsd = gson.toJson(OrdenDAO.DetallePedido(OrdenDTO));
                if (jsonsd != null) {
                    System.out.println("Json " + jsonsd);
                    response.setContentType("application/json");
                    out.println("[" + jsonsd + "]");
                    out.close();
                } else {
                    out.println("");
                    out.close();
                }
                break;
            default:
                throw new AssertionError();
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
