package modeloDTO;

import java.util.List;

public class PermisoDTO {

    private Integer cod_permiso;
    private Integer cod_perfil;
    private String perfil_descripcion;
    private Integer cod_menu_item;
    private String descripcion_menu_item;
    private List<Menu_Item_SistemaDTO> lista_menu_item;

    public List<Menu_Item_SistemaDTO> getLista_menu_item() {
        return lista_menu_item;
    }

    public void setLista_menu_item(List<Menu_Item_SistemaDTO> lista_menu_item) {
        this.lista_menu_item = lista_menu_item;
    }

    public Integer getCod_menu_item() {
        return cod_menu_item;
    }

    public void setCod_menu_item(Integer cod_menu_item) {
        this.cod_menu_item = cod_menu_item;
    }

    public String getDescripcion_menu_item() {
        return descripcion_menu_item;
    }

    public void setDescripcion_menu_item(String descripcion_menu_item) {
        this.descripcion_menu_item = descripcion_menu_item;
    }

    private Integer bandera;

    public Integer getCod_permiso() {
        return cod_permiso;
    }

    public void setCod_permiso(Integer cod_permiso) {
        this.cod_permiso = cod_permiso;
    }

    public Integer getCod_perfil() {
        return cod_perfil;
    }

    public void setCod_perfil(Integer cod_perfil) {
        this.cod_perfil = cod_perfil;
    }

    public String getPerfil_descripcion() {
        return perfil_descripcion;
    }

    public void setPerfil_descripcion(String perfil_descripcion) {
        this.perfil_descripcion = perfil_descripcion;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }
}
