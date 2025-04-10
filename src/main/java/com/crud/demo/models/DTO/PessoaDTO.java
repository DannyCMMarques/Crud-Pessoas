package com.crud.demo.models.DTO;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PessoaDTO {


    private long id;
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos")
    private String CPF;

    private LocalDate dataNascimento;

    private List<EnderecoDTO>enderecos;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Integer getIdade(){
        if (dataNascimento == null)
            return null;
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
