package com.crud.demo.utils;

import java.time.LocalDate;
import java.util.List;

import com.crud.demo.models.Endereco;
import com.crud.demo.models.Pessoa;
import com.crud.demo.models.DTO.EnderecoDTO;
import com.crud.demo.models.DTO.PessoaDTO;

public class TestDataFactory {

    public static final String CPF_PURO = "12345678900";
    public static final String NOME_PADRAO = "João da Silva";
    public static final LocalDate DATA_NASCIMENTO = LocalDate.of(1990, 5, 10);
    public static final String URL_BASE = "/pessoas";
    public static final String NOME_ATUALIZADO = "Nome Atualizado";

    public static PessoaDTO criarPessoaDTOValida() {
        PessoaDTO dto = new PessoaDTO();
        dto.setNome(NOME_PADRAO);
        dto.setCpf(CPF_PURO);
        dto.setDataNascimento(DATA_NASCIMENTO);
        dto.setEnderecos(List.of(
                criarEnderecoDTO1(),
                criarEnderecoDTO2()));
        return dto;
    }

    public static EnderecoDTO criarEnderecoDTO1() {
        return EnderecoDTO.builder()
                .rua("Rua das Flores")
                .numero(123)
                .bairro("Centro")
                .cidade("Itaperuna")
                .estado("RJ")
                .CEP("28300-000")
                .build();
    }

    public static EnderecoDTO criarEnderecoDTO2() {
        return EnderecoDTO.builder()
                .rua("Avenida Brasil")
                .numero(456)
                .bairro("Boa Vista")
                .cidade("Campos dos Goytacazes")
                .estado("RJ")
                .CEP("28000-000")
                .build();
    }

    public static Pessoa criarPessoaEntity() {
        return Pessoa.builder()
                .id(1L)
                .nome("Nome 1")
                .cpf(CPF_PURO)
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .enderecos(List.of(criarEndereco1(), criarEndereco2()))
                .build();
    }

    public static Endereco criarEndereco1() {
        return Endereco.builder()
                .rua("Rua 1")
                .numero(123)
                .bairro("Bairro 1")
                .cidade("Cidade 1")
                .estado("Estado 1")
                .CEP("12345-678")
                .build();
    }

    public static Endereco criarEndereco2() {
        return Endereco.builder()
                .rua("Rua 2")
                .numero(456)
                .bairro("Bairro 2")
                .cidade("Cidade 2")
                .estado("Estado 2")
                .CEP("98765-432")
                .build();
    }
}