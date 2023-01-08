package modeloDTO;

public class SucursalDTO {

    private Integer cod_sucursal;
    private String nro_sucursal;
    private String descripcion;
    private String direccion;
    private Integer cod_empresa;
    private String descripcion_empresa;
    private Integer cod_ciudad;
    private String descripcion_ciudad;
    private Integer bandera;

    public Integer getCod_sucursal() {
        return cod_sucursal;
    }

    public void setCod_sucursal(Integer cod_sucursal) {
        this.cod_sucursal = cod_sucursal;
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

    public Integer getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(Integer cod_empresa) {
        this.cod_empresa = cod_empresa;
    }

    public String getDescripcion_empresa() {
        return descripcion_empresa;
    }

    public void setDescripcion_empresa(String descripcion_empresa) {
        this.descripcion_empresa = descripcion_empresa;
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

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public String getNro_sucursal() {
        return nro_sucursal;
    }

    public void setNro_sucursal(String nro_sucursal) {
        this.nro_sucursal = nro_sucursal;
    }

}
