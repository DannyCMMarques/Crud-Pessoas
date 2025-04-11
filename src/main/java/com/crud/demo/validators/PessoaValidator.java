package com.crud.demo.validators;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.crud.demo.Exceptions.pessoaException.CpfJaCadastradoException;
import com.crud.demo.Exceptions.pessoaException.PessoaNaoEncontradaException;
import com.crud.demo.models.Pessoa;
import com.crud.demo.repositories.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PessoaValidator {
    private final PessoaRepository pessoaRepository;

    public void validarCadastro(String cpf) {

        Optional<Pessoa> clienteCadastrado = pessoaRepository.findBycpf(cpf);
        if (clienteCadastrado.isPresent()) {
            throw new CpfJaCadastradoException();
        }
    }

    public Pessoa validarExistencia(long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(PessoaNaoEncontradaException::new);

    }
}