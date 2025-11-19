package com.example.tecnico.mapper;

import com.example.tecnico.dto.ChamadoDTO;
import com.example.tecnico.entity.Chamado;
import com.example.tecnico.entity.Cliente;
import com.example.tecnico.entity.Tecnico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChamadoMapper {

    @Mapping(source = "cliente.id", target = "cliente.id")
    @Mapping(source = "cliente.nome", target = "cliente.nome")
    @Mapping(source = "tecnico.id", target = "tecnico.id")
    @Mapping(source = "tecnico.nome", target = "tecnico.nome")
    ChamadoDTO toDTO(Chamado entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAbertura", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    Chamado toEntity(ChamadoDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAbertura", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    void updateEntityFromDTO(ChamadoDTO dto, @MappingTarget Chamado entity);

    List<ChamadoDTO> toDTOList(List<Chamado> entities);

    default Chamado setClienteAndTecnico(Chamado chamado, Cliente cliente, Tecnico tecnico) {
        chamado.setCliente(cliente);
        chamado.setTecnico(tecnico);
        return chamado;
    }
}
