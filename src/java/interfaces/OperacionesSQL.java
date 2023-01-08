package interfaces;

import java.util.List;

public interface OperacionesSQL<T> {

    public Boolean agregar(T dto);

    public Boolean modificar(T dto);

    public Boolean eliminar(T dto);

    public List<T> seleccionarTodos();

    public List<T> seleccionarSegunFiltro(T dto);

    public T seleccionarSegunId(T dto);

}
