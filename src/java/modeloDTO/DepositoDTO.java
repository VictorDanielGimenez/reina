package modeloDTO;

public class DepositoDTO {

    private Integer bandera;
    private Integer cod_deposito;
    private String descripcion;
    private Integer cod_sucursal;
    private String sucursal_descripcion;

    public String getSucursal_descripcion() {
        return sucursal_descripcion;
    }

    public void setSucursal_descripcion(String sucursal_descripcion) {
        this.sucursal_descripcion = sucursal_descripcion;
    }

    public Integer getCod_deposito() {
        return cod_deposito;
    }

    public void setCod_deposito(Integer cod_deposito) {
        this.cod_deposito = cod_deposito;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCod_sucursal() {
        return cod_sucursal;
    }

    public void setCod_sucursal(Integer cod_sucursal) {
        this.cod_sucursal = cod_sucursal;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }
}
