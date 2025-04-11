package com.crud.demo.models.mappers;

import java.util.Collections;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.crud.demo.models.Pessoa;
import com.crud.demo.models.DTO.PessoaDTO;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class PessoaMappers {

    private final EnderecoMapper enderecoMapper;

    public Pessoa toEntity(PessoaDTO dto) {
        return Pessoa.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .dataNascimento(dto.getDataNascimento())
                .enderecos(enderecoMapper.toEntityList(
                        Optional.ofNullable(dto.getEnderecos()).orElse(Collections.emptyList())))
                .build();
    }

    public PessoaDTO toDto(Pessoa pessoa) {
        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setCpf(pessoa.getCpf());
        dto.setDataNascimento(pessoa.getDataNascimento());
        dto.setEnderecos(enderecoMapper.toDtoList(pessoa.getEnderecos()));
        return dto;
    }
}