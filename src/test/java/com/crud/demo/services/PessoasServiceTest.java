package com.crud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.crud.demo.models.Pessoa;
import com.crud.demo.models.DTO.PessoaDTO;
import com.crud.demo.models.mappers.EnderecoMapper;
import com.crud.demo.models.mappers.PessoaMappers;
import com.crud.demo.repositories.EnderecoRepository;
import com.crud.demo.repositories.PessoaRepository;
import com.crud.demo.utils.TestDataFactory;
import com.crud.demo.validators.PessoaValidator;

@ExtendWith(MockitoExtension.class)
public class PessoasServiceTest {
    @InjectMocks
    private PessoaServiceImpl pessoaService;
    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PessoaValidator pessoaValidator;

    @Mock
    private EnderecoMapper enderecoMapper;

    private PessoaMappers pessoaMappers;

    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;

    @BeforeEach
    public void setUp() {
        pessoaMappers = new PessoaMappers(enderecoMapper);
        pessoaService = new PessoaServiceImpl(pessoaRepository, pessoaMappers, pessoaValidator);

        pessoa = TestDataFactory.criarPessoaEntity();
        pessoaDTO = TestDataFactory.criarPessoaDTOValida();
    }

    @Test
    @DisplayName("Deve salvar uma pessoa com dois endereços corretamente")
    public void deveCriarPessoaCorretamente() {
        // Garante que pessoa e pessoaDTO estejam populados corretamente
        assertNotNull(pessoa);
        assertNotNull(pessoa.getEnderecos());

        when(enderecoMapper.toEntityList(any())).thenReturn(pessoa.getEnderecos());
        when(enderecoMapper.toDtoList(any())).thenReturn(pessoaDTO.getEnderecos());
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);
        doNothing().when(pessoaValidator).validarCadastro(anyString());

        PessoaDTO resultado = pessoaService.criarPessoa(pessoaDTO);

        verify(pessoaValidator).validarCadastro(TestDataFactory.CPF_PURO);
        verify(pessoaRepository).save(any(Pessoa.class));
        assertEquals(TestDataFactory.CPF_PURO, resultado.getCpf());
        assertEquals(2, resultado.getEnderecos().size());
    }

    @Test
    @DisplayName("Deve buscar uma pessoa pelo ID")
    public void deveBuscarPessoaPorID() {
        when(pessoaValidator.validarExistencia(1L)).thenReturn(pessoa);
        when(enderecoMapper.toDtoList(any())).thenReturn(pessoaDTO.getEnderecos());

        PessoaDTO resultado = pessoaService.buscarPessoaPorId(1L);

        verify(pessoaValidator).validarExistencia(1L);
        assertEquals(pessoa.getNome(), resultado.getNome());
        assertEquals(pessoa.getCpf(), resultado.getCpf());
        assertEquals(2, resultado.getEnderecos().size());
    }

    @Test
    @DisplayName("Deve buscar todas as pessoas")
    public void deveRetornarTodasPessoas2() {
        when(pessoaRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<Pessoa>(List.of(pessoa)));
        when(enderecoMapper.toDtoList(any())).thenReturn(pessoaDTO.getEnderecos());
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());

        Page<PessoaDTO> resultado = pessoaService.filtrarPessoas(null, null, null, null, null, null, pageable);

        assertEquals(1, resultado.getContent().size());
        assertEquals(pessoa.getNome(), resultado.getContent().get(0).getNome());
        assertEquals(pessoa.getCpf(), resultado.getContent().get(0).getCpf());
        assertEquals(2, resultado.getContent().get(0).getEnderecos().size());
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa")
    public void deveAtualizarPessoa() {
        Pessoa pessoaAtualizada = Pessoa.builder()
                .id(1L)
                .nome(TestDataFactory.NOME_ATUALIZADO)
                .cpf(TestDataFactory.CPF_PURO)
                .dataNascimento(pessoa.getDataNascimento())
                .enderecos(List.of(TestDataFactory.criarEndereco1()))
                .build();

        when(pessoaValidator.validarExistencia(1L)).thenReturn(pessoa);
        when(enderecoMapper.toEntityList(any())).thenReturn(pessoaAtualizada.getEnderecos());
        when(enderecoMapper.toDtoList(any())).thenReturn(
                List.of(TestDataFactory.criarEnderecoDTO1()));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaAtualizada);

        PessoaDTO resultado = pessoaService.atualizarPessoa(1L, pessoaDTO);

        verify(pessoaValidator).validarExistencia(1L);
        verify(pessoaRepository).save(any(Pessoa.class));
        assertEquals(TestDataFactory.NOME_ATUALIZADO, resultado.getNome());
        assertEquals(1, resultado.getEnderecos().size());
    }

    @Test
    @DisplayName("Deve deletar uma pessoa")
    public void deveDeletarPessoa() {
        when(pessoaValidator.validarExistencia(1L)).thenReturn(pessoa);

        pessoaService.deletarPessoa(1L);

        verify(pessoaValidator).validarExistencia(1L);
        verify(pessoaRepository).deleteById(1L);
    }
}
