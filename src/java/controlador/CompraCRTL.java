/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modeloDAO.CompraDAO;
import modeloDTO.CompraDTO;
import programas.Genericos;

/**
 *
 * @author dgtic-miguel
 */
public class CompraCRTL extends HttpServlet {

      private CompraDTO CompraDTO;
    private CompraDAO CompraDAO;
    private Gson gson;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println("Llegamos al controlador");
        String cadenaJSON = Genericos.deRequestToJson(request);
        System.out.println("JSON Obtenido" + cadenaJSON);
        gson = new Gson();
        CompraDTO = gson.fromJson(cadenaJSON, CompraDTO.class);
        CompraDAO = new CompraDAO();

        switch (CompraDTO.getBandera()) {
            case 1:
                if (CompraDAO.agregar(CompraDTO)) {
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
                if (CompraDAO.modificar(CompraDTO)) {
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
                if (CompraDAO.eliminar(CompraDTO)) {
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
                String json = gson.toJson(CompraDAO.seleccionarSegunId(CompraDTO));
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
                String data = gson.toJson(CompraDAO.seleccionarTodos());
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
                String jso = gson.toJson(CompraDAO.UltimoID());
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
                String jsonsd = gson.toJson(CompraDAO.DetalleOrden(CompraDTO));
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
                case 8:                
                //Cargar Tabla
               response.setContentType("application/json");//            
                String datadoc = gson.toJson(CompraDAO.seleccionarDocumento());
                if (datadoc != null) {                     
                    //enviar al js la cadena               
                    System.out.println("data " + datadoc);
                    out.println(datadoc);
                } else {
                    //enviar alguna respuesta para indicar error
                    out.println("ERROR");
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
