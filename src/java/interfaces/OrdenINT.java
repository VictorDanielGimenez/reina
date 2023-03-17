package interfaces;


import java.util.List;
import modeloDTO.OrdenDTO;



public interface OrdenINT extends OperacionesSQL<OrdenDTO>{
    public List<OrdenDTO> UltimoID();  
    public List<OrdenDTO> DetallePedido(OrdenDTO dto);
    
}
