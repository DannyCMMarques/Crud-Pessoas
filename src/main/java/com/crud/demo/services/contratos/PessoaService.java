package com.crud.demo.services.contratos;

import java.util.List;

import com.crud.demo.models.DTO.PessoaDTO;

public interface PessoaService {

PessoaDTO criarPessoa(PessoaDTO pessoaDTO);
PessoaDTO buscarPessoaPorId(Long id);
List<PessoaDTO> buscarTodasPessoas();
void deletarPessoa(Long id);
PessoaDTO atualizarPessoa(Long id,PessoaDTO pessoaDTO);
}
