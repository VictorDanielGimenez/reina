package modeloDTO;

import java.util.List;

public class Solicitud_CompraDTO {

    private Integer bandera;
    private Integer nro_solicitud;
    private String descripcion;
    private String fecha;
    private String prioridad;
    private Integer cod_empleado;
    private String empleado_descripcion;
    private Integer cod_departamento;
    private String departamento_descripcion;
    private Integer cod_deposito;
    private String deposito_descripcion;
    private Integer cod_sucursal;
    private String sucursal_descripcion;
    private Integer cod_marca;
    private String marca_descripcion;
    private Integer cod_medida;
    private String descripcion_medida;
    private Boolean estado;
    private Integer cod_mercaderia;
    private String articulo;
    private Integer cantidad;
    private String precio_unitario;
    private String subtotal;
    private List<MercaderiaDTO> lista_mercaderia;

    public String getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(String precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
  
    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getNro_solicitud() {
        return nro_solicitud;
    }

    public void setNro_solicitud(Integer nro_solicitud) {
        this.nro_solicitud = nro_solicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
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

    public Integer getCod_departamento() {
        return cod_departamento;
    }

    public void setCod_departamento(Integer cod_departamento) {
        this.cod_departamento = cod_departamento;
    }

    public String getDepartamento_descripcion() {
        return departamento_descripcion;
    }

    public void setDepartamento_descripcion(String departamento_descripcion) {
        this.departamento_descripcion = departamento_descripcion;
    }

    public Integer getCod_deposito() {
        return cod_deposito;
    }

    public void setCod_deposito(Integer cod_deposito) {
        this.cod_deposito = cod_deposito;
    }

    public String getDeposito_descripcion() {
        return deposito_descripcion;
    }

    public void setDeposito_descripcion(String deposito_descripcion) {
        this.deposito_descripcion = deposito_descripcion;
    }

    public Integer getCod_sucursal() {
        return cod_sucursal;
    }

    public void setCod_sucursal(Integer cod_sucursal) {
        this.cod_sucursal = cod_sucursal;
    }

    public String getSucursal_descripcion() {
        return sucursal_descripcion;
    }

    public void setSucursal_descripcion(String sucursal_descripcion) {
        this.sucursal_descripcion = sucursal_descripcion;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getCod_mercaderia() {
        return cod_mercaderia;
    }

    public void setCod_mercaderia(Integer cod_mercaderia) {
        this.cod_mercaderia = cod_mercaderia;
    }


    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<MercaderiaDTO> getLista_mercaderia() {
        return lista_mercaderia;
    }

    public void setLista_mercaderia(List<MercaderiaDTO> lista_mercaderia) {
        this.lista_mercaderia = lista_mercaderia;
    }

}
