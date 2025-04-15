package com.crud.demo.services.contratos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crud.demo.models.DTO.PessoaDTO;

public interface PessoaService {

PessoaDTO criarPessoa(PessoaDTO pessoaDTO);
PessoaDTO buscarPessoaPorId(Long id);
void deletarPessoa(Long id);
PessoaDTO atualizarPessoa(Long id,PessoaDTO pessoaDTO);
Page<PessoaDTO> filtrarPessoas(String nome, String cpf, String cep,String cidade, String bairro,String estado,Pageable pageable);
Page<PessoaDTO> aniversariantesDeHoje(Pageable pageable);
Page<PessoaDTO> aniversariantesDoMes(int mes, Pageable pageable);
}
