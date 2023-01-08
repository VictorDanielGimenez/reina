package modeloDTO;

public class DepartamentoDTO {

    private Integer cod_departamento;
    private String descripcion;
    private Integer bandera;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getCod_departamento() {
        return cod_departamento;
    }

    public void setCod_departamento(Integer cod_departamento) {
        this.cod_departamento = cod_departamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
