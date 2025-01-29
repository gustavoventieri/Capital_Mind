# **Arquitetura do Projeto CapitalMind**

Este documento descreve a arquitetura do projeto **CapitalMind**, que segue o modelo **Hexagonal**. A arquitetura é organizada em pacotes que agrupam funcionalidades relacionadas, garantindo uma estrutura coesa, flexível e fácil de gerenciar.

## **Camadas da Arquitetura**

A arquitetura é dividida em pacotes principais que representam as camadas e responsabilidades da aplicação:

### **1. Application**

A camada **Application** é responsável por coordenar as interações entre as interfaces externas e a lógica de negócios. Ela é composta por controladores, serviços e interfaces de repositório que fazem a ponte entre o mundo externo e o domínio da aplicação.

**Exemplo de pastas**:
- `application/controller`
- `application/dto`
- `application/repository`
- `application/service`

### **2. Domain**

A camada **Domain** contém as entidades de negócio e a lógica do sistema. Ela é independente de qualquer tecnologia ou framework específico, garantindo que o sistema seja agnóstico às implementações. Cada entidade de domínio reflete um conceito de negócio, como um usuário ou uma despesa.

**Exemplo de pastas**:
- `domain/user`
- `domain/stock`
- `domain/crypto`
- `domain/expense`
- `domain/investment`

### **3. Infrastructure**

A camada **Infrastructure** fornece as implementações técnicas necessárias para a integração do sistema com serviços externos, como banco de dados, APIs e outros recursos. Ela contém os adaptadores que conectam as portas definidas na camada **Application** às implementações reais dos serviços externos.

**Exemplo de pastas**:
- `infrastructure/clients`
- `infrastructure/config`
- `infrastructure/persistence`
- `infrastructure/service`

### **4. DTO (Data Transfer Object)**

A camada **DTO** é responsável por representar os dados que serão transferidos entre as camadas. Ela é usada para simplificar a comunicação entre o **Controller** e o **Service**, encapsulando os dados necessários de forma otimizada e evitando a exposição das entidades diretamente.

**Exemplo de pastas**:
- `application/dto`

### **5. Exception**

A camada **Exception** é responsável pelo tratamento de exceções e erros do sistema. Ela garante que erros sejam tratados de maneira consistente, fornecendo mensagens de erro claras e garantindo a integridade da aplicação.

**Exemplo de pastas**:
- `infrastructure/exception`

---

## **Resumo da Arquitetura**

- **Application**: Camada que orquestra as interações entre as interfaces externas e o domínio da aplicação.
  - **Exemplo de pastas**: `application/controller`, `application/service`, `application/repository`
- **Domain**: Contém as entidades e lógica de negócios da aplicação.
  - **Exemplo de pastas**: `domain/user`, `domain/expense`, `domain/investment`
- **Infrastructure**: Implementação de componentes que conectam a aplicação a sistemas externos (banco de dados, APIs, etc.).
  - **Exemplo de pastas**: `infrastructure/persistence`, `infrastructure/clients`, `infrastructure/service`
- **DTO**: Objetos usados para transferir dados de forma otimizada entre camadas.
  - **Exemplo de pastas**: `application/dto`
- **Exception**: Tratamento de exceções e erros do sistema.
  - **Exemplo de pastas**: `infrastructure/exception`

## **Considerações Finais**

A arquitetura do **CapitalMind** é baseada no modelo **Hexagonal**, também conhecido como **Arquitetura de Portos e Adaptadores**. Esse modelo permite um alto grau de desacoplamento entre a lógica de negócios e os detalhes de implementação, proporcionando flexibilidade e facilitando a manutenção e evolução do sistema. A modularização em camadas bem definidas garante que as diferentes responsabilidades do sistema sejam claramente separadas, promovendo uma organização eficiente e escalável.