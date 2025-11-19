package com.example.tecnico.services;
import com.example.tecnico.dto.ChamadoDTO;
import com.example.tecnico.entity.Chamado;
import com.example.tecnico.entity.Cliente;
import com.example.tecnico.entity.Tecnico;
import com.example.tecnico.enums.Status;
import com.example.tecnico.repositories.ChamadoRepository;
import com.example.tecnico.repositories.ClienteRepository;
import com.example.tecnico.repositories.TecnicoRepository;
import com.example.tecnico.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Transactional(readOnly = true)
    public List<ChamadoDTO> findAll() {
        return chamadoRepository.findAll()
                .stream()
                .map(ChamadoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChamadoDTO findById(Long id) {
        Chamado entity = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
        return new ChamadoDTO(entity);
    }

    @Transactional
    public ChamadoDTO insert(ChamadoDTO dto) {
        Chamado entity = new Chamado();
        copyDtoToEntity(dto, entity, true);
        entity = chamadoRepository.save(entity);
        return new ChamadoDTO(entity);
    }

    @Transactional
    public ChamadoDTO update(Long id, ChamadoDTO dto) {
        Chamado entity = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
        copyDtoToEntity(dto, entity, false);
        entity = chamadoRepository.save(entity);
        return new ChamadoDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        Chamado entity = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
        chamadoRepository.delete(entity);
    }

    private void copyDtoToEntity(ChamadoDTO dto, Chamado entity, boolean isInsert) {

        // PRIORIDADE / STATUS / TEXTO
        entity.setPrioridade(dto.getPrioridade());
        entity.setStatus(dto.getStatus());
        entity.setTitulo(dto.getTitulo());
        entity.setObservacoes(dto.getObservacoes());

        // DATA FECHAMENTO
        if (dto.getDataFechamento() != null) {
            entity.setDataFechamento(dto.getDataFechamento());
        } else if (dto.getStatus() == Status.ENCERRADO && entity.getDataFechamento() == null) {
            entity.setDataFechamento(LocalDateTime.now());
        }

        // CLIENTE
        if (dto.getCliente() == null || dto.getCliente().getId() == null) {
            throw new ResourceNotFoundException("Cliente é obrigatório");
        }
        Long clienteId = dto.getCliente().getId();
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + clienteId));
        entity.setCliente(cliente);

        // TÉCNICO
        if (dto.getTecnico() == null || dto.getTecnico().getId() == null) {
            throw new ResourceNotFoundException("Técnico é obrigatório");
        }
        Long tecnicoId = dto.getTecnico().getId();
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado: " + tecnicoId));
        entity.setTecnico(tecnico);
    }
}
