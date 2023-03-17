package interfaces;


import java.util.List;
import modeloDTO.PedidoDTO;



public interface PedidoINT extends OperacionesSQL<PedidoDTO>{
    public List<PedidoDTO> UltimoID();
    public List<PedidoDTO> PedidoPendientes();
    
}
