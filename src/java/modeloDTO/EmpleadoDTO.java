package modeloDTO;

import java.sql.Date;


public class EmpleadoDTO {

    private Integer cod_empleado;
    private String emp_ci;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private Integer cod_ciudad;
    private String ciudad_descripcion;
    private String fecha_nac;

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }
    private Integer cod_nacionalidad;
    private String nacionalidad_descripcion;

    public String getCiudad_descripcion() {
        return ciudad_descripcion;
    }

    public void setCiudad_descripcion(String ciudad_descripcion) {
        this.ciudad_descripcion = ciudad_descripcion;
    }

    public String getNacionalidad_descripcion() {
        return nacionalidad_descripcion;
    }

    public void setNacionalidad_descripcion(String nacionalidad_descripcion) {
        this.nacionalidad_descripcion = nacionalidad_descripcion;
    }
    private Integer bandera;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getCod_ciudad() {
        return cod_ciudad;
    }

    public void setCod_ciudad(Integer cod_ciudad) {
        this.cod_ciudad = cod_ciudad;
    }

    public Integer getCod_nacionalidad() {
        return cod_nacionalidad;
    }

    public void setCod_nacionalidad(Integer cod_nacionalidad) {
        this.cod_nacionalidad = cod_nacionalidad;
    }

    public Integer getCod_empleado() {
        return cod_empleado;
    }

    public void setCod_empleado(Integer cod_empleado) {
        this.cod_empleado = cod_empleado;
    }

    public String getEmp_ci() {
        return emp_ci;
    }

    public void setEmp_ci(String emp_ci) {
        this.emp_ci = emp_ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
