package com.example.tecnico.dto;

import com.example.tecnico.entity.Cliente;

public class ClienteDTO extends PessoaDTO {

    public ClienteDTO() { }

    public ClienteDTO(Long id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha, null, null);
    }

    public ClienteDTO(Cliente entity) {
        super(entity);
    }
}


