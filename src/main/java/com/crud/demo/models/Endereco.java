package com.crud.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "endereco")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Endereco {

@Id
@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
private Long id;

private String rua;

private long numero;

private String bairro;

private String cidade;

private String estado;

private String cep;

}
