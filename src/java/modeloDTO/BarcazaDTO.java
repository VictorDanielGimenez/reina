package modeloDTO;

public class BarcazaDTO {

    private Integer cod_barcaza;
    private String descripcion;
    private String matricula;
    private String arqueo_bruto;
    private String arqueo_neto;
    private String eslora;
    private String manga;
    private String puntal;
    private Integer codigo;
    private String tipo_barcaza;
    private Integer cod_empresa;
    private String empresa;
    private Integer cod_nacionalidad;
    private String nacionalidad;

    public Integer getCod_nacionalidad() {
        return cod_nacionalidad;
    }

    public void setCod_nacionalidad(Integer cod_nacionalidad) {
        this.cod_nacionalidad = cod_nacionalidad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    private Integer bandera;

    public Integer getCod_barcaza() {
        return cod_barcaza;
    }

    public void setCod_barcaza(Integer cod_barcaza) {
        this.cod_barcaza = cod_barcaza;
    }

    public String getDescripicion() {
        return descripcion;
    }

    public void setDescripicion(String descripicion) {
        this.descripcion = descripicion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getArqueo_bruto() {
        return arqueo_bruto;
    }

    public void setArqueo_bruto(String arqueo_bruto) {
        this.arqueo_bruto = arqueo_bruto;
    }

    public String getArqueo_neto() {
        return arqueo_neto;
    }

    public void setArqueo_neto(String arqueo_neto) {
        this.arqueo_neto = arqueo_neto;
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTipo_barcaza() {
        return tipo_barcaza;
    }

    public void setTipo_barcaza(String tipo_barcaza) {
        this.tipo_barcaza = tipo_barcaza;
    }

    public Integer getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(Integer cod_empresa) {
        this.cod_empresa = cod_empresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

}
