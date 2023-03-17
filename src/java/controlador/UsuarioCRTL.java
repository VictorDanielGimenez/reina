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
import modeloDAO.UsuarioDAO;
import modeloDTO.UsuarioDTO;
import programas.Genericos;

/**
 *
 * @author dgtic-miguel
 */
public class UsuarioCRTL extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        System.out.println("Llegamos al controlador");
        String cadenaJSON = Genericos.deRequestToJson(request);
        System.out.println("JSON OBTENIDO " + cadenaJSON);
        Gson gson = new Gson();
        UsuarioDTO usuarioDTO;
        UsuarioDAO usuarioDAO;
        usuarioDTO = gson.fromJson(cadenaJSON, UsuarioDTO.class);
        usuarioDAO = new UsuarioDAO();

        switch (usuarioDTO.getBandera()) {
            case 1:
                if (usuarioDAO.agregar(usuarioDTO)) {
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
            case 2:
                if (usuarioDAO.modificar(usuarioDTO)) {
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
                if (usuarioDAO.eliminar(usuarioDTO)) {
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
//                PrintWriter out = response.getWriter();
                String json = gson.toJson(usuarioDAO.seleccionarSegunId(usuarioDTO));
                if (json != null) {
                    System.out.println("Json " + json);
                    response.setContentType("application/json, charset=UTF-8");
                    out.println("[" + json + "]");
                    out.close();
                } else {
                    out.println("");
                    out.close();
                }
                break;
            case 5:
                //Cargar Tabla
                response.setContentType("application/json");
                String cadenaSucursalTodos = gson.toJson(usuarioDAO.seleccionarTodos());
                if (cadenaSucursalTodos != null) {
                    //enviar al js la cadena 
                    System.out.println("Cadena " + cadenaSucursalTodos);
                    out.println(cadenaSucursalTodos);
                } else {
                    //enviar alguna respuesta para indicar error
                }
                break;
                 case 6:
                //Cargar Tabla
                response.setContentType("application/json");
                String sas = gson.toJson(usuarioDAO.UserLogueado());
                if (sas != null) {
                    //enviar al js la cadena 
                    System.out.println("Cadena " + sas);
                    out.println(sas);
                } else {
                    //enviar alguna respuesta para indicar error
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
