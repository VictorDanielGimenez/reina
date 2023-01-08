package modeloDTO;

public class EmbarcacionDTO {

    private Integer cod_empuje;
    private String descripcion;
    private String matricula;
    private String letras;
    private String eslora;
    private String manga;
    private String puntal;
    private String calado;
    private Integer cod_tipo;
    private String descripcion_tipo;
    private Integer cod_nacionalidad;
    private String descripcion_nacionalidad;
    private Integer cod_empresa;
    private String descripcion_empresa;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getLetras() {
        return letras;
    }

    public void setLetras(String letras) {
        this.letras = letras;
    }

    public String getEslora() {
        return eslora;
    }

    public void setEslora(String eslora) {
        this.eslora = eslora;
    }

    public String getManga() {
        return manga;
    }

    public void setManga(String manga) {
        this.manga = manga;
    }

    public String getPuntal() {
        return puntal;
    }

    public void setPuntal(String puntal) {
        this.puntal = puntal;
    }

    public String getCalado() {
        return calado;
    }

    public void setCalado(String calado) {
        this.calado = calado;
    }

    public Integer getCod_tipo() {
        return cod_tipo;
    }

    public void setCod_tipo(Integer cod_tipo) {
        this.cod_tipo = cod_tipo;
    }

    public String getDescripcion_tipo() {
        return descripcion_tipo;
    }

    public void setDescripcion_tipo(String descripcion_tipo) {
        this.descripcion_tipo = descripcion_tipo;
    }
    private Integer bandera;

    public Integer getCod_empuje() {
        return cod_empuje;
    }

    public void setCod_empuje(Integer cod_empuje) {
        this.cod_empuje = cod_empuje;
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

    public Integer getCod_nacionalidad() {
        return cod_nacionalidad;
    }

    public void setCod_nacionalidad(Integer cod_nacionalidad) {
        this.cod_nacionalidad = cod_nacionalidad;
    }

    public String getDescripcion_nacionalidad() {
        return descripcion_nacionalidad;
    }

    public void setDescripcion_nacionalidad(String descripcion_nacionalidad) {
        this.descripcion_nacionalidad = descripcion_nacionalidad;
    }

    public Integer getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(Integer cod_empresa) {
        this.cod_empresa = cod_empresa;
    }

    public String getDescripcion_empresa() {
        return descripcion_empresa;
    }

    public void setDescripcion_empresa(String descripcion_empresa) {
        this.descripcion_empresa = descripcion_empresa;
    }
}
