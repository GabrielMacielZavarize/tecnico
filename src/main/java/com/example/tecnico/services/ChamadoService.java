package com.example.tecnico.services;

import com.example.tecnico.dto.ChamadoDTO;
import com.example.tecnico.entity.Chamado;
import com.example.tecnico.entity.Cliente;
import com.example.tecnico.entity.Tecnico;
import com.example.tecnico.enums.Status;
import com.example.tecnico.exceptions.DatabaseException;
import com.example.tecnico.mapper.ChamadoMapper;
import com.example.tecnico.repositories.ChamadoRepository;
import com.example.tecnico.repositories.ClienteRepository;
import com.example.tecnico.repositories.TecnicoRepository;
import com.example.tecnico.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ChamadoMapper mapper;

    @Transactional(readOnly = true)
    public List<ChamadoDTO> findAll() {
        List<Chamado> entities = chamadoRepository.findAll();
        return mapper.toDTOList(entities);
    }

    @Transactional(readOnly = true)
    public ChamadoDTO findById(Long id) {
        Chamado entity = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
        return mapper.toDTO(entity);
    }

    @Transactional
    public ChamadoDTO insert(ChamadoDTO dto) {
        // Busca Cliente e Técnico
        Cliente cliente = buscarCliente(dto);
        Tecnico tecnico = buscarTecnico(dto);

        // Converte DTO para Entity
        Chamado entity = mapper.toEntity(dto);

        // Seta as relações
        mapper.setClienteAndTecnico(entity, cliente, tecnico);

        // Salva e retorna
        entity = chamadoRepository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public ChamadoDTO update(Long id, ChamadoDTO dto) {
        Chamado entity = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));

        // Busca Cliente e Técnico
        Cliente cliente = buscarCliente(dto);
        Tecnico tecnico = buscarTecnico(dto);

        // Atualiza os campos do chamado
        mapper.updateEntityFromDTO(dto, entity);

        // Atualiza as relações
        entity.setCliente(cliente);
        entity.setTecnico(tecnico);

        // Gerencia data de fechamento
        if (dto.getDataFechamento() != null) {
            entity.setDataFechamento(dto.getDataFechamento());
        } else if (dto.getStatus() == Status.ENCERRADO && entity.getDataFechamento() == null) {
            entity.setDataFechamento(LocalDateTime.now());
        }

        entity = chamadoRepository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        Chamado entity = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
        if (entity.getStatus() != Status.ENCERRADO) {
            throw new DatabaseException("Não é possivel excluir um chamado que ainda não foi encerrado" + entity.getStatus());
        }
        chamadoRepository.delete(entity);
    }

    private Cliente buscarCliente(ChamadoDTO dto) {
        if (dto.getCliente() == null || dto.getCliente().getId() == null) {
            throw new ResourceNotFoundException("Cliente é obrigatório");
        }
        Long clienteId = dto.getCliente().getId();
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + clienteId));
    }

    private Tecnico buscarTecnico(ChamadoDTO dto) {
        if (dto.getTecnico() == null || dto.getTecnico().getId() == null) {
            throw new ResourceNotFoundException("Técnico é obrigatório");
        }
        Long tecnicoId = dto.getTecnico().getId();
        return tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado: " + tecnicoId));
    }
}