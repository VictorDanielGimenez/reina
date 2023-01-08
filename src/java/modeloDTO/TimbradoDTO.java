package modeloDTO;

public class TimbradoDTO {

    private Integer cod_timbrado;
    private Integer nro_timbrado;
    private String fec_inicio;
    private String fecha_venc;
    private Integer nro_proveedor;
    private String descripcion_proveedor;
    private String ruc_proveedor;
    private Integer bandera;

    public Integer getCod_timbrado() {
        return cod_timbrado;
    }

    public void setCod_timbrado(Integer cod_timbrado) {
        this.cod_timbrado = cod_timbrado;
    }

    public Integer getNro_timbrado() {
        return nro_timbrado;
    }

    public void setNro_timbrado(Integer nro_timbrado) {
        this.nro_timbrado = nro_timbrado;
    }

    public String getFec_inicio() {
        return fec_inicio;
    }

    public void setFec_inicio(String fec_inicio) {
        this.fec_inicio = fec_inicio;
    }

    public String getFecha_venc() {
        return fecha_venc;
    }

    public void setFecha_venc(String fecha_venc) {
        this.fecha_venc = fecha_venc;
    }

    public Integer getNro_proveedor() {
        return nro_proveedor;
    }

    public void setNro_proveedor(Integer nro_proveedor) {
        this.nro_proveedor = nro_proveedor;
    }

    public String getDescripcion_proveedor() {
        return descripcion_proveedor;
    }

    public void setDescripcion_proveedor(String descripcion_proveedor) {
        this.descripcion_proveedor = descripcion_proveedor;
    }

    public String getRuc_proveedor() {
        return ruc_proveedor;
    }

    public void setRuc_proveedor(String ruc_proveedor) {
        this.ruc_proveedor = ruc_proveedor;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

}
