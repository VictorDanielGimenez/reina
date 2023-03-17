package modeloDTO;

import java.sql.Date;
import java.util.List;

public class OrdenDTO {

    private Integer id_orden;
    private String usu_nombre;
    private Integer id_usuario;
    private Integer id_pedidocompra;
    private String razon;
    private Integer id_proveedor;
    private String orden_fecha;
    private String orden_monto;
    private Integer id_estado;
    private String est_descri;

    private Integer id_articulo;
     private String art_descri;
    private Integer det_cant;
    private Integer det_precio;
    private List<OrdenDTO> lista_articulo;
    private Integer bandera;

    public Integer getId_orden() {
        return id_orden;
    }

    public void setId_orden(Integer id_orden) {
        this.id_orden = id_orden;
    }

    public String getUsu_nombre() {
        return usu_nombre;
    }

    public void setUsu_nombre(String usu_nombre) {
        this.usu_nombre = usu_nombre;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_pedidocompra() {
        return id_pedidocompra;
    }

    public void setId_pedidocompra(Integer id_pedidocompra) {
        this.id_pedidocompra = id_pedidocompra;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public Integer getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Integer id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getOrden_fecha() {
        return orden_fecha;
    }

    public void setOrden_fecha(String orden_fecha) {
        this.orden_fecha = orden_fecha;
    }

    public String getOrden_monto() {
        return orden_monto;
    }

    public void setOrden_monto(String orden_monto) {
        this.orden_monto = orden_monto;
    }

    public Integer getId_estado() {
        return id_estado;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public String getEst_descri() {
        return est_descri;
    }

    public void setEst_descri(String est_descri) {
        this.est_descri = est_descri;
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

    public List<OrdenDTO> getLista_articulo() {
        return lista_articulo;
    }

    public void setLista_articulo(List<OrdenDTO> lista_articulo) {
        this.lista_articulo = lista_articulo;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public String getArt_descri() {
        return art_descri;
    }

    public void setArt_descri(String art_descri) {
        this.art_descri = art_descri;
    }

    

}
