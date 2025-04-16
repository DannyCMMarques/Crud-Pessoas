package com.crud.demo.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.models.DTO.PessoaDTO;
import com.crud.demo.services.PessoaServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/pessoas")
@RequiredArgsConstructor
@Tag(name = "Pessoas", description = "Endpoints para operações com pessoas")
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
    @Operation(summary = "Listar ou filtrar pessoas", description = "Retorna uma lista paginada de pessoas. É possível filtrar por nome, CPF, CEP, cidade, bairro ou estado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })

    public ResponseEntity<Page<PessoaDTO>> filtrarPessoas(
            @Parameter(description = "Filtrar por nome ") @RequestParam(required = false) String nome,
            @Parameter(description = "Filtrar por CPF") @RequestParam(required = false) String cpf,
            @Parameter(description = "Filtrar por CEP") @RequestParam(required = false) String cep,
            @Parameter(description = "Filtrar por cidade") @RequestParam(required = false) String cidade,
            @Parameter(description = "Filtrar por bairro") @RequestParam(required = false) String bairro,
            @Parameter(description = "Filtrar por estado") @RequestParam(required = false) String estado,
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenar") @RequestParam(defaultValue = "nome") String sortBy,
            @Parameter(description = "Direção da ordenação (asc ou desc)") @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
        Sort.by(sortBy).descending() :
        Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size,sort);
        Page<PessoaDTO> pessoas = pessoaService.filtrarPessoas(nome, cpf, cep, cidade, bairro, estado, pageable);
        if (pessoas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/aniversariantes/hoje")
    @Operation(summary = "Buscar aniversariantes de hoje", description = "Retorna uma lista de pessoas que fazem aniversário hoje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de aniversariantes retornada com sucesso")
    })
    public ResponseEntity<Page<PessoaDTO>> aniversariantesDeHoje(
        @Parameter(description = "Número da página")@RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Tamanho da página")@RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Campo para ordenar")  @RequestParam(defaultValue = "nome") String sortBy,
        @Parameter(description = "Direção da ordenação (asc ou desc)")  @RequestParam(defaultValue = "asc") String direction
            ) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
        Sort.by(sortBy).descending() :
        Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PessoaDTO> aniversariantes = pessoaService.aniversariantesDeHoje(pageable);
        return ResponseEntity.ok(aniversariantes);
    }

    @GetMapping("/aniversariantes")
    @Operation(summary = "Buscar aniversariantes do mês", description = "Retorna uma lista de pessoas que fazem aniversário no mês fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de aniversariantes do mês retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Mês inválido")
    })
    public ResponseEntity<Page<PessoaDTO>> aniversariantesDoMes(
            @Parameter(description = "Número do mês (1 a 12)") @RequestParam int mes,
            @Parameter(description = "Número da página")@RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenar")  @RequestParam(defaultValue = "nome") String sortBy,
            @Parameter(description = "Direção da ordenação (asc ou desc)")  @RequestParam(defaultValue = "asc") String direction
            ) {
        if (mes < 1 || mes > 12) {
            return ResponseEntity.badRequest().build();
        }
        Sort sort = direction.equalsIgnoreCase("desc") ?
        Sort.by(sortBy).descending() :
        Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<PessoaDTO> aniversariantes = pessoaService.aniversariantesDoMes(mes, pageable);
        return ResponseEntity.ok(aniversariantes);
    }
}
