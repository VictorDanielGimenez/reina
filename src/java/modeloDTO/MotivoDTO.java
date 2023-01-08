package modeloDTO;

public class MotivoDTO {
    private Integer cod_motivo;
    private String descripcion;
    private Integer bandera;

    public Integer getCod_motivo() {
        return cod_motivo;
    }

    public void setCod_motivo(Integer cod_motivo) {
        this.cod_motivo = cod_motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }
    
}
