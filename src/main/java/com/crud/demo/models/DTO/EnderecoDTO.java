package com.crud.demo.models.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

    @Schema(description = "ID do endereço", example = "1")
    private Long id;

    @Schema(description = "Rua do endereço", example = "Rua das Flores")
    private String rua;

    @Schema(description = "Número da residência", example = "123")
    private long numero;

    @Schema(description = "Bairro do endereço", example = "Centro")
    private String bairro;

    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado (sigla)", example = "SP")
    private String estado;

    @Schema(description = "CEP no formato 00000000", example = "12345678")
    @Size(min = 8, max = 8, message = "O CEP deve conter exatamente 8 dígitos")
    private String cep;
}
