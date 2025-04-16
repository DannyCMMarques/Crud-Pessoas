
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

src
└── main
    └── java
        └── com
            └── crud
                └── demo
                    ├── controllers
                    │   └── PessoaControllador.java
                    ├── Exceptions
                    │   ├── handler
                    │   │   └── GlobalExceptionHandler.java
                    │   ├── pessoaException
                    │   │   ├── CpfJaCadastradoException.java
                    │   │   └── PessoaNaoEncontradaException.java
                    │   ├── ApiException.java
                    │   └── RestErrorMessage.java
                    ├── models
                    │   ├── dto
                    │   │   ├── EnderecoDTO.java
                    │   │   └── PessoaDTO.java
                    │   ├── mappers
                    │   │   ├── EnderecoMapper.java
                    │   │   └── PessoaMappers.java
                    │   ├── Endereco.java
                    │   └── Pessoa.java
                    ├── repositories
                    │   ├── EnderecoRepository.java
                    │   └── PessoaRepository.java
                    ├── services
                    │   ├── contratos
                    │   │   └── PessoaService.java
                    │   └── PessoaServiceImpl.java
                    ├── specifications
                    │   └── PessoaSpecifications.java
                    ├── validators
                    │   └── PessoaValidator.java
                    └── DemoApplication.java

---

## 🧪 Testes

- ✅ **Integração no Controller**  
  Testes com `@SpringBootTest` e `MockMvc` validando os endpoints da `PessoaController`, cobrindo requisições reais, respostas esperadas e tratamento de exceções como pessoa não encontrada.

- ✅ **Unitários no Service**  
  Testes com `Mockito` simulando os repositórios para validar a lógica da `PessoaServiceImpl`: criação, atualização, exclusão e exibição.

---
