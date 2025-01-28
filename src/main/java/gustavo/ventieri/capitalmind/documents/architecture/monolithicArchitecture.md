# Arquitetura do Projeto

Este documento descreve a arquitetura do projeto, que segue o modelo **monolítico**. A arquitetura é organizada em pacotes que agrupam funcionalidades relacionadas, garantindo uma estrutura coesa e fácil de gerenciar.

## Camadas da Arquitetura

A arquitetura é dividida em pacotes principais que representam as camadas e responsabilidades da aplicação:

### 1. Controller

A camada **Controller** é responsável por receber as requisições externas e direcioná-las para os serviços apropriados. Ela atua como uma interface entre o mundo externo e a lógica de negócios.

**Exemplos de classes**:
- `UserController.java`
- `ExpenseController.java`

### 2. Documents

A camada **Documents** contém as representações de documentos ou objetos que são usados para a transferência de dados entre as camadas do sistema. Esses objetos podem ser usados para transportar dados entre a camada de **Controller** e a camada de **Services**.

**Exemplos de classes**:
- `UserDocument.java`
- `ExpenseDocument.java`

### 3. DTO (Data Transfer Object)

A camada **DTO** é responsável por representar os dados que serão transferidos entre as camadas e, geralmente, é usada para otimizar as transferências de dados, evitando a exposição de entidades complexas. Os DTOs podem ser usados para simplificar e organizar as informações que são passadas entre o **Controller** e os **Services**.

**Exemplos de classes**:
- `UserDTO.java`
- `ExpenseDTO.java`

### 4. Entity

A camada **Entity** contém as entidades de negócio, que representam os objetos principais da aplicação. Elas são usadas para mapear os dados do sistema de forma estruturada e são frequentemente associadas a tabelas no banco de dados.

**Exemplos de classes**:
- `User.java`
- `Expense.java`

### 5. Infra

A camada **Infra** é responsável pela implementação dos detalhes técnicos e infraestrutura do sistema, como configurações de banco de dados, autenticação, entre outros. Ela garante que os serviços funcionem corretamente com os recursos externos.

**Exemplos de classes**:
- `DatabaseConfig.java`
- `SecurityConfig.java`

### 6. Repository

A camada **Repository** é responsável pela persistência de dados. Ela interage diretamente com o banco de dados e outros mecanismos de armazenamento, realizando operações de leitura e escrita.

**Exemplos de classes**:
- `UserRepository.java`
- `ExpenseRepository.java`

### 7. Services

A camada **Services** contém a lógica de negócios do sistema. Ela implementa os processos que gerenciam os dados, realizando cálculos, validações e interações com os repositórios para persistir ou recuperar informações.

**Exemplos de classes**:
- `UserService.java`
- `ExpenseService.java`

## Resumo

- **Controller**: Recebe as requisições externas e as direciona para a camada de serviços.
  - **Exemplo**: `UserController.java`, `ExpenseController.java`.
- **Documents**: Representações de dados para transporte entre as camadas.
  - **Exemplo**: `UserDocument.java`, `ExpenseDocument.java`.
- **DTO**: Objetos usados para transferir dados entre camadas, simplificando a manipulação de informações.
  - **Exemplo**: `UserDTO.java`, `ExpenseDTO.java`.
- **Entity**: Entidades de negócio que representam os objetos principais da aplicação e mapeiam as tabelas do banco de dados.
  - **Exemplo**: `User.java`, `Expense.java`.
- **Infra**: Configurações e implementações de infraestrutura que garantem o funcionamento dos recursos externos.
  - **Exemplo**: `DatabaseConfig.java`, `SecurityConfig.java`.
- **Repository**: Interage com o banco de dados ou outros sistemas para realizar operações de leitura e escrita.
  - **Exemplo**: `UserRepository.java`, `ExpenseRepository.java`.
- **Services**: Implementa a lógica de negócios, realizando interações com os repositórios e controladores.
  - **Exemplo**: `UserService.java`, `ExpenseService.java`.

A arquitetura monolítica é mais simples e direta, com todas as partes do sistema interagindo diretamente dentro de um único projeto. A modularização dentro do projeto é feita por meio de pacotes e camadas, facilitando a manutenção e organização do código.
