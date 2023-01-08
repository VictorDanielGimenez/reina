
package modeloDTO;

public class BancoDTO {
  private Integer cod_banco;
  private String descripcion;
  private String ruc;
  private String telefono;
  private String direccion;
  private String correo;
  private Integer cod_ciudad;
  private String ciudad_descripcion;
  private Integer cod_nacionalidad;
  private String nacionalidad_descripcion;
  private Integer bandera;

    public Integer getCod_banco() {
        return cod_banco;
    }

    public void setCod_banco(Integer cod_banco) {
        this.cod_banco = cod_banco;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getCod_ciudad() {
        return cod_ciudad;
    }

    public void setCod_ciudad(Integer cod_ciudad) {
        this.cod_ciudad = cod_ciudad;
    }

    public String getCiudad_descripcion() {
        return ciudad_descripcion;
    }

    public void setCiudad_descripcion(String ciudad_descripcion) {
        this.ciudad_descripcion = ciudad_descripcion;
    }

    public Integer getCod_nacionalidad() {
        return cod_nacionalidad;
    }

    public void setCod_nacionalidad(Integer cod_nacionalidad) {
        this.cod_nacionalidad = cod_nacionalidad;
    }

    public String getNacionalidad_descripcion() {
        return nacionalidad_descripcion;
    }

    public void setNacionalidad_descripcion(String nacionalidad_descripcion) {
        this.nacionalidad_descripcion = nacionalidad_descripcion;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }
}
