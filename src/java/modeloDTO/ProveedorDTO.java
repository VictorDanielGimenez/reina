package modeloDTO;

public class ProveedorDTO {

    private Integer nro_proveedor;
    private String razon_social;
    private String ruc;
    private String telefono;
    private String direccion;
    private Integer cod_ciudad;
    private String descripcion_ciudad;
    private Integer cod_nacionalidad;
    private String descripcion_nacionalidad;
    private Integer bandera;

    public Integer getNro_proveedor() {
        return nro_proveedor;
    }

    public void setNro_proveedor(Integer nro_proveedor) {
        this.nro_proveedor = nro_proveedor;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
     public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getCod_ciudad() {
        return cod_ciudad;
    }

    public void setCod_ciudad(Integer cod_ciudad) {
        this.cod_ciudad = cod_ciudad;
    }

    public String getDescripcion_ciudad() {
        return descripcion_ciudad;
    }

    public void setDescripcion_ciudad(String descripcion_ciudad) {
        this.descripcion_ciudad = descripcion_ciudad;
    }

    public Integer getCod_nacionalidad() {
        return cod_nacionalidad;
    }

    public void setCod_nacionalidad(Integer cod_nacionalidad) {
        this.cod_nacionalidad = cod_nacionalidad;
    }

    public String getDescripcion_nacionalidad() {
        return descripcion_nacionalidad;
    }

    public void setDescripcion_nacionalidad(String descripcion_nacionalidad) {
        this.descripcion_nacionalidad = descripcion_nacionalidad;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

}
