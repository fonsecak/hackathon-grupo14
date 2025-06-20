package com.alfa.experience.model;

import java.sql.Timestamp;

public class Evento {
    private Long id;
    private String nome;
    private String descricao;
    private Timestamp dtInicio;
    private Timestamp dtFim;
    private String local;
    private String valorInscricao;
    private String publicoAlvo;
    private String objetivo;
    private String banner;
    private Integer vagasMaximas;
    private Long idPalestrantes;

    public Evento() {
    }

    public Evento(Long id, String nome, String descricao, Timestamp dtInicio, Timestamp dtFim, String local, String valorInscricao, String publicoAlvo, String objetivo, String banner, Integer vagasMaximas, Long idPalestrantes) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.local = local;
        this.valorInscricao = valorInscricao;
        this.publicoAlvo = publicoAlvo;
        this.objetivo = objetivo;
        this.banner = banner;
        this.vagasMaximas = vagasMaximas;
        this.idPalestrantes = idPalestrantes;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Timestamp dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Timestamp getDtFim() {
        return dtFim;
    }

    public void setDtFim(Timestamp dtFim) {
        this.dtFim = dtFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getValorInscricao() {
        return valorInscricao;
    }

    public void setValorInscricao(String valorInscricao) {
        this.valorInscricao = valorInscricao;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getVagasMaximas() {
        return vagasMaximas;
    }

    public void setVagasMaximas(Integer vagasMaximas) {
        this.vagasMaximas = vagasMaximas;
    }

    public Long getIdPalestrantes() {
        return idPalestrantes;
    }

    public void setIdPalestrantes(Long idPalestrantes) {
        this.idPalestrantes = idPalestrantes;
    }
}