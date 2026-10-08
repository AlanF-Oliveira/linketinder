# Linketinder

**Autor:** Alan Ferreira Oliveira

Aplicação de recrutamento inspirada no LinkedIn e no Tinder, desenvolvida em Groovy. O backend utiliza PostgreSQL para persistência e possui uma interface por terminal.

## Tecnologias

- Groovy 4
- Gradle
- PostgreSQL
- JDBC
- Spock Framework
- TypeScript no frontend

## Configuração do banco

Crie um banco PostgreSQL chamado `linketinder` e execute o script:

```text
src/main/groovy/org/alan/database/linketinder.sql
```

A senha do usuário `postgres` deve ser informada pela variável de ambiente `DB_SENHA`.

No terminal:

```bash
export DB_SENHA='SUA_SENHA'
```

Também é possível configurar essa variável nas opções de execução do IntelliJ.

## Como executar

```bash
./gradlew run
```

A classe principal da aplicação é:

```text
org.alan.Main
```

## Como executar os testes

```bash
./gradlew test
```

Os testes unitários utilizam Spock e isolam os services por meio de stubs das interfaces de repositório.

## Organização do backend

O backend está dividido nas seguintes camadas:

- `model`: representa candidatos, empresas e vagas.
- `repository`: define os contratos de acesso aos dados.
- `dao`: implementa os repositórios e realiza o acesso ao PostgreSQL.
- `service`: contém as regras da aplicação.
- `terminal`: controla a interação pelo menu.
- `database`: contém a criação da conexão e o script SQL.

Cada entidade possui seu próprio DAO e sua própria interface de repositório:

- `CandidatoDAO` e `CandidatoRepository`.
- `EmpresaDAO` e `EmpresaRepository`.
- `VagaDAO` e `VagaRepository`.
- `CompetenciaDAO` e `CompetenciaRepository`.

## Refatoração, Clean Code e SOLID

As principais melhorias realizadas foram:

- Separação da antiga classe de banco em DAOs por entidade.
- Criação da `ConnectionFactory`.
- Criação de interfaces para os repositórios.
- Uso de injeção de dependências por meio dos construtores.
- Services dependendo de interfaces em vez de DAOs concretos.
- Remoção do acoplamento direto entre os DAOs.
- Uso de nomes mais claros para métodos e variáveis.
- Extração de métodos menores para evitar repetição.
- Remoção de código antigo e não utilizado.
- Uso de `ON DELETE CASCADE` nas tabelas associativas.
- Tratamento de falhas nos services e na configuração da conexão.
- Padronização de nomes e formatação no frontend.
- Ampliação dos testes unitários de candidatos, empresas, vagas e competências.

Essas mudanças reduziram duplicações, diminuíram o acoplamento e deixaram as responsabilidades das classes mais claras. O uso de interfaces também facilitou a criação dos stubs utilizados nos testes.

## Frontend

O frontend foi desenvolvido em TypeScript e mantém seus dados em memória no navegador.

Para executar:

```bash
cd frontend
npm install
npm run dev
```

Acesse:

```text
http://localhost:5173
```