/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programas;

import programas.conexion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dgtic-miguel
 */
public class Genericos {
    public static DateFormat formatterTime=  new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat formatoddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat formatoyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    private static conexion conexion;
    
    public static Time getDateTimeSql(String dateTime) throws java.text.ParseException {
        long ms = formatterTime.parse(dateTime).getTime();
        return new Time(ms);
    }
    
    public static java.sql.Time getHHmmSS(String hora ){
    return java.sql.Time.valueOf(hora);
    }
    
   //java.sql.Time myTime = java.sql.Time.valueOf(hour1);
    

    public static String getFechaServidor() {
        conexion = new conexion();
        ResultSet rs;
        PreparedStatement ps;
        try {
            ps = conexion.getConnection().prepareStatement("select to_char(current_date,'DD/MM/YYYY') as fecha");
            rs = ps.executeQuery();
            rs.next();
            return rs.getString("fecha");
        } catch (Throwable e) {
            return "fecha invalida";
        }
        finally{
            conexion.cerrarConexion();
        }
    }

    public static String retornarFechaddMMyyyy(Date fechaRecuperada) {
        return formatoddMMyyyy.format(fechaRecuperada);
    }

//    //retornar Fecha como String
//    public static String retornarFechayyyyMMdd(String fecha) {
//       Date fechaLocal = null;
//        try {
//          
//           fechaLocal =  (Date) formatoddMMyyyy.parse(fecha);
//           return formatoyyyyMMdd.format(fechaLocal);
//       } catch (java.text.ParseException ex) {
//           Logger.getLogger(Genericos.class.getName()).log(Level.SEVERE, null, ex);
//       }
//       return null;
//    }

    //retornar Fecha como Date
    public static java.sql.Date retornarFecha(String fechaRecibida) {
       // SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        String strFecha = fechaRecibida;
        java.util.Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return fechaUtilTosql(fecha);
    }

    private static java.sql.Date fechaUtilTosql(java.util.Date utilDate) {
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }   

    
    public static String deRequestToJson(HttpServletRequest request){
        BufferedReader br = null;
        String json = "";
        try {
            br = new BufferedReader
                (new InputStreamReader(request.getInputStream()));
            if (br.ready()) {
                json = br.readLine();
            }
        } catch (IOException ex) {
            //
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
              //
            }
        }
        return json;
    }
    
    
    public static String paginaAccesoDenegado(){
        String pagina="";

            pagina+="<!DOCTYPE html>";
            pagina+="<html>";
            pagina+="<head>";
            pagina+="<title></title>";            
            pagina+="</head>";
            pagina+="<body>";
            pagina+="<center>";
            pagina+="<img src=\"Recursos/Img/acceso_denegado.jpg\"><br>";
            pagina+="<h1><a href=\"Paginas/Menu_1.html\">IR AL ACCESO </a> </h1>";
            pagina+="</body>";
            pagina+="</html>";
        
        
        return pagina;
    }
    
    public static void verAyuda() {
        try {
//            Runtime.getRuntime().exec("cmd /c star "+System.getProperty("user.dir")+"/help.chm");
            Runtime.getRuntime().exec("cmd /c C:\\src\\java\\Ayuda\\help.chm");
//            Runtime.getRuntime().exec("cmd /c C:\\Users\\ealegre\\Documents\\UTIC\\Lenguaje de Programación II\\Nueva Carpeta\\LP2\\src\\ayuda");
        } catch (IOException ex) {
            System.out.println(ex.getCause());
        }
    }
}
