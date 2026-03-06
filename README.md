# Gerenciador de Clientes

Uma API REST desenvolvida para o gerenciamento de clientes. Este projeto implementa as operações de um CRUD aplicando boas práticas de engenharia de software, como arquitetura em camadas.

---

## 🚀 Funcionalidades

- Cadastro de novos clientes com validação de dados (Nome, E-mail e Idade mínima).
- Busca de clientes por E-mail (único por usuário).
- Listagem de todos os clientes cadastrados.
- Remoção de clientes por ID de forma segura.

---

## 🛠️ Tecnologias e Ferramentas

O projeto foi construído utilizando o seguinte ecossistema:

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Web** (Construção da API REST)
- **Spring Data JPA** (Persistência e comunicação com o banco de dados)
- **Spring Boot Validation** (Validação de DTOs)
- **Lombok** (Redução de código boilerplate/repetitivo)
- **Maven** (Gerenciamento de dependências)

---

## 🏗️ Arquitetura e Fluxo

O projeto segue a clássica **Arquitetura em Camadas** do Spring, garantindo a separação de responsabilidades:

1. **Cliente (Postman/Insomnia/Front-end):** Envia requisições HTTP para a API.
2. **Controller (`CustomerController`):** Recebe a requisição, valida os dados de entrada (DTOs) e repassa para o Service.
3. **Service (`CustomerService`):** Contém as regras de negócio, converte DTOs em Entidades e solicita operações ao banco.
4. **Repository (`ICustomerRepository`):** Interface do Spring Data JPA que executa as queries (SQL) no banco de dados de forma automatizada.
5. **Database:** Armazena as informações das entidades (`Customer`).

---

## 🌐 Endpoints da API

Abaixo estão as rotas disponíveis nesta API:

| Método | Endpoint | Descrição | Status HTTP de Sucesso |
| --- | --- | --- | --- |
| **POST** | `/customers` | Cria um novo cliente | `201 Created` |
| **GET** | `/customers` | Retorna a lista de todos os clientes | `200 OK` ou `204 No Content` |
| **GET** | `/customers/email/{email}` | Retorna um cliente específico pelo e-mail | `200 OK` |
| **DELETE** | `/customers/{id}` | Deleta um cliente existente pelo ID | `204 No Content` |

> **Nota:** O endpoint de criação espera um payload em JSON no seguinte formato:
>
>
> ```json
> {
>   "name": "João da Silva",
>   "email": "joao@email.com",
>   "age": 25
> }
> ```
>

---

## ⚙️ Como executar o projeto localmente

Siga os passos abaixo para baixar e rodar a aplicação na sua máquina.

### Pré-requisitos

- **Git** instalado.
- **Java Development Kit (JDK) 17** ou superior.
- **Maven** instalado (opcional, o wrapper do Maven geralmente vem junto com o projeto).

### Passos via CLI (Command Line Interface)

1. **Clone o repositório:**
```
   git clone https://github.com/Ivictors/gerenciador-de-clientes.git