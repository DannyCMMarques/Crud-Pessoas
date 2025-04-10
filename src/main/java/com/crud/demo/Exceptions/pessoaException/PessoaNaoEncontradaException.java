package com.crud.demo.Exceptions.pessoaException;

import org.springframework.http.HttpStatus;

import com.crud.demo.Exceptions.ApiException;

public class PessoaNaoEncontradaException extends ApiException {

      public PessoaNaoEncontradaException() {
                super("Cliente não encontrado", HttpStatus.NOT_FOUND);

}
}