package interfaces;

import java.util.List;
import modeloDTO.UsuarioDTO;

public interface UsuarioINT extends OperacionesSQL<UsuarioDTO> {
     public List<UsuarioDTO> UserLogueado();
    
}
