package modeloDTO;

public class MonedaDTO {

    private Integer cod_moneda;
    private String descripcion;
    private String simbolo;
    private Integer bandera;

    public Integer getCod_moneda() {
        return cod_moneda;
    }

    public void setCod_moneda(Integer cod_moneda) {
        this.cod_moneda = cod_moneda;
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
