# micro-cliente-clean-arch

Microsserviço de gerenciamento de clientes utilizando arquitetura limpa. Este projeto faz parte do Tech Challenge 04 da pós-graduação de `Arquitetura e Desenvolvimento Java` na instituição FIAP.

---

## Funções

* **Cadastrar clientes**: Permite a criação de novos registros de clientes.
* **Listar clientes**:
    * Obtém uma lista paginada de todos os clientes.
    * Permite a busca de um cliente específico pelo ID.
* **Atualizar clientes**: Permite a modificação de dados de um cliente existente.
* **Apagar clientes**: Permite a exclusão de um registro de cliente.

---

## Tecnologias

* **Spring Boot**: Framework web utilizado para o desenvolvimento da aplicação.
* **Maven**: Ferramenta para automação de builds e gerenciamento de dependências.
* **Swagger/OpenAPI**: Utilizado para documentação e interface interativa para testes da API.
* **MongoDB**: Banco de dados NoSQL utilizado para persistência dos dados dos clientes.
* **Docker/Docker Compose**: Utilizado para orquestração e execução dos contêineres da aplicação e do banco de dados.
* **Lombok**: Biblioteca para reduzir código boilerplate em Java.
* **Project Reactor**: Para programação reativa com Spring Boot (mencionado nas dependências, mas o uso reativo pode ser mais explícito em outras partes do código).

---

## Instalação

Para executar o microsserviço localmente utilizando Docker Compose, siga os passos abaixo:

1.  **Instalar Docker Desktop**: Certifique-se de ter o Docker Desktop instalado em sua máquina.
2.  **Clone o repositório**: Baixe os arquivos do repositório para sua máquina local.
    ```bash
    git clone [https://github.com/joao-v170r/micro-cliente-clean-arch.git](https://github.com/joao-v170r/micro-cliente-clean-arch.git)
    ```
3.  **Acesse a pasta do repositório**: Navegue até o diretório raiz do projeto.
    ```bash
    cd micro-cliente-clean-arch
    ```
4.  **Execute com Docker Compose**: Utilize o comando abaixo para construir as imagens e iniciar os contêineres.
    ```bash
    docker-compose up --build
    ```
    Este comando irá levantar o microsserviço Spring Boot (`cliente-app`) e o servidor MongoDB (`mongo-cliente`), com um volume persistente para os dados do MongoDB e uma rede dedicada (`cliente-network`).
5.  **Acesse o Swagger UI**: Após a inicialização dos contêineres, a documentação interativa da API estará disponível em `http://localhost:8080/swagger-ui/index.html`.

---

## Banco de Dados

O microsserviço utiliza o MongoDB como banco de dados NoSQL. A entidade principal é o `Cliente`.

### Entidade Cliente

| Campo        | Descrição                                                                               |
| :----------- | :-------------------------------------------------------------------------------------- |
| `id`         | Identificador único do cliente (gerado pelo MongoDB)                         |
| `nome`       | Nome completo do cliente                                                     |
| `cpf`        | Cadastro de Pessoa Física (CPF), valor único para cada cliente               |
| `email`      | Endereço de e-mail do cliente                                                |
| `dataNascimento` | Data de nascimento do cliente                                              |
| `enderecos`  | Conjunto de endereços associados ao cliente (CEP, endereço completo, latitude, longitude) |
| `telefones`  | Conjunto de telefones associados ao cliente (número, DDD)                |

Um script de inicialização (`mongo-init/init.js`) cria um usuário `cliente_micro` com permissões de `readWrite` e `dbAdmin` para o banco de dados `db_cliente`.

---

## API

### Cliente

| Método | URL                          | Ação                                      | Exemplo de Requisição (Body)                                                                                                                                                                                                                                                                             |
| :----- | :--------------------------- | :---------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `POST` | `/create-cliente`            | Cria um novo cliente.           | ```json { "nome": "João da Silva", "cpf": "123.456.789-09", "email": "joao.silva@example.com", "dataNascimento": "1990-05-15", "cep": "01001-000", "enderecoCompleto": "Rua das Flores, 123, Centro, São Paulo/SP", "latitude": -2356834, "longitude": -4657744, "telefone": "987654321", "ddd": "11" } ``` |
| `GET`  | `/cliente/{id}`              | Obtém um cliente pelo seu ID.   | `GET /cliente/60c72b2f9f1d4f001f3e0c0a`                                                                                                                                                                                                                                                                 |
| `GET`  | `/cliente`                   | Obtém todos os clientes (paginado). | ```json { "page": 0, "size": 10 } ```                                                                                                                                                                                                                                                                   |
| `PUT`  | `/update-cliente/{id}`       | Atualiza os dados de um cliente pelo ID. | ```json { "nome": "João Silva Atualizado", "email": "joao.atualizado@example.com", "dataNascimento": "1995-01-01", "enderecos": [ { "cep": "01001-001", "enderecoCompleto": "Nova Rua, 456, Bairro Novo, São Paulo/SP", "latitude": -2356830, "longitude": -46665590 } ], "telefones": [ { "numero": "999999999", "ddd": "11" } ] } ``` |
| `DELETE` | `/delete-cliente/{id}`       | Apaga um cliente pelo seu ID.   | `DELETE /delete-cliente/60c72b2f9f1d4f001f3e0c0a`                                                                                                                                                                                                                                                             |

---

## Qualidade de Software

### Testes

O projeto possui testes automatizados para as camadas de `controller`, `domain` e `gateway`, buscando garantir a qualidade e a cobertura do código. A cobertura de testes busca ser superior a 80%.