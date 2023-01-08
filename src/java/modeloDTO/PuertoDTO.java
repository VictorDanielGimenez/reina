package modeloDTO;

public class PuertoDTO {

    private Integer cod_puerto;
    private String descripcion;
    private Integer cod_ciudad;
    private String ciudad_descripcion;
    private Integer cod_nacionalidad;
    private String nacionalidad_descripcion;
    private Integer bandera;

    public Integer getCod_puerto() {
        return cod_puerto;
    }

    public void setCod_puerto(Integer cod_puerto) {
        this.cod_puerto = cod_puerto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCod_ciudad() {
        return cod_ciudad;
    }

    public void setCod_ciudad(Integer cod_ciudad) {
        this.cod_ciudad = cod_ciudad;
    }

    public String getCiudad_descripcion() {
        return ciudad_descripcion;
    }

    public void setCiudad_descripcion(String ciudad_descripcion) {
        this.ciudad_descripcion = ciudad_descripcion;
    }

    public Integer getCod_nacionalidad() {
        return cod_nacionalidad;
    }

    public void setCod_nacionalidad(Integer cod_nacionalidad) {
        this.cod_nacionalidad = cod_nacionalidad;
    }

    public String getNacionalidad_descripcion() {
        return nacionalidad_descripcion;
    }

    public void setNacionalidad_descripcion(String nacionalidad_descripcion) {
        this.nacionalidad_descripcion = nacionalidad_descripcion;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer Bandera) {
        this.bandera = Bandera;
    }

}
