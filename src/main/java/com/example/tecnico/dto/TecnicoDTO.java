package com.example.tecnico.dto;

import com.example.tecnico.entity.Tecnico;

public class TecnicoDTO extends PessoaDTO {

    public TecnicoDTO() { }

    public TecnicoDTO(Long id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha, null, null);
    }

    public TecnicoDTO(Tecnico entity) {
        super(entity);
    }
}


