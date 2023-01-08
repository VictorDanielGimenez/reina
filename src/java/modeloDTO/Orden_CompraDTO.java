package modeloDTO;

import java.util.List;

public class Orden_CompraDTO {

    private Integer bandera;
    private Integer nro_orden;
    private String fecha;
    private Integer nro_proveedor;
    private String proveedor_descripcion;
    private Integer cod_empleado;
    private String empleado_descripcion;
    private String empleado_solicitante;
    private Integer nro_solicitud;
    private String solicitud_descripcion;
    private Boolean estado_compra;
    private Integer cod_empresa;
    private String empresa_descripcion;
    private String condicion_compra;
    private Integer cod_mercaderia;
    private String articulo;
    private Integer cantidad;
    private Integer cod_marca;
    private String marca_descripcion;
    private Integer cod_medida;
    private String descripcion_medida;
    private String precio_unitario;
    private String subtotal;
    private String cod_impuesto;
    private String impuesto_descripcion;
    private String exento;
    private String iva5;
    private String iva10;
    private String total;
    private List<MercaderiaDTO> lista_mercaderia;

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

    public String getCod_impuesto() {
        return cod_impuesto;
    }

    public void setCod_impuesto(String cod_impuesto) {
        this.cod_impuesto = cod_impuesto;
    }

    public String getImpuesto_descripcion() {
        return impuesto_descripcion;
    }

    public void setImpuesto_descripcion(String impuesto_descripcion) {
        this.impuesto_descripcion = impuesto_descripcion;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getEmpleado_solicitante() {
        return empleado_solicitante;
    }

    public void setEmpleado_solicitante(String empleado_solicitante) {
        this.empleado_solicitante = empleado_solicitante;
    }

    public String getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(String precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Integer getCod_mercaderia() {
        return cod_mercaderia;
    }

    public void setCod_mercaderia(Integer cod_mercaderia) {
        this.cod_mercaderia = cod_mercaderia;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCod_marca() {
        return cod_marca;
    }

    public void setCod_marca(Integer cod_marca) {
        this.cod_marca = cod_marca;
    }

    public String getMarca_descripcion() {
        return marca_descripcion;
    }

    public void setMarca_descripcion(String marca_descripcion) {
        this.marca_descripcion = marca_descripcion;
    }

    public Integer getCod_medida() {
        return cod_medida;
    }

    public void setCod_medida(Integer cod_medida) {
        this.cod_medida = cod_medida;
    }

    public String getDescripcion_medida() {
        return descripcion_medida;
    }

    public void setDescripcion_medida(String descripcion_medida) {
        this.descripcion_medida = descripcion_medida;
    }

    public String getSolicitud_descripcion() {
        return solicitud_descripcion;
    }

    public void setSolicitud_descripcion(String solicitud_descripcion) {
        this.solicitud_descripcion = solicitud_descripcion;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getNro_orden() {
        return nro_orden;
    }

    public void setNro_orden(Integer nro_orden) {
        this.nro_orden = nro_orden;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getNro_proveedor() {
        return nro_proveedor;
    }

    public void setNro_proveedor(Integer nro_proveedor) {
        this.nro_proveedor = nro_proveedor;
    }

    public String getProveedor_descripcion() {
        return proveedor_descripcion;
    }

    public void setProveedor_descripcion(String proveedor_descripcion) {
        this.proveedor_descripcion = proveedor_descripcion;
    }

    public Integer getCod_empleado() {
        return cod_empleado;
    }

    public void setCod_empleado(Integer cod_empleado) {
        this.cod_empleado = cod_empleado;
    }

    public String getEmpleado_descripcion() {
        return empleado_descripcion;
    }

    public void setEmpleado_descripcion(String empleado_descripcion) {
        this.empleado_descripcion = empleado_descripcion;
    }

    public Integer getNro_solicitud() {
        return nro_solicitud;
    }

    public void setNro_solicitud(Integer nro_solicitud) {
        this.nro_solicitud = nro_solicitud;
    }

    public Boolean getEstado_compra() {
        return estado_compra;
    }

    public void setEstado_compra(Boolean estado_compra) {
        this.estado_compra = estado_compra;
    }

    public Integer getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(Integer cod_empresa) {
        this.cod_empresa = cod_empresa;
    }

    public String getEmpresa_descripcion() {
        return empresa_descripcion;
    }

    public void setEmpresa_descripcion(String empresa_descripcion) {
        this.empresa_descripcion = empresa_descripcion;
    }

    public String getCondicion_compra() {
        return condicion_compra;
    }

    public void setCondicion_compra(String condicion_compra) {
        this.condicion_compra = condicion_compra;
    }

    public List<MercaderiaDTO> getLista_mercaderia() {
        return lista_mercaderia;
    }

    public void setLista_mercaderia(List<MercaderiaDTO> lista_mercaderia) {
        this.lista_mercaderia = lista_mercaderia;
    }

}
