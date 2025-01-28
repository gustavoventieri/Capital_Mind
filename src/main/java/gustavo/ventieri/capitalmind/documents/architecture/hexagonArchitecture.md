# Arquitetura do Projeto

Este documento descreve a arquitetura do projeto, a qual segue o modelo **Hexagonal** (ou **Ports and Adapters**). A arquitetura é estruturada em camadas bem definidas com responsabilidades claras, garantindo modularidade, flexibilidade e facilidade de manutenção.

## Camadas da Arquitetura

A arquitetura é dividida em três camadas principais:

### 1. Camada de Domínio

A camada de domínio é o **núcleo** do sistema, onde estão as **entidades** e as **regras de negócio**. Ela é independente de qualquer tecnologia externa, garantindo que as regras de negócio permaneçam isoladas e reutilizáveis.

**Exemplos de classes**:
- `User.java`
- `Expense.java`

### 2. Camada de Aplicação

A camada de aplicação contém os **serviços de negócios** que implementam a lógica de como os dados devem ser processados. Ela também interage com o mundo externo por meio de **controllers** e **interfaces**.

**Exemplos de classes**:
- `UserService.java`
- `ExpenseService.java`

### 3. Camada de Infraestrutura

A camada de infraestrutura é responsável por implementar os detalhes técnicos, como a interação com o banco de dados ou APIs externas. Aqui estão os **repositórios** que fazem a persistência de dados e integração com sistemas externos.

**Exemplos de classes**:
- `UserRepository.java`
- `ExpenseRepository.java`

## Arquitetura Hexagonal (Ports and Adapters)

A **Arquitetura Hexagonal** separa o núcleo de negócios (domínio) dos detalhes externos (tecnologias, APIs, etc.). O objetivo é tornar o sistema mais modular, testável e fácil de manter.

A arquitetura hexagonal é composta por duas partes principais: **ports** e **adapters**.

### 1. Domínio (Core)

O domínio é o centro do sistema, onde ficam as **entidades** e as **regras de negócio**. Em nossa arquitetura, isso corresponde à camada `domain`, que contém as classes que representam as entidades principais, como `User` e `Expense`.

### 2. Ports (Portas)

As **ports** são interfaces que definem como o sistema interage com o mundo exterior. Elas são divididas em duas categorias:

- **Ports de Entrada**: São responsáveis por como o sistema **recebe dados** externos. Eles estão representados pelos **controllers** em `application/controller`, que recebem as requisições (ex.: HTTP) e as encaminham para a camada de serviços.

  **Exemplo**: Controllers em `application/controller` (como `UserController.java`, `ExpenseController.java`).

- **Ports de Saída**: São responsáveis por como o sistema **envia dados** para o exterior. Em nossa arquitetura, esses ports são representados pelas **interfaces de repositórios** em `domain`, que permitem que o sistema interaja com o banco de dados e outros sistemas externos.

  **Exemplo**: Interfaces de repositórios em `domain` (como `UserRepository.java`, `ExpenseRepository.java`).

### 3. Adapters (Adaptadores)

Os **adapters** são as implementações dos **ports**. Eles conectam o sistema com o mundo externo, fazendo a conversão dos dados e interagindo com as tecnologias externas.

- **Adaptadores de Entrada**: São os **controllers**, que recebem os dados de entrada, como requisições HTTP, e os transformam em objetos que o domínio pode processar. Eles estão localizados em `application/controller`.

  **Exemplo**: Controllers em `application/controller`.

- **Adaptadores de Saída**: São os **repositórios**, que implementam as interfaces dos repositórios e interagem diretamente com o banco de dados ou sistemas externos.

  **Exemplo**: Repositórios em `infrastructure/persistence`.

## Resumo

- **Domínio (Core)**: O núcleo da aplicação, onde estão as entidades e as regras de negócio.
  - **Exemplo**: `User.java`, `Expense.java`.
- **Ports**:
  - **Entrada**: Recebe dados do exterior (ex.: controllers em `application/resources`).
  - **Saída**: Envia dados para o exterior (ex.: interfaces de repositórios em `application/repository`).
- **Adapters**:
  - **Entrada**: Controllers que transformam dados externos em algo que o domínio pode processar.
  - **Saída**: Repositórios que interagem com o banco de dados ou sistemas externos.
  
A arquitetura hexagonal permite que o sistema seja modular, desacoplado e fácil de testar. As alterações nos detalhes externos (como banco de dados ou APIs) não afetam as regras de negócio no núcleo do sistema.
