package modeloDTO;

public class CiudadDTO {

    private Integer bandera;
    private Integer cod_ciudad;
    private String descripcion;

    //set = Asignación
    //get = Recuperación
    
    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getCod_ciudad() {
        return cod_ciudad;
    }

    public void setCod_ciudad(Integer cod_ciudad) {
        this.cod_ciudad = cod_ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
