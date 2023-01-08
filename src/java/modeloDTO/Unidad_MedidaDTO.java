package modeloDTO;

public class Unidad_MedidaDTO {

    private Integer cod_medida;
    private String descripcion;
    private Integer bandera;

    public Integer getCod_medida() {
        return cod_medida;
    }

    public void setCod_medida(Integer cod_medida) {
        this.cod_medida = cod_medida;
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
