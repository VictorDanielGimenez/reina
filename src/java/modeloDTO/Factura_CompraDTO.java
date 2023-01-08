package modeloDTO;

import java.util.List;

public class Factura_CompraDTO {

    private Integer bandera;
    private Integer cod_factura;
    private Integer nro_orden;
    private Integer cod_tipofactura;
    private Integer nro_proveedor;
    private String descripcion_proveedor;
    private String fecha_creacion;
    private String fac_timbrado;
    private String fac_vtotimbrado;
    private String nro_factura;
    private String fecha_compra;
    private String fac_total;
    private String fac_totaliva;
    private Integer cod_empleado;
    private String descripcion_empleado;
    private Integer cod_deposito;
    private String descripcion_deposito;
    private String descripcion_sucursal;
    private String estado;
    private Integer cod_mercaderia;
    private String descripcion_mercaderia;
    private Integer cod_stok;
    private Integer det_cantidad;
    private String det_monto;
    private String impuesto_descripcion;
    private String exento;
    private String iva5;
    private String iva10;
    private String condicion_compra;
    private List<MercaderiaDTO> lista_mercaderia;

    public String getCondicion_compra() {
        return condicion_compra;
    }

    public void setCondicion_compra(String condicion_compra) {
        this.condicion_compra = condicion_compra;
    }

    public String getExento() {
        return exento;
    }

    public void setExento(String exento) {
        this.exento = exento;
    }

    public String getIva5() {
        return iva5;
    }

    public void setIva5(String iva5) {
        this.iva5 = iva5;
    }

    public String getIva10() {
        return iva10;
    }

    public void setIva10(String iva10) {
        this.iva10 = iva10;
    }

    public String getImpuesto_descripcion() {
        return impuesto_descripcion;
    }

    public void setImpuesto_descripcion(String impuesto_descripcion) {
        this.impuesto_descripcion = impuesto_descripcion;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getCod_mercaderia() {
        return cod_mercaderia;
    }

    public void setCod_mercaderia(Integer cod_mercaderia) {
        this.cod_mercaderia = cod_mercaderia;
    }

    public String getDescripcion_mercaderia() {
        return descripcion_mercaderia;
    }

    public void setDescripcion_mercaderia(String descripcion_mercaderia) {
        this.descripcion_mercaderia = descripcion_mercaderia;
    }

    public Integer getCod_stok() {
        return cod_stok;
    }

    public void setCod_stok(Integer cod_stok) {
        this.cod_stok = cod_stok;
    }

    public Integer getDet_cantidad() {
        return det_cantidad;
    }

    public void setDet_cantidad(Integer det_cantidad) {
        this.det_cantidad = det_cantidad;
    }

    public String getDet_monto() {
        return det_monto;
    }

    public void setDet_monto(String det_monto) {
        this.det_monto = det_monto;
    }

    public Integer getCod_factura() {
        return cod_factura;
    }

    public void setCod_factura(Integer cod_factura) {
        this.cod_factura = cod_factura;
    }

    public Integer getNro_orden() {
        return nro_orden;
    }

    public void setNro_orden(Integer nro_orden) {
        this.nro_orden = nro_orden;
    }

    public Integer getCod_tipofactura() {
        return cod_tipofactura;
    }

    public void setCod_tipofactura(Integer cod_tipofactura) {
        this.cod_tipofactura = cod_tipofactura;
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

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFac_timbrado() {
        return fac_timbrado;
    }

    public void setFac_timbrado(String fac_timbrado) {
        this.fac_timbrado = fac_timbrado;
    }

    public String getFac_vtotimbrado() {
        return fac_vtotimbrado;
    }

    public void setFac_vtotimbrado(String fac_vtotimbrado) {
        this.fac_vtotimbrado = fac_vtotimbrado;
    }

    public String getNro_factura() {
        return nro_factura;
    }

    public void setNro_factura(String nro_factura) {
        this.nro_factura = nro_factura;
    }

    public String getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public String getFac_total() {
        return fac_total;
    }

    public void setFac_total(String fac_total) {
        this.fac_total = fac_total;
    }

    public String getFac_totaliva() {
        return fac_totaliva;
    }

    public void setFac_totaliva(String fac_totaliva) {
        this.fac_totaliva = fac_totaliva;
    }

    public Integer getCod_empleado() {
        return cod_empleado;
    }

    public void setCod_empleado(Integer cod_empleado) {
        this.cod_empleado = cod_empleado;
    }

    public String getDescripcion_empleado() {
        return descripcion_empleado;
    }

    public void setDescripcion_empleado(String descripcion_empleado) {
        this.descripcion_empleado = descripcion_empleado;
    }

    public Integer getCod_deposito() {
        return cod_deposito;
    }

    public void setCod_deposito(Integer cod_deposito) {
        this.cod_deposito = cod_deposito;
    }

    public String getDescripcion_deposito() {
        return descripcion_deposito;
    }

    public void setDescripcion_deposito(String descripcion_deposito) {
        this.descripcion_deposito = descripcion_deposito;
    }

    public String getDescripcion_sucursal() {
        return descripcion_sucursal;
    }

    public void setDescripcion_sucursal(String descripcion_sucursal) {
        this.descripcion_sucursal = descripcion_sucursal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<MercaderiaDTO> getLista_mercaderia() {
        return lista_mercaderia;
    }

    public void setLista_mercaderia(List<MercaderiaDTO> lista_mercaderia) {
        this.lista_mercaderia = lista_mercaderia;
    }
}
