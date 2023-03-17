package modeloDTO;

import java.sql.Date;
import java.util.List;

public class CompraDTO {

    private Integer bandera;
    private Integer id_compra;
    private Integer id_proveedor;
    private Integer id_usuario;
    private Integer id_orden;
    private Integer id_deposito;
    private String com_fecha;
    private String com_monto;
    private Integer com_cuo;
    private Integer com_inter;
    private Integer id_estado;
    private List<EstadoDTO> EstadoDTO;
    private Integer id_tipodoc;

    private Integer id_articulo;
    private Integer det_cant;
    private Integer det_precio;
    private Integer det_exenta;
    private Integer det_iva5;
    private Integer det_iva10;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_compra() {
        return id_compra;
    }

    public void setId_compra(Integer id_compra) {
        this.id_compra = id_compra;
    }

    public Integer getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Integer id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_orden() {
        return id_orden;
    }

    public void setId_orden(Integer id_orden) {
        this.id_orden = id_orden;
    }

    public Integer getId_deposito() {
        return id_deposito;
    }

    public void setId_deposito(Integer id_deposito) {
        this.id_deposito = id_deposito;
    }

    public String getCom_fecha() {
        return com_fecha;
    }

    public void setCom_fecha(String com_fecha) {
        this.com_fecha = com_fecha;
    }

    public String getCom_monto() {
        return com_monto;
    }

    public void setCom_monto(String com_monto) {
        this.com_monto = com_monto;
    }

    public Integer getCom_cuo() {
        return com_cuo;
    }

    public void setCom_cuo(Integer com_cuo) {
        this.com_cuo = com_cuo;
    }

    public Integer getCom_inter() {
        return com_inter;
    }

    public void setCom_inter(Integer com_inter) {
        this.com_inter = com_inter;
    }

    public Integer getId_estado() {
        return id_estado;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public Integer getId_tipodoc() {
        return id_tipodoc;
    }

    public void setId_tipodoc(Integer id_tipodoc) {
        this.id_tipodoc = id_tipodoc;
    }

    public Integer getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(Integer id_articulo) {
        this.id_articulo = id_articulo;
    }

    public Integer getDet_cant() {
        return det_cant;
    }

    public void setDet_cant(Integer det_cant) {
        this.det_cant = det_cant;
    }

    public Integer getDet_precio() {
        return det_precio;
    }

    public void setDet_precio(Integer det_precio) {
        this.det_precio = det_precio;
    }

    public Integer getDet_exenta() {
        return det_exenta;
    }

    public void setDet_exenta(Integer det_exenta) {
        this.det_exenta = det_exenta;
    }

    public Integer getDet_iva5() {
        return det_iva5;
    }

    public void setDet_iva5(Integer det_iva5) {
        this.det_iva5 = det_iva5;
    }

    public Integer getDet_iva10() {
        return det_iva10;
    }

    public void setDet_iva10(Integer det_iva10) {
        this.det_iva10 = det_iva10;
    }

    public List<EstadoDTO> getEstadoDTO() {
        return EstadoDTO;
    }

    public void setEstadoDTO(List<EstadoDTO> EstadoDTO) {
        this.EstadoDTO = EstadoDTO;
    }
    
    

 
}
