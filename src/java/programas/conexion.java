package programas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class conexion {

    private Connection conn;
    private String ultimoError;

    public conexion() {
        String motorBD = "mysql";
        String host = "localhost";
        String nombreBD = "reina";
        String puertoBD = "3306";
        String usuarioBD = "root";
        String claveBD = "";
        String controlador = "com.mysql.cj.jdbc.Driver";
        
        String url = "jdbc:"+motorBD+"://"+host+"/"+nombreBD;
        try {
            System.out.println("url " + url);
            Class.forName(controlador);
            conn = DriverManager.getConnection(url, usuarioBD, claveBD);
            if (conn == null) {
                System.out.println("Conexión no establecida ");
            } else {
                System.out.println("Conexión exitosa ");
            }
        } catch (ClassNotFoundException | SQLException e) {
            ultimoError = e.getMessage();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void cerrarConexion() {
        try {
            conn.close();
        } catch (SQLException ex) {
            ultimoError = ex.getMessage();
        }

    }

    public String getUltimoError() {
        return ultimoError;
    }

}
