package com.example.tecnico.services;

import com.example.tecnico.dto.TecnicoDTO;
import com.example.tecnico.entity.Tecnico;
import com.example.tecnico.mapper.TecnicoMapper;
import com.example.tecnico.repositories.TecnicoRepository;
import com.example.tecnico.repositories.ClienteRepository;
import com.example.tecnico.exceptions.DatabaseException;
import com.example.tecnico.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TecnicoMapper mapper;

    @Transactional(readOnly = true)
    public List<TecnicoDTO> findAll() {
        List<Tecnico> entities = repository.findAll();
        return mapper.toDTOList(entities);
    }

    @Transactional(readOnly = true)
    public TecnicoDTO findById(Long id) {
        Tecnico entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado"));
        return mapper.toDTO(entity);
    }

    @Transactional
    public TecnicoDTO insert(TecnicoDTO dto) {
        if (repository.existsByCpf(dto.getCpf()) || clienteRepository.existsByCpf(dto.getCpf())) {
            throw new DatabaseException("CPF já cadastrado");
        }

        if (repository.existsByEmail(dto.getEmail()) || clienteRepository.existsByEmail(dto.getEmail())) {
            throw new DatabaseException("Email já cadastrado");
        }

        Tecnico entity = mapper.toEntity(dto);

        if (dto.getPerfis() != null && !dto.getPerfis().isEmpty()) {
            entity.getPerfis().addAll(dto.getPerfis());
        }

        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public TecnicoDTO update(Long id, TecnicoDTO dto) {
        Tecnico entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado"));

        mapper.updateEntityFromDTO(dto, entity);

        if (dto.getPerfis() != null) {
            entity.getPerfis().clear();
            entity.getPerfis().addAll(dto.getPerfis());
        }

        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Técnico não encontrado");
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir o técnico. Ele possui chamados associados.");
        }
    }
}