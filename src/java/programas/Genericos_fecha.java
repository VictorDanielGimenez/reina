package programas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;

public class Genericos_fecha {

    private final SimpleDateFormat formatoddMMyyy = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat formatoyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaActual = new Date();

    public String prepararDateField(JFormattedTextField prepararJTfField) {
        try {
            prepararJTfField.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(
                            new javax.swing.text.MaskFormatter("##-##-####")));
        } catch (Exception e) {
        }
        return formatoddMMyyy.format(fechaActual);
    }

    public String retornarFechayyyyMMdd(String fecha) {
        Date fechaLocal = null;
        try {
            fechaLocal = formatoddMMyyy.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(programas.Genericos_fecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return formatoyyyMMdd.format(fechaLocal);
    }

    public String convertirFecha(java.sql.Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
