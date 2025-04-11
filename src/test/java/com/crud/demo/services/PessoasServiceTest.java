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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crud.demo.models.Pessoa;
import com.crud.demo.models.DTO.PessoaDTO;
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

    @Spy
    private PessoaMappers pessoaMappers;

    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;

    @BeforeEach
    public void setUp() {
        pessoa = TestDataFactory.criarPessoaEntity();
        pessoaDTO = pessoaMappers.toDto(pessoa);
    }

    @Test
    @DisplayName("Deve salvar uma pessoa com dois endereços corretamente")
    public void deveCriarPessoaCorretamente() {
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

        PessoaDTO resultado = pessoaService.buscarPessoaPorId(1L);

        verify(pessoaValidator).validarExistencia(1L);
        assertEquals(pessoa.getNome(), resultado.getNome());
        assertEquals(pessoa.getCpf(), resultado.getCpf());
        assertEquals(2, resultado.getEnderecos().size());
    }

    @Test
    @DisplayName("Deve buscar todas as pessoas")
    public void deveRetornarTodasPessoas() {
        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

        List<PessoaDTO> resultado = pessoaService.buscarTodasPessoas();

        verify(pessoaRepository).findAll();
        assertEquals(1, resultado.size());
        assertEquals(pessoa.getNome(), resultado.get(0).getNome());
        assertEquals(pessoa.getCpf(), resultado.get(0).getCpf());
        assertEquals(2, resultado.get(0).getEnderecos().size());
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

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaAtualizada);

        PessoaDTO pessoaDTOAtualizada = pessoaMappers.toDto(pessoaAtualizada);
        pessoaDTOAtualizada.setId(1L);

        PessoaDTO resultado = pessoaService.atualizarPessoa(1L, pessoaDTOAtualizada);

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
