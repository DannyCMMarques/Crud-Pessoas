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
import org.mockito.junit.jupiter.MockitoExtension;

import com.crud.demo.models.Endereco;
import com.crud.demo.models.Pessoa;
import com.crud.demo.models.DTO.PessoaDTO;
import com.crud.demo.repositories.EnderecoRepository;
import com.crud.demo.repositories.PessoaRepository;

@ExtendWith(MockitoExtension.class)
public class PessoasServiceTest {

    @InjectMocks
private PessoaService pessoaService;
    @Mock
private PessoaRepository pessoaRepository;
    @Mock
private EnderecoRepository enderecoRepository;

private Endereco endereco1;
private Endereco endereco2;

private Pessoa pessoa;

@BeforeEach
public void setUp() {
    endereco1 = Endereco.builder()
    .rua("Rua 1")
    .numero(123)
    .bairro("Bairro 1")
    .cidade("Cidade 1")
    .estado("Estado 1")
    .CEP("12345-678")
    .build();

    endereco2 = Endereco.builder()
    .rua("Rua 2")
    .numero(456)
    .bairro("Bairro 2")
    .cidade("Cidade 2")
    .estado("Estado 2")
    .CEP("98765-432")
    .build();

pessoa = Pessoa.builder()
    .nome("Nome 1")
    .CPF("123.456.789-00")
    .dataNascimento(LocalDate.of(2000, 1, 1))
    .enderecos(List.of(endereco1, endereco2))
    .build();

}
@DisplayName("Deve salvar uma pessoa com dois endereços corretamente")
@Test
public void deveCriarPessoaCorretamente(){
  when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);
    Pessoa pessoaCriada = pessoaService.criarPessoa(pessoa);

        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
        verify(enderecoRepository, times(2)).save(any(Endereco.class));
        assertEquals("123.456.789-00", pessoaCriada.getCPF());
        assertEquals(2, pessoaCriada.getEnderecos().size());
        assertEquals(pessoa, pessoaCriada);

}

@DisplayName("Deve buscar uma pessoa pelo ID")
@Test
public void deveBuscarPessoaPorID() {
    when(pessoaRepository.findById(1L)).thenReturn(java.util.Optional.of(pessoa));

    PessoaDTO  pessoaEncontrada = pessoaService.buscarPessoaPorId(1L);

    verify(pessoaRepository, times(1)).findById(1L);
    assertEquals("Nome 1", pessoaEncontrada.getNome());
    assertEquals("123.456.789-00", pessoaEncontrada.getCPF());
    assertIterableEquals(List.of(endereco1, endereco2),pessoaEncontrada.getEnderecos());
    assertEquals(2, pessoaEncontrada.getEnderecos().size());

}

@DisplayName("Deve buscar todas as pessoas")
@Test
public void deveRetornarTodasPessoas() {
    when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

    List<PessoaDTO> pessoasEncontradas = pessoaService.buscarTodasPessoas();

    verify(pessoaRepository, times(1)).findAll();
    assertEquals(1, pessoasEncontradas.size());
    assertEquals("Nome 1", pessoasEncontradas.get(0).getNome());
    assertEquals("123.456.789-00", pessoasEncontradas.get(0).getCPF());
    assertIterableEquals(List.of(endereco1, endereco2),pessoasEncontradas.get(0).getEnderecos())

}

@DisplayName("Deve atualizar uma pessoa")
@Test
public void deveAtualizarPessoa() {
    Pessoa pessoaAtualizada = Pessoa.builder()
            .id(1L)
            .nome("Nome Atualizado")
            .CPF("123.456.789-00")
            .dataNascimento(LocalDate.of(2000, 1, 1))
            .enderecos(List.of(endereco1))
            .build();

    when(pessoaRepository.findById(1L)).thenReturn(java.util.Optional.of(pessoa));
    when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaAtualizada);

    Pessoa pessoaRetornada = pessoaService.atualizarPessoa(pessoaAtualizada);

    verify(pessoaRepository, times(1)).findById(1L);
    verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    assertEquals("Nome Atualizado", pessoaRetornada.getNome());
    assertEquals(1, pessoaRetornada.getEnderecos().size());

}
@DisplayName("Deve deletar uma pessoa")
@Test
public void deveDeletarPessoa() {
    when(pessoaRepository.findById(1L)).thenReturn(java.util.Optional.of(pessoa));

    pessoaService.deletarPessoa(1L);

    verify(pessoaRepository, times(1)).findById(1L);
    verify(pessoaRepository, times(1)).deleteById(1L);
}
}
