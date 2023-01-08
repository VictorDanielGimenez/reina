package modeloDTO;

public class EmpresaDTO {

    private Integer cod_empresa;
    private String telefono;
    private String descripcion;
    private String direccion;
    private String ruc;
    private Integer cod_ciudad;
    private String ciudad_descripcion;

    public String getCiudad_descripcion() {
        return ciudad_descripcion;
    }

    public void setCiudad_descripcion(String ciudad_descripcion) {
        this.ciudad_descripcion = ciudad_descripcion;
    }
    private Integer bandera;

    public Integer getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(Integer cod_empresa) {
        this.cod_empresa = cod_empresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Integer getCod_ciudad() {
        return cod_ciudad;
    }

    public void setCod_ciudad(Integer cod_ciudad) {
        this.cod_ciudad = cod_ciudad;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }
}
