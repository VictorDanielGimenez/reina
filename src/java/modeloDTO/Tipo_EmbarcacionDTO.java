package modeloDTO;

public class Tipo_EmbarcacionDTO {

    private Integer cod_tipo;
    private String descripcion;
    private String simbolo;
    private Integer bandera;

    public Integer getCod_tipo() {
        return cod_tipo;
    }

    public void setCod_tipo(Integer cod_tipo) {
        this.cod_tipo = cod_tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }
}
