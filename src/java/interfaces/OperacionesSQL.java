package interfaces;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface OperacionesSQL<T> {

    public Boolean agregar(T dto);

    public Boolean modificar(T dto);

    public Boolean eliminar(T dto);

    public List<T> seleccionarTodos();

    public List<T> seleccionarSegunFiltro(T dto);

    public T seleccionarSegunId(T dto);
    //generar pdf y devolver ruta
    public String generarPDF(HttpServletRequest reques);
    //generar pdf segun parametro y devolver ruta
    public String generarPDFSegunParametro(T dto);
    

}
