package com.crud.demo.models.DTO;

import lombok.Data;

@Data
public class EnderecoDTO {
private long id;

private String rua;

private long numero;

private String bairro;

private String cidade;

private String estado;

private String CEP;
}
