package com.crud.demo.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {
private Long id;

private String rua;

private long numero;

private String bairro;

private String cidade;

private String estado;

private String CEP;
}
