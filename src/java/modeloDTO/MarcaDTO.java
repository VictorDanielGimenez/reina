package modeloDTO;

public class MarcaDTO {

    private Integer cod_marca;
    private String descripcion;
    private String simbolo;
    private Integer bandera;

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Integer getCod_marca() {
        return cod_marca;
    }

    public void setCod_marca(Integer cod_marca) {
        this.cod_marca = cod_marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

}
