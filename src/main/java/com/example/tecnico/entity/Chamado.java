package com.example.tecnico.entity;

import com.example.tecnico.enums.Prioridade;
import com.example.tecnico.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataAbertura = LocalDateTime.now();

    private LocalDateTime dataFechamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade = Prioridade.BAIXA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ABERTO;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }
}