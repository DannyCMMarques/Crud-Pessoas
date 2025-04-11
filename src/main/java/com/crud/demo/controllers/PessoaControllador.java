package com.crud.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.models.DTO.PessoaDTO;
import com.crud.demo.services.PessoaServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaControllador {


        private final PessoaServiceImpl pessoaService;

        @PostMapping
        public ResponseEntity<PessoaDTO> cadastrarPessoa(@Valid @RequestBody PessoaDTO dto) {
            PessoaDTO pessoaCriada = pessoaService.criarPessoa(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
        }

        @DeleteMapping("{id}")
        public ResponseEntity<Void> excluirPessoa(@PathVariable long id) {
            pessoaService.deletarPessoa(id);
            return ResponseEntity.noContent().build();
        }

        @PutMapping("{id}")
        public ResponseEntity<PessoaDTO> atualizarCadastroPessoa(@PathVariable long id,
                @Valid @RequestBody PessoaDTO pessoaAtualizadoDTO) {
            PessoaDTO atualizado = pessoaService.atualizarPessoa(id, pessoaAtualizadoDTO);
            return ResponseEntity.ok(atualizado);
        }

        @GetMapping("{id}")
        public ResponseEntity<PessoaDTO> buscarPessoaPorId(@PathVariable long id) {
            PessoaDTO pessoa = pessoaService.buscarPessoaPorId(id);
            return ResponseEntity.ok(pessoa);
        }

        @GetMapping
        public ResponseEntity<List<PessoaDTO>> exibirPessoas() {
            List<PessoaDTO> pessoas = pessoaService.buscarTodasPessoas();
            return ResponseEntity.ok(pessoas);
        }
    }
