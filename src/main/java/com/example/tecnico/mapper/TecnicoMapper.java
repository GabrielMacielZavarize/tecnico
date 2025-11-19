package com.example.tecnico.mapper;

import com.example.tecnico.dto.TecnicoDTO;
import com.example.tecnico.entity.Tecnico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TecnicoMapper {

    TecnicoDTO toDTO(Tecnico entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "chamados", ignore = true)
    Tecnico toEntity(TecnicoDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "chamados", ignore = true)
    void updateEntityFromDTO(TecnicoDTO dto, @MappingTarget Tecnico entity);

    List<TecnicoDTO> toDTOList(List<Tecnico> entities);
}