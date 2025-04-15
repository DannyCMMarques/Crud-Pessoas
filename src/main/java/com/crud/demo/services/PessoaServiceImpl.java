package com.crud.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crud.demo.models.DTO.PessoaDTO;
import com.crud.demo.models.Pessoa;
import com.crud.demo.models.mappers.PessoaMappers;
import com.crud.demo.repositories.PessoaRepository;
import com.crud.demo.services.contratos.PessoaService;
import com.crud.demo.validators.PessoaValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMappers pessoaMappers;
    private final PessoaValidator pessoaValidator;

    @Override
    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO) {

        Pessoa pessoa = pessoaMappers.toEntity(pessoaDTO);
        pessoaValidator.validarCadastro(pessoa.getCpf());

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        return pessoaMappers.toDto(pessoaSalva);

    }

    @Override
    public PessoaDTO buscarPessoaPorId(Long id) {
        Pessoa pessoa = pessoaValidator.validarExistencia(id);
        return pessoaMappers.toDto(pessoa);
    }

    @Override
    public List<PessoaDTO> buscarTodasPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        List<PessoaDTO> pessoasDTO = pessoas.stream()
                .map(pessoaMappers::toDto)
                .toList();
        return pessoasDTO;
    }

    @Override
public void deletarPessoa(Long id) {
        pessoaValidator.validarExistencia(id);
        pessoaRepository.deleteById(id);
    }

    @Override
    public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
        pessoaValidator.validarExistencia(id);

        Pessoa pessoa = pessoaMappers.toEntity(pessoaDTO);
        pessoa.setId(id);

        Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);
        return pessoaMappers.toDto(pessoaAtualizada);
    }
}