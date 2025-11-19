package com.example.tecnico.mapper;

import com.example.tecnico.dto.ClienteDTO;
import com.example.tecnico.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDTO toDTO(Cliente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "chamados", ignore = true)
    Cliente toEntity(ClienteDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "chamados", ignore = true)
    void updateEntityFromDTO(ClienteDTO dto, @MappingTarget Cliente entity);

    List<ClienteDTO> toDTOList(List<Cliente> entities);
}