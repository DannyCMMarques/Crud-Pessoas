package com.crud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crud.demo.models.Endereco;
import com.crud.demo.models.Pessoa;
import com.crud.demo.models.DTO.PessoaDTO;
import com.crud.demo.models.mappers.PessoaMappers;
import com.crud.demo.repositories.EnderecoRepository;
import com.crud.demo.repositories.PessoaRepository;
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

    private Endereco endereco1;
    private Endereco endereco2;
    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;

    @BeforeEach
    public void setUp() {
        endereco1 = Endereco.builder()
                .rua("Rua 1").numero(123).bairro("Bairro 1").cidade("Cidade 1").estado("Estado 1").CEP("12345-678")
                .build();

        endereco2 = Endereco.builder()
                .rua("Rua 2").numero(456).bairro("Bairro 2").cidade("Cidade 2").estado("Estado 2").CEP("98765-432")
                .build();

        pessoa = Pessoa.builder()
                .id(1L)
                .nome("Nome 1")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .enderecos(List.of(endereco1, endereco2))
                .build();

        pessoaDTO = pessoaMappers.toDto(pessoa);
    }

    @Test
    @DisplayName("Deve salvar uma pessoa com dois endereços corretamente")
    public void deveCriarPessoaCorretamente() {
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);
        doNothing().when(pessoaValidator).validarCadastro(anyString());

        PessoaDTO resultado = pessoaService.criarPessoa(pessoaDTO);

        verify(pessoaValidator).validarCadastro("123.456.789-00");
        verify(pessoaRepository).save(any(Pessoa.class));
        assertEquals("123.456.789-00", resultado.getCpf());
        assertEquals(2, resultado.getEnderecos().size());
    }

    @Test
    @DisplayName("Deve buscar uma pessoa pelo ID")
    public void deveBuscarPessoaPorID() {
        when(pessoaValidator.validarExistencia(1L)).thenReturn(pessoa);

        PessoaDTO resultado = pessoaService.buscarPessoaPorId(1L);

        verify(pessoaValidator).validarExistencia(1L);
        assertEquals("Nome 1", resultado.getNome());
        assertEquals("123.456.789-00", resultado.getCpf());
        assertEquals(2, resultado.getEnderecos().size());
    }

    @Test
    @DisplayName("Deve buscar todas as pessoas")
    public void deveRetornarTodasPessoas() {
        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

        List<PessoaDTO> resultado = pessoaService.buscarTodasPessoas();

        verify(pessoaRepository).findAll();
        assertEquals(1, resultado.size());
        assertEquals("Nome 1", resultado.get(0).getNome());
        assertEquals("123.456.789-00", resultado.get(0).getCpf());
        assertEquals(2, resultado.get(0).getEnderecos().size());
        assertEquals("Rua 1", resultado.get(0).getEnderecos().get(0).getRua());
    }

    @Test
@DisplayName("Deve atualizar uma pessoa")
public void deveAtualizarPessoa() {
    Pessoa pessoaAtualizada = Pessoa.builder()
            .id(1L)
            .nome("Nome Atualizado")
            .cpf("123.456.789-00")
            .dataNascimento(LocalDate.of(2000, 1, 1))
            .enderecos(List.of(endereco1))
            .build();

    when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaAtualizada);

    PessoaDTO pessoaDTOAtualizada = pessoaMappers.toDto(pessoaAtualizada);
    pessoaDTOAtualizada.setId(1L);

    PessoaDTO resultado = pessoaService.atualizarPessoa(1L, pessoaDTOAtualizada);

    verify(pessoaRepository).save(any(Pessoa.class));

    assertEquals("Nome Atualizado", resultado.getNome());
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