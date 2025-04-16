
# 🧾 CRUD de Pessoa e Endereço – Spring Boot

## 📋 Descrição

API RESTful com Java + Spring Boot que realiza operações de cadastro, listagem, atualização e exclusão para pessoas e seus endereços. Inclui validações, cálculo automático de idade e uso de boas práticas como DTOs, mappers e testes de integração.

Foi integrado o Swagger à aplicação para documentar adequadamente a API, conforme as boas práticas.  
Também foi criado o arquivo `PessoaSpecifications`, responsável pela implementação de filtros dinâmicos para buscas por critérios como **nome**, **CPF**, **cidade**, **estado** e **bairro**.  

Além disso, duas novas rotas foram adicionadas:  
- Uma para retornar os aniversariantes do **dia atual**  
- Outra para filtrar os aniversariantes de um **mês específico**

---

## 📌 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Banco de dados PostgreSQL (via Docker)
- Banco de dados H2 (para testes)
- Validação com Jakarta Bean Validation
- Testes de Integração com JUnit e MockMvc
- Docker + Docker Compose

---

## ✅ Funcionalidades

- [x] Criar pessoa com múltiplos endereços
- [x] Listar todas as pessoas e seus endereços
- [x] Buscar pessoa por ID
- [x] Atualizar pessoa e/ou endereços
- [x] Excluir pessoa com seus endereços
- [x] Validar campos obrigatórios e CPF duplicado
- [x] Exibir idade automaticamente calculada no JSON
- [x] Filtros dinâmicos
- [x] Retorno dos aniversariantes dos dias

---

## 🐳 Como Rodar o Projeto

### 1. Clone o Repositório

```bash
git clone https://github.com/DannyCMMarques/Crud-Pessoas.git
cd demo
```

### 2. Abra o Docker Desktop

Certifique-se de que o **Docker Desktop** está em execução.

### 3. Builde e rode o projeto

```bash
docker compose build
docker compose up
```

### 4. Acesse o Swagger

Após subir o projeto, acesse a documentação da API em:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 📁 Estrutura de Diretórios
```bash

src
└── main
    └── java
        └── com
            └── crud
                └── demo
                    ├── controllers
                    │   └── PessoaControllador.java
                    │
                    ├── Exceptions
                    │   ├── handler
                    │   │   └── GlobalExceptionHandler.java
                    │   │   # Trata exceções globais com mensagens padronizadas.
                    │   ├── pessoaException
                    │   │   ├── CpfJaCadastradoException.java
                    │   │   └── PessoaNaoEncontradaException.java
                    │   ├── ApiException.java
                    │   └── RestErrorMessage.java
                    │   # Auxiliam no tratamento e compreensão de erros pela equipe de desenvolvimento.
                    │
                    ├── models
                    │   ├── dto
                    │   │   ├── EnderecoDTO.java
                    │   │   └── PessoaDTO.java
                    │   │   # Utilizei DTOs para separar a entidade da interface da API, mesmo sendo um CRUD simples,
                    │   │   # por ser uma boa prática. Também incluí validações (@NotNull, @Size, etc.) nos DTOs
                    │   │   # para garantir a integridade dos dados antes de chegarem ao serviço.
                    │   │   # Além disso, na PessoaDTO implementei a exibição da idade de forma calculada apenas
                    │   │   # para leitura no JSON, mantendo o campo como somente leitura com @JsonProperty.
                    │   ├── mappers
                    │   │   ├── EnderecoMapper.java
                    │   │   └── PessoaMappers.java
                    │   │
                    │   ├── Endereco.java
                    │   └── Pessoa.java
                    │
                    ├── repositories
                    │   ├── EnderecoRepository.java
                    │   └── PessoaRepository.java
                    │   # Interfaces que extendem JpaRepository, fornecendo métodos prontos para CRUD e consultas customizadas.
                    │
                    ├── services
                    │   ├── contratos
                    │   │   └── PessoaService.java  # Define a interface com as regras de negócio expostas pelos serviços.
                    │   └── PessoaServiceImpl.java
                    │
                    ├── specifications
                    │   └── PessoaSpecifications.java
                    │   # Classe responsável por implementar filtros dinâmicos usando Specification para a entidade Pessoa.
                    │
                    ├── validators
                    │   └── PessoaValidator.java
                    │   # Validações adicionais como CPF duplicado e existência da pessoa.
                    │
                    └── DemoApplication.java

```
---

## 🧪 Testes

- ✅ **Integração no Controller**  
  Testes com `@SpringBootTest` e `MockMvc` validando os endpoints da `PessoaController`, cobrindo requisições reais, respostas esperadas e tratamento de exceções como pessoa não encontrada.

- ✅ **Unitários no Service**  
  Testes com `Mockito` simulando os repositórios para validar a lógica da `PessoaServiceImpl`: criação, atualização, exclusão e exibição.

---
