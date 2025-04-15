package com.crud.demo.Exceptions.pessoaException;

import org.springframework.http.HttpStatus;

import com.crud.demo.Exceptions.ApiException;


public class CpfJaCadastradoException extends ApiException  {

    public CpfJaCadastradoException() {

        super("Cpf já cadastrado", HttpStatus.CONFLICT);
    }
}