package com.example.tecnico.dto;

import com.example.tecnico.entity.Chamado;
import com.example.tecnico.enums.Prioridade;
import com.example.tecnico.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ChamadoDTO {

    private Long id;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    @NotNull(message = "Prioridade é obrigatória")
    private Prioridade prioridade;

    @NotNull(message = "Status é obrigatório")
    private Status status;

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 3, max = 100, message = "Título deve ter entre 3 e 100 caracteres")
    private String titulo;

    @Size(max = 2000, message = "Observações deve ter no máximo 2000 caracteres")
    private String observacoes;

    @NotNull(message = "Cliente é obrigatório")
    @Valid
    private IdNomeDTO cliente;

    @NotNull(message = "Técnico é obrigatório")
    @Valid
    private IdNomeDTO tecnico;

    public ChamadoDTO() { }

    public ChamadoDTO(Chamado entity) {
        this.id = entity.getId();
        this.dataAbertura = entity.getDataAbertura();
        this.dataFechamento = entity.getDataFechamento();
        this.prioridade = entity.getPrioridade();
        this.status = entity.getStatus();
        this.titulo = entity.getTitulo();
        this.observacoes = entity.getObservacoes();
        this.cliente = new IdNomeDTO(entity.getCliente().getId(), entity.getCliente().getNome());
        this.tecnico = new IdNomeDTO(entity.getTecnico().getId(), entity.getTecnico().getNome());
    }

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

    public IdNomeDTO getCliente() { return cliente; }
    public void setCliente(IdNomeDTO cliente) { this.cliente = cliente; }

    public IdNomeDTO getTecnico() { return tecnico; }
    public void setTecnico(IdNomeDTO tecnico) { this.tecnico = tecnico; }
}


