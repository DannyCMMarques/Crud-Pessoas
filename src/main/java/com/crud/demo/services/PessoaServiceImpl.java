package com.crud.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.crud.demo.models.DTO.PessoaDTO;
import com.crud.demo.models.Pessoa;
import com.crud.demo.models.mappers.PessoaMappers;
import com.crud.demo.repositories.PessoaRepository;
import com.crud.demo.services.contratos.PessoaService;
import com.crud.demo.specifications.PessoaSpecifications;
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

    @Override
    public Page<PessoaDTO> filtrarPessoas(String nome, String cpf, String cep, String cidade, String bairro,String estado,
            Pageable pageable) {
        Specification<Pessoa> spec = Specification
                .where(PessoaSpecifications.comNomeContendo(nome))
                .and(PessoaSpecifications.comCpfContendo(cpf))
                .and(PessoaSpecifications.comCepContendo(cep))
                .and(PessoaSpecifications.comCidadeContendo(cidade))
                .and(PessoaSpecifications.comBairroContendo(bairro))
                .and(PessoaSpecifications.comEstadoContendo(estado));

        return pessoaRepository.findAll(spec, pageable)
                .map(pessoaMappers::toDto);
    }

    @Override
    public Page<PessoaDTO> aniversariantesDeHoje(Pageable pageable) {
        Specification<Pessoa> spec = PessoaSpecifications.aniversariantesDeHoje();
        return pessoaRepository.findAll(spec, pageable)
                .map(pessoaMappers::toDto);
    }

    @Override
    public Page<PessoaDTO> aniversariantesDoMes(int mes, Pageable pageable) {
        Specification<Pessoa> spec = PessoaSpecifications.aniversariantesDoMes(mes);
        return pessoaRepository.findAll(spec, pageable)
                .map(pessoaMappers::toDto);
    }

}