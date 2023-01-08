package modeloDTO;

public class NacionalidadDTO {

    private Integer cod_nacionalidad;
    private String descripcion;
    private Integer bandera;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public void setCod_nacionalidad(Integer cod_nacionalidad) {
        this.cod_nacionalidad = cod_nacionalidad;
    }

    public Integer getCod_nacionalidad() {
        return cod_nacionalidad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
