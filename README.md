![banner](https://github.com/MassolaGabriel/sistema_gestao_industria/blob/main/img/banner.png)
# **Sistema de Gestão Industrial**

## **1. Visão Geral** 🗺️
O Sistema de Gestão Industrial é uma aplicação de consola desenvolvida em Java, projetada para gerir as operações fundamentais de uma pequena indústria. O sistema utiliza uma arquitetura em camadas e conecta-se a um banco de dados MySQL para persistir os dados, permitindo a gestão de setores, funcionários, produtos e registos de produção de forma integrada.

---

## **2. Arquitetura do Projeto** 🏗️
O projeto segue uma arquitetura bem definida, separando as responsabilidades em diferentes pacotes para facilitar a manutenção e a escalabilidade.

> **Separação de Camadas:** A divisão do projeto em pacotes como `config`, `model`, `dao` e `menu` é um ponto crucial. Isso organiza o código, tornando-o mais limpo, reutilizável e fácil de manter.

* **`AppIndustria.java`**: Ponto de entrada da aplicação. Contém o menu principal que direciona o utilizador para os diferentes módulos do sistema.
* **`config`**: Responsável pela configuração da ligação com o banco de dados.
    * `ConexaoMySQL.java`: Fornece um método estático para obter uma ligação ativa com o banco de dados MySQL.
* **`model`**: Contém as classes de entidade (POJOs) que espelham as tabelas do banco de dados (Ex: `Setor.java`, `Funcionario.java`).
* **`dao` (Data Access Object)**:
    > Camada de acesso aos dados. Isola a lógica de negócio da persistência. Cada classe DAO é responsável pelas operações de CRUD (Create, Read, Update, Delete) de uma entidade.
* **`menu`**: Camada de apresentação (Interface do Utilizador). Contém as classes que exibem os menus na consola, capturam a entrada do utilizador e interagem com a camada DAO.

```
/src
|
|-- AppIndustria.java
|
|-- config/
|   `-- ConexaoMySQL.java
|
|-- dao/
|   |-- SetorDAO.java
|   |-- FuncionarioDAO.java
|   |-- ProdutoDAO.java
|   `-- ProducaoDAO.java
|
|-- menu/
|   |-- SetorMenu.java
|   |-- FuncionarioMenu.java
|   |-- ProdutoMenu.java
|   `-- ProducaoMenu.java
|
`-- model/
    |-- Setor.java
    |-- Funcionario.java
    |-- Produto.java
    `-- Producao.java
```

---

## **3. Banco de Dados** 🗄️
O sistema utiliza um banco de dados MySQL chamado `industria`. A seguir, o script SQL para criar a estrutura completa, os seus relacionamentos e para popular o banco com dados iniciais.

### **Script SQL (DDL e DML)**
```sql
CREATE DATABASE IF NOT EXISTS industria;
USE industria;

-- Tabela de Setores
CREATE TABLE setor (
    id_setor INT PRIMARY KEY AUTO_INCREMENT,
    nome_setor VARCHAR(40) NOT NULL,
    responsavel TEXT
);

-- Tabela de Funcionários
CREATE TABLE funcionario (
    id_funcionario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    sobrenome VARCHAR(40) NOT NULL,
    id_setor INT NOT NULL,
    FOREIGN KEY(id_setor) REFERENCES setor(id_setor)
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);
```
> **Integridade Referencial:** O uso de `FOREIGN KEY` com `ON DELETE CASCADE` é uma decisão de design importante. Garante que, ao remover um setor, todos os funcionários ligados a ele sejam automaticamente removidos, mantendo a consistência dos dados.

```sql
-- Tabela de Produtos
CREATE TABLE produtos (
    id_produtos INT PRIMARY KEY AUTO_INCREMENT,
    nome_produto VARCHAR(40) NOT NULL,
    descricao TEXT
);

-- Tabela de Produção
CREATE TABLE producao (
    id_producao INT PRIMARY KEY AUTO_INCREMENT,
    id_produtos INT NOT NULL,
    id_funcionario INT NOT NULL,
    data_producao VARCHAR(10) NOT NULL,
    quantidade INT NOT NULL,
    FOREIGN KEY(id_produtos) REFERENCES produtos(id_produtos)
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    FOREIGN KEY(id_funcionario) REFERENCES funcionario(id_funcionario)
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

-- Inserção de dados iniciais
INSERT INTO setor(nome_setor, responsavel)
VALUES("Logística", "Roberto"),
      ("Recursos Humanos", "Fernanda"),
      ("Tecnologia", "Carlos"),
      ("Financeiro", "Juliana"),
      ("Marketing", "Lucas");

INSERT INTO funcionario(nome, sobrenome, id_setor)
VALUES("Lucas", "Dos Santos Crispim", 1),
      ("Ana", "Silva Souza", 2),
      ("Carlos", "Oliveira Santos", 3);

INSERT INTO produtos(nome_produto, descricao)
VALUES("Caderno", "Caderno espiral de 100 folhas, capa dura"),
      ("Caneta", "Caneta esferográfica azul com tinta de alta qualidade"),
      ("Lápis", "Lápis grafite 2B, ideal para escrita e desenhos");

INSERT INTO producao(id_produtos, id_funcionario, data_producao, quantidade)
VALUES(1, 1, '2025-06-01', 100),
      (2, 2, '2025-06-02', 150),
      (3, 3, '2025-06-03', 200);
```

---

## **4. Como Configurar e Executar o Projeto** 🚀
Para compilar e executar o projeto com sucesso, siga os passos abaixo.

### **4.1. Pré-requisitos**
Antes de começar, garanta que tem os seguintes softwares instalados e configurados:

* **JDK 8 ou superior:** Essencial para compilar e executar o código-fonte.
* **Servidor MySQL:** Um banco de dados MySQL ativo para armazenar os dados.
* **IDE Java:** Recomenda-se o uso de um Ambiente de Desenvolvimento Integrado como Eclipse, IntelliJ IDEA ou VS Code.
* **Driver JDBC para MySQL:**
    > O conector (`.jar`) que permite a comunicação entre a aplicação e o banco de dados deve ser adicionado ao *classpath* do projeto. Este é um passo fundamental e uma causa comum de erros de ligação.

### **4.2. Passos para Execução**
1.  **Clone o Repositório**
    Abra um terminal e clone o projeto para a sua máquina local.
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd <NOME_DO_PROJETO>
    ```

2.  **Configure o Banco de Dados**
    Execute o script SQL acima no seu cliente MySQL para criar o banco `industria` e as suas tabelas.

3.  **Ajuste a Ligação**
    Navegue até ao ficheiro `src/config/ConexaoMySQL.java` e verifique se as constantes `HOST`, `USER` e `PASSWORD` correspondem às credenciais do seu banco de dados.
    ```java
    private static final String HOST = "jdbc:mysql://localhost:3306/industria"; // Verifique a porta
    private static final String USER = "root";
    private static final String PASSWORD = "seu_password_aqui";
    ```

4.  **Compile e Execute**
    Importe o projeto na sua IDE, adicione o Driver JDBC e execute o método `main` da classe `AppIndustria.java`.

---

## **5. Funcionalidades e Módulos** ✨
O sistema oferece operações de CRUD completas para as principais entidades da indústria.

### **Gestão de Setores (`SetorMenu`)** 🏢
* `Listar` todos os setores.
* `Buscar` um setor específico pelo seu ID.
* **Cadastrar um novo setor.**
* **Atualizar os dados de um setor existente.**
* `Remover` um setor (e os seus funcionários associados).

### **Gestão de Funcionários (`FuncionarioMenu`)** 👷
* `Listar` todos os funcionários, exibindo o nome do setor.
* `Buscar` um funcionário pelo seu ID.
* **Cadastrar um novo funcionário, selecionando um setor existente.**
* **Atualizar os dados de um funcionário, incluindo a opção de alterar o seu setor.**
* `Remover` um funcionário.

### **Gestão de Produtos (`ProdutoMenu`)** 📦
* `Listar` todos os produtos.
* `Buscar` um produto específico pelo seu ID.
* **Cadastrar um novo produto com nome e descrição.**
* **Atualizar os dados de um produto.**
* `Remover` um produto.

### **Gestão de Produção (`ProducaoMenu`)** 🏭
* `Listar` todos os registos de produção, com detalhes do produto e do funcionário.
* **Cadastrar um novo registo de produção, selecionando um funcionário e um produto.**
* `Remover` um registo de produção.
