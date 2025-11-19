package com.example.tecnico.services;

import com.example.tecnico.dto.ClienteDTO;
import com.example.tecnico.entity.Cliente;
import com.example.tecnico.repositories.ClienteRepository;
import com.example.tecnico.repositories.TecnicoRepository;
import com.example.tecnico.exceptions.DatabaseException;
import com.example.tecnico.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ClienteDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        Cliente entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return new ClienteDTO(entity);
    }

    @Transactional
    public ClienteDTO insert(ClienteDTO dto) {
        if (repository.existsByCpf(dto.getCpf()) || tecnicoRepository.existsByCpf(dto.getCpf())) {
            throw new DatabaseException("CPF já cadastrado");
        }
        if (repository.existsByEmail(dto.getEmail()) || tecnicoRepository.existsByEmail(dto.getEmail())) {
            throw new DatabaseException("Email já cadastrado");
        }

        Cliente entity = new Cliente();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ClienteDTO(entity);
    }

    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ClienteDTO(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir o cliente (dados relacionados).");
        }
    }

    private void copyDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());

        if (dto.getPerfis() != null) {
            entity.getPerfis().clear();
            entity.getPerfis().addAll(dto.getPerfis());
        }
    }
}
