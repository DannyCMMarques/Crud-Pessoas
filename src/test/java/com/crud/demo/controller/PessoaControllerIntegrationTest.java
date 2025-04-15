package com.crud.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crud.demo.models.DTO.PessoaDTO;
import com.crud.demo.utils.TestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PessoaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PessoaDTO pessoaDTO;

    @BeforeEach
    public void setup() {
        pessoaDTO = TestDataFactory.criarPessoaDTOValida();
    }

    @Test
    @DisplayName("Deve criar corretamente uma pessoa, seu endereço e retornar o status correto")
    void deveCriarPessoa() throws Exception {
        String json = objectMapper.writeValueAsString(pessoaDTO);

        mockMvc.perform(post(TestDataFactory.URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(TestDataFactory.NOME_PADRAO))
                .andExpect(jsonPath("$.cpf").value(TestDataFactory.CPF_PURO))
                .andExpect(jsonPath("$.enderecos.length()").value(2));
    }

    @Test
    @DisplayName("Deve buscar uma pessoa pelo ID após criação")
    void deveBuscarPessoaPorId() throws Exception {
        String json = objectMapper.writeValueAsString(pessoaDTO);

        MvcResult result = mockMvc.perform(post(TestDataFactory.URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        PessoaDTO pessoaCriada = objectMapper.readValue(result.getResponse().getContentAsString(), PessoaDTO.class);

        mockMvc.perform(get(TestDataFactory.URL_BASE + "/" + pessoaCriada.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(TestDataFactory.NOME_PADRAO))
                .andExpect(jsonPath("$.cpf").value(TestDataFactory.CPF_PURO));
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa corretamente")
    void deveAtualizarPessoa() throws Exception {
        String json = objectMapper.writeValueAsString(pessoaDTO);

        MvcResult result = mockMvc.perform(post(TestDataFactory.URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        PessoaDTO pessoaCriada = objectMapper.readValue(result.getResponse().getContentAsString(), PessoaDTO.class);

        pessoaCriada.setNome(TestDataFactory.NOME_ATUALIZADO);

        String jsonAtualizado = objectMapper.writeValueAsString(pessoaCriada);

        mockMvc.perform(put(TestDataFactory.URL_BASE + "/" + pessoaCriada.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAtualizado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(TestDataFactory.NOME_ATUALIZADO));
    }

    @Test
    @DisplayName("Deve deletar uma pessoa corretamente")
    void deveDeletarPessoa() throws Exception {
        String json = objectMapper.writeValueAsString(pessoaDTO);

        MvcResult result = mockMvc.perform(post(TestDataFactory.URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        PessoaDTO pessoaCriada = objectMapper.readValue(result.getResponse().getContentAsString(), PessoaDTO.class);

        mockMvc.perform(delete(TestDataFactory.URL_BASE + "/" + pessoaCriada.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(TestDataFactory.URL_BASE + pessoaCriada.getId()))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Deve listar todas as pessoas")
    void deveListarTodasPessoas() throws Exception {
        String json = objectMapper.writeValueAsString(pessoaDTO);

        mockMvc.perform(post(TestDataFactory.URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get(TestDataFactory.URL_BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
