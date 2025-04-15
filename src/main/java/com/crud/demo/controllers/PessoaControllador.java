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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/pessoas")
@RequiredArgsConstructor
public class PessoaControllador {

    private final PessoaServiceImpl pessoaService;

    @PostMapping
    @Operation(summary = "Cadastrar uma nova pessoa", description = "Cria uma nova pessoa com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado")
    })
    public ResponseEntity<PessoaDTO> cadastrarPessoa(@Valid @RequestBody PessoaDTO dto) {
        PessoaDTO pessoaCriada = pessoaService.criarPessoa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir uma pessoa", description = "Remove a pessoa do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pessoa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<Void> excluirPessoa(@PathVariable long id) {
        pessoaService.deletarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar uma pessoa", description = "Atualiza os dados de uma pessoa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PessoaDTO> atualizarCadastroPessoa(@PathVariable long id,
            @Valid @RequestBody PessoaDTO pessoaAtualizadoDTO) {
        PessoaDTO atualizado = pessoaService.atualizarPessoa(id, pessoaAtualizadoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar pessoa por ID", description = "Retorna os dados da pessoa com o ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PessoaDTO> buscarPessoaPorId(@PathVariable long id) {
        PessoaDTO pessoa = pessoaService.buscarPessoaPorId(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping
    @Operation(summary = "Listar todas as pessoas", description = "Retorna a lista completa de pessoas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso")
    })
    public ResponseEntity<List<PessoaDTO>> exibirPessoas() {
        List<PessoaDTO> pessoas = pessoaService.buscarTodasPessoas();
        return ResponseEntity.ok(pessoas);
    }
}
