package modeloDTO;

public class ImpuestoDTO {

    private Integer cod_impuesto;
    private String descripcion;
    private String porcentaje;
    private Integer bandera;

    public Integer getCod_impuesto() {
        return cod_impuesto;
    }

    public void setCod_impuesto(Integer cod_impuesto) {
        this.cod_impuesto = cod_impuesto;
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

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }
}
