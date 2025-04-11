# 🧾 CRUD de Pessoa e Endereço – Spring Boot

## 📋 Descrição

API RESTful com Java + Spring Boot que realiza operações de cadastro, listagem, atualização e exclusão para pessoas e seus endereços. Inclui validações, cálculo automático de idade e uso de boas práticas como DTOs, mappers e testes de integração.

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

---
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
---

## 🔁 Endpoints da API

| Método | Endpoint             | Descrição                                 |
|--------|----------------------|-------------------------------------------|
| POST   | `/pessoas`           | Cadastrar nova pessoa com endereços       |
| GET    | `/pessoas`           | Listar todas as pessoas                   |
| GET    | `/pessoas/{id}`      | Buscar pessoa por ID                      |
| PUT    | `/pessoas/{id}`      | Atualizar pessoa e/ou seus endereços      |
| DELETE | `/pessoas/{id}`      | Excluir pessoa e todos os seus endereços  |

### 📤 Exemplo de Requisição POST `/pessoas`

```json
{
  "nome": "Maria Oliveira",
  "cpf": "98765432100",
  "dataNascimento": "1985-11-25",
  "enderecos": [
    {
      "rua": "Rua Amapá",
      "numero": 50,
      "bairro": "Jardim das Acácias",
      "cidade": "Niterói",
      "estado": "RJ",
      "cep": "24030-000"
    },
    {
      "rua": "Travessa São João",
      "numero": 200,
      "bairro": "Centro",
      "cidade": "São Gonçalo",
      "estado": "RJ",
      "cep": "24710-000"
    }
  ]
}
```

### 📥 Exemplo de Resposta GET `/pessoas`

```json
[
  {
    "id": 6,
    "nome": "João da Silva",
    "cpf": "12345678900",
    "dataNascimento": "1990-05-10",
    "enderecos": [
      {
        "id": 5,
        "rua": "Rua das Flores",
        "numero": 123,
        "bairro": "Centro",
        "cidade": "Itaperuna",
        "estado": "RJ",
        "cep": "28300-000"
      },
      {
        "id": 6,
        "rua": "Avenida Brasil",
        "numero": 456,
        "bairro": "Boa Vista",
        "cidade": "Campos",
        "estado": "RJ",
        "cep": "28000-000"
      }
    ],
    "idade": 34
  },
  {
    "id": 7,
    "nome": "Maria Oliveira",
    "cpf": "98765432100",
    "dataNascimento": "1985-11-25",
    "enderecos": [
      {
        "id": 9,
        "rua": "Rua Amapá",
        "numero": 50,
        "bairro": "Jardim das Acácias",
        "cidade": "Niterói",
        "estado": "RJ",
        "cep": "24030-000"
      },
      {
        "id": 10,
        "rua": "Travessa São João",
        "numero": 200,
        "bairro": "Centro",
        "cidade": "São Gonçalo",
        "estado": "RJ",
        "cep": "24710-000"
      }
    ],
    "idade": 39
  }
]
```

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
                    │   │  # Utilizei DTOs para separar a entidade da interface da API, mesmo sendo um CRUD simples,
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
                    │   │   └── PessoaService.java
                    │   └── PessoaServiceImpl.java
                            # Define a interface com as regras de negócio expostas pelos serviços.
                    │
                    ├── validators
                    │   └── PessoaValidator.java
                    │   # Validações adicionais como CPF duplicado e existência da pessoa.
                    │
                    └── DemoApplication.java
```

---



