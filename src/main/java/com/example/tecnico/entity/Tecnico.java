package com.example.tecnico.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tecnicos")
public class Tecnico extends Pessoa {

    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    public List<Chamado> getChamados() { return chamados; }
    public void setChamados(List<Chamado> chamados) { this.chamados = chamados; }
}