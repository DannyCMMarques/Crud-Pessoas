package com.crud.demo.models.mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.crud.demo.models.Endereco;
import com.crud.demo.models.Pessoa;
import com.crud.demo.models.DTO.EnderecoDTO;
import com.crud.demo.models.DTO.PessoaDTO;

@Component
public class PessoaMappers {
    public Pessoa toEntity(PessoaDTO dto) {

        Pessoa pessoa = Pessoa.builder()
                .nome(dto.getNome())
                .CPF(dto.getCPF())
                .dataNascimento(dto.getDataNascimento())
                .build();
        List<Endereco> enderecos = Optional.ofNullable(dto.getEnderecos())
                .orElse(Collections.emptyList())
                .stream()
                .map(enderecoDTO -> {
                    Endereco endereco = Endereco.builder()
                            .rua(enderecoDTO.getRua())
                            .numero(enderecoDTO.getNumero())
                            .bairro(enderecoDTO.getBairro())
                            .cidade(enderecoDTO.getCidade())
                            .estado(enderecoDTO.getEstado())
                            .CEP(enderecoDTO.getCEP())
                            .build();
                    return endereco;
                })
                .collect(Collectors.toList());

        pessoa.setEnderecos(enderecos);
        return pessoa;
    }

    public PessoaDTO toDto(Pessoa pessoa) {
        PessoaDTO dto = new PessoaDTO();
        dto.setNome(pessoa.getNome());
        dto.setCPF(pessoa.getCPF());
        dto.setDataNascimento(pessoa.getDataNascimento());

        List<EnderecoDTO> enderecosDTO = pessoa
                .getEnderecos()
                .stream()
                .map(endereco -> {
                    EnderecoDTO e = new EnderecoDTO();
                    e.setRua(endereco.getRua());
                    e.setNumero(endereco.getNumero());
                    e.setBairro(endereco.getBairro());
                    e.setCidade(endereco.getCidade());
                    e.setEstado(endereco.getEstado());
                    e.setCEP(endereco.getCEP());
                    return e;
                })
                .collect(Collectors.toList());

        dto.setEnderecos(enderecosDTO);
        return dto;
    }
}
