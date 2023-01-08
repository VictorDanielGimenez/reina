package modeloDTO;

import java.util.List;

public class PerfilDTO {

    private Integer cod_perfil;
    private String descripcion;
    private Integer bandera;
    private List <Menu_Item_SistemaDTO> listaMenuitem;

    public List<Menu_Item_SistemaDTO> getListaMenuitem() {
        return listaMenuitem;
    }

    public void setListaMenuitem(List<Menu_Item_SistemaDTO> listaMenuitem) {
        this.listaMenuitem = listaMenuitem;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getCod_perfil() {
        return cod_perfil;
    }

    public void setCod_perfil(Integer cod_perfil) {
        this.cod_perfil = cod_perfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
