package interfaces;


import java.util.List;
import modeloDTO.CompraDTO;



public interface CompraINT extends OperacionesSQL<CompraDTO>{
    public List<CompraDTO> UltimoID();  
    public List<CompraDTO> DetalleOrden(CompraDTO dto);
    public List<CompraDTO> seleccionarDocumento();
    
}
