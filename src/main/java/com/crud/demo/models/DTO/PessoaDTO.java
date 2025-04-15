package com.crud.demo.models.DTO;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Representa uma pessoa com dados pessoais e lista de endereços")

public class PessoaDTO {

    @Schema(description = "ID gerado automaticamente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;
    @NotNull(message = "Nome é obrigatório")

    @Schema(description = "Nome completo da pessoa", example = "João da Silva")
    private String nome;

    @Schema(description = "Número do CPF da pessoa", example = "12345678900")
    @NotNull(message = "cpf é obrigatório")
    @Size(min = 11, max = 11, message = "O cpf deve conter exatamente 11 dígitos")
    private String cpf;

    @Schema(description = "Data de nascimento da pessoa no formato ISO", example = "1990-01-01")
    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @Schema(description = "Lista de endereços da pessoa")
    private List<EnderecoDTO> enderecos;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Idade calculada automaticamente a partir da data de nascimento", example = "33", accessMode = Schema.AccessMode.READ_ONLY)
    public Integer getIdade() {
        if (dataNascimento == null)
            return null;
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
