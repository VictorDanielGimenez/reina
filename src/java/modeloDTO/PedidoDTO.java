package modeloDTO;

import java.sql.Date;
import java.util.List;

public class PedidoDTO {

    private Integer id_pedidocompra;
   private  String ped_fecha;
    private String ped_monto;
    private Integer id_estado;
    private String est_descri;
    private String usu_nombre;
    private Integer id_usuario;
    private Integer id_articulo;
    private Integer det_cant;
    private Integer det_precio;
    private List<PedidoDTO> lista_articulo;
    private Integer bandera;

    public Integer getId_pedidocompra() {
        return id_pedidocompra;
    }

    public void setId_pedidocompra(Integer id_pedidocompra) {
        this.id_pedidocompra = id_pedidocompra;
    }

    public String getPed_fecha() {
        return ped_fecha;
    }

    public void setPed_fecha(String ped_fecha) {
        this.ped_fecha = ped_fecha;
    }

    public String getPed_monto() {
        return ped_monto;
    }

    public void setPed_monto(String ped_monto) {
        this.ped_monto = ped_monto;
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

    public List<PedidoDTO> getLista_articulo() {
        return lista_articulo;
    }

    public void setLista_articulo(List<PedidoDTO> lista_articulo) {
        this.lista_articulo = lista_articulo;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    

    

   

   

}
