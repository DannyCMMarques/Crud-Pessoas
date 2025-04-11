package com.crud.demo.models.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.crud.demo.models.Endereco;
import com.crud.demo.models.DTO.EnderecoDTO;

@Component
public class EnderecoMapper {

    public Endereco toEntity(EnderecoDTO dto) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .CEP(dto.getCEP())
                .build();
    }

    public EnderecoDTO toDto(Endereco entity) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(entity.getId());
        dto.setRua(entity.getRua());
        dto.setNumero(entity.getNumero());
        dto.setBairro(entity.getBairro());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setCEP(entity.getCEP());
        return dto;
    }

    public List<Endereco> toEntityList(List<EnderecoDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<EnderecoDTO> toDtoList(List<Endereco> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}