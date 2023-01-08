package interfaces;

import modeloDTO.UsuarioDTO;

public interface UsuarioINT extends OperacionesSQL<UsuarioDTO> {
    public Integer getPermiso(UsuarioDTO dto);
    
}
