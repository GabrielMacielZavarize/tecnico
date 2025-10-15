package com.example.tecnico.entity;

import com.example.tecnico.enums.Perfil;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pessoa_perfis", joinColumns = @JoinColumn(name = "pessoa_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private Set<Perfil> perfis = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public Long getId() { return id;}
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Set<Perfil> getPerfis() { return perfis; }
    public void setPerfis(Set<Perfil> perfis) { this.perfis = perfis; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}