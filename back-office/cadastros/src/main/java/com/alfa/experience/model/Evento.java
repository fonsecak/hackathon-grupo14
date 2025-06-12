package com.alfa.experience.model;

public class Evento {
    private Long id;
    private String nome;
    private String dtInicio;
    private String dtFim;
    private String local;
    private String valorIncricao;
    private String publicoAlvo;
    private String objetivo;
    private String banner;
    private String palestrante;
    private String especialidade;
    private String vagasMax;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Evento(){

    }

    public Evento(Long id, String vagasMax, String especialidade, String palestrante, String objetivo, String publicoAlvo, String valorIncricao, String local, String dtFim, String dtInicio, String nome) {
        this.id = id;
        this.vagasMax = vagasMax;
        this.especialidade = especialidade;
        this.palestrante = palestrante;
        this.objetivo = objetivo;
        this.publicoAlvo = publicoAlvo;
        this.valorIncricao = valorIncricao;
        this.local = local;
        this.dtFim = dtFim;
        this.dtInicio = dtInicio;
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(String dtInicio) {
        this.dtInicio = dtInicio;
    }

    public String getDtFim() {
        return dtFim;
    }

    public void setDtFim(String dtFim) {
        this.dtFim = dtFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getValorIncricao() {
        return valorIncricao;
    }

    public void setValorIncricao(String valorIncricao) {
        this.valorIncricao = valorIncricao;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(String palestrante) {
        this.palestrante = palestrante;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getVagasMax() {
        return vagasMax;
    }

    public void setVagasMax(String vagasMax) {
        this.vagasMax = vagasMax;
    }

}
