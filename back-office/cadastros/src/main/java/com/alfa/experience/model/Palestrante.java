package com.alfa.experience.model;

import java.util.Objects;

public class Palestrante {
    private Long id;
    private String nome;
    private String sobre;
    private String foto;

    public Palestrante() {
    }

    public Palestrante(Long id, String nome, String sobre, String foto) {
        this.id = id;
        this.nome = nome;
        this.sobre = sobre;
        this.foto = foto;
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

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    //Isso garante que dois objetos Palestrante com o mesmo id sejam considerados iguais,
    // permitindo que cbPalestrante.setSelectedItem funcione.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Palestrante that = (Palestrante) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
