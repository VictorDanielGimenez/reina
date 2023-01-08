package modeloDTO;

public class Menu_Item_SistemaDTO {

    private Integer cod_menu_item;
    private String descripcion;
    private String url;
    private Integer cod_menu;
    private String descripcion_menu;
    private Integer bandera;
    

    public Integer getCod_menu_item() {
        return cod_menu_item;
    }

    public void setCod_menu_item(Integer cod_menu_item) {
        this.cod_menu_item = cod_menu_item;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCod_menu() {
        return cod_menu;
    }

    public void setCod_menu(Integer cod_menu) {
        this.cod_menu = cod_menu;
    }

    public String getDescripcion_menu() {
        return descripcion_menu;
    }

    public void setDescripcion_menu(String descripcion_menu) {
        this.descripcion_menu = descripcion_menu;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }
}
