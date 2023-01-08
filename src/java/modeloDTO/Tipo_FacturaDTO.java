package modeloDTO;

public class Tipo_FacturaDTO {

    private Integer cod_tipofactura;
    private String descripcion;
    private Integer bandera;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getCod_tipofactura() {
        return cod_tipofactura;
    }

    public void setCod_tipofactura(Integer cod_tipofactura) {
        this.cod_tipofactura = cod_tipofactura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
