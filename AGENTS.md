# Linketinder — contexto para agentes

Este arquivo descreve o estado confirmado do repositório. Ele deve ser
atualizado quando o código, os scripts de banco ou os comandos de execução
mudarem. Não trate funcionalidades mencionadas apenas como intenção ou
documentação antiga como implementadas.

## 1. Visão geral

Linketinder é uma aplicação de recrutamento inspirada em LinkedIn e Tinder.
O backend em Groovy oferece:

- uma aplicação de terminal para administrar candidatos, empresas, vagas,
  competências, curtidas e matches;
- uma API HTTP simples para cadastrar candidatos, empresas e vagas;
- persistência em PostgreSQL via JDBC.

Existe também um frontend TypeScript/Vite com interface no navegador. Ele usa
listas de dados criadas no próprio frontend e mantém as alterações somente em
memória; não há integração implementada entre ele e a API Groovy.

O fluxo do backend segue, em termos gerais:

```text
Menu ou handler HTTP -> Controller -> Service -> Repository/DAO -> PostgreSQL
```

## 2. Tecnologias e ferramentas

### Backend

- Groovy 4.0.31.
- Gradle, com os plugins `groovy` e `application`.
- Java 8 como compatibilidade de origem e destino no `build.gradle`.
- PostgreSQL acessado pelo driver JDBC `org.postgresql:postgresql`.
- `com.sun.net.httpserver.HttpServer` para a API, sem framework web.
- `groovy-json` para ler e gerar JSON.
- Spock 2.3 para testes, executado pela plataforma JUnit.

### Frontend

- TypeScript 6.x.
- Vite 8.x.
- Bootstrap 5 para componentes/estilos.
- Chart.js para o gráfico de competências.
- npm e `package-lock.json` para dependências.

### Ferramentas do projeto

- `gradlew`/`gradlew.bat` são os lançadores Gradle versionados.
- `build.gradle` define compilação, dependências, `run`, `runApi` e testes.
- `frontend/package.json` define `dev`, `build` e `preview`.
- Não há configuração de lint ou framework de testes do frontend no
  `package.json`.

## 3. Arquitetura

### Backend Groovy

O backend separa responsabilidades por camadas:

1. `model` contém os objetos de domínio.
2. `repository` define contratos para persistência.
3. `dao` implementa esses contratos e executa SQL JDBC.
4. `service` delega as operações de aplicação aos repositórios.
5. `controller` faz a ponte entre menus/HTTP e services.
6. `view` contém o menu interativo de terminal.
7. `api` contém os `HttpHandler` da API.
8. `database` contém a fábrica de conexões e o script usado pelo backend.

`Main` inicializa o menu. `ApiMain` monta as dependências, cria os contextos
HTTP e inicia o servidor em `localhost:8080`. `Menu` monta a mesma composição
de DAOs, services e controllers para o terminal.

A dependência de banco é injetada nos DAOs por `ConnectionFactory`; a
implementação atual é `PostgresConnectionFactory`. Os services dependem das
interfaces de repository, o que permite os stubs usados nos testes.

### Frontend TypeScript

`frontend/src/main.ts` instancia os services com os arrays de dados iniciais e
renderiza a tela inicial. As views substituem o conteúdo de `#app` e conectam
eventos diretamente aos services. Não há camada HTTP, cliente da API ou
persistência no navegador além do estado em memória da sessão da página.

## 4. Estrutura e responsabilidades

### Raiz

- [`README.md`](./README.md): descrição geral, comandos e arquitetura
  pretendida/atual; contém alguns caminhos de banco que precisam ser
  conferidos conforme a seção de divergências.
- [`build.gradle`](./build.gradle): build e dependências do backend.
- [`settings.gradle`](./settings.gradle): configuração do projeto Gradle.
- [`gradlew`](./gradlew) e [`gradlew.bat`](./gradlew.bat): execução Gradle.
- [`src/main/groovy/org/alan/Main.groovy`](./src/main/groovy/org/alan/Main.groovy):
  entrada da aplicação de terminal.
- [`src/main/groovy/org/alan/ApiMain.groovy`](./src/main/groovy/org/alan/ApiMain.groovy):
  entrada da API HTTP.

### Backend

- `src/main/groovy/org/alan/model`: `Pessoa`, `Usuario`, `Candidato`,
  `Empresa`, `Vaga` e `Match`.
- `src/main/groovy/org/alan/repository`: `CandidatoRepository`,
  `EmpresaRepository`, `VagaRepository`, `CompetenciaRepository` e
  `MatchRepository`.
- `src/main/groovy/org/alan/dao`: SQL e mapeamento JDBC de candidatos,
  empresas, vagas, competências e matches.
- `src/main/groovy/org/alan/service`: serviços correspondentes às entidades e
  ao fluxo de matches.
- `src/main/groovy/org/alan/controller`: controllers usados pelo terminal e
  pela API.
- `src/main/groovy/org/alan/view`: menus de candidato, empresa, vaga,
  competência, match e menu principal.
- `src/main/groovy/org/alan/api`: handlers `POST` de candidatos, empresas e
  vagas.
- `src/main/groovy/org/alan/database`: `ConnectionFactory`,
  `PostgresConnectionFactory`, imagem do esquema e
  [`linketinder.sql`](./src/main/groovy/org/alan/database/linketinder.sql).

### Frontend

- [`frontend/src/main.ts`](./frontend/src/main.ts): composição inicial.
- `frontend/src/models`: modelos TypeScript e funções de anonimização.
- `frontend/src/services`: CRUD em arrays em memória e geração do gráfico.
- `frontend/src/views`: telas inicial, áreas de candidato/empresa, cadastros,
  listagens e gráfico.
- `frontend/src/data`: dados iniciais de candidatos, empresas e vagas.
- [`frontend/index.html`](./frontend/index.html): documento HTML com `#app`.
- [`frontend/tsconfig.json`](./frontend/tsconfig.json): compilação sem emissão,
  resolução bundler e verificações de uso não utilizado.
- [`frontend/dist`](./frontend/dist): saída gerada pelo build; não é fonte de
  integração com o backend.

## 5. Domínio, entidades e regras confirmadas

### Backend

- `Candidato` herda os campos de `Usuario` e acrescenta `id`, sobrenome, CPF,
  nascimento e competências.
- `Empresa` herda `Usuario` e acrescenta `id` e CNPJ.
- `Vaga` possui id, título, descrição, estado, cidade, competências exigidas e
  uma empresa.
- `Competencia` é representada na persistência por id e descrição.
- `Match` representa a combinação de vaga, candidato e empresa e também
  carrega os dados textuais liberados na consulta de matches.

O banco relaciona:

- uma empresa a várias vagas (`vagas.id_empresa`);
- candidatos e competências em N:N por `candidato_competencia`;
- vagas e competências em N:N por `vagas_competencias`;
- candidatos e vagas em N:N por `curtidas_candidato_vaga`;
- empresas e candidatos em N:N por `curtidas_empresa_candidato`;
- matches a uma vaga, candidato e empresa por chaves estrangeiras.

As operações observáveis no terminal incluem cadastro, listagem, atualização e
exclusão de candidatos e empresas; cadastro, listagem, atualização e exclusão
de vagas; manutenção de competências; curtidas e consulta de matches.

As restrições explícitas do script usado pelo backend incluem CPF e CNPJ
únicos, senha com pelo menos seis caracteres e chaves estrangeiras. A
persistência usa `ON DELETE CASCADE` em entidades associativas e em relações de
vagas/matches conforme definido no SQL.

### Frontend

`CandidatoService`, `EmpresaService` e `VagaService` fazem CRUD em arrays:
CPF, CNPJ e id de vaga não podem ser duplicados no cadastro; atualização e
exclusão lançam erro quando o identificador não existe. O modelo de vaga gera
ids incrementalmente no processo do navegador.

## 6. Curtidas e matches

O fluxo de curtida está implementado no backend de terminal, por
`MatchMenu` -> `MatchController` -> `MatchService` -> `MatchDAO`:

1. candidato curte uma vaga por `id_candidato` e `id_vaga`;
2. a curtida é inserida em `curtidas_candidato_vaga`;
3. empresa curte candidato por `id_empresa` e `id_candidato`;
4. a curtida é inserida em `curtidas_empresa_candidato`;
5. ao encontrar as duas relações para uma vaga da empresa, o DAO insere
   `matches`;
6. a transação usa `ON CONFLICT DO NOTHING`, portanto curtidas repetidas não
   duplicam a relação; `matches` também possui conflito protegido por
   `(id_vaga, id_candidato)`.

A consulta de matches aceita filtro por candidato ou por empresa e retorna
nome, email, descrição e local de candidato e empresa, além de nome, descrição
e local da vaga. O menu exibe também ids de vaga e candidato. O código de
match não retorna senha, CPF ou CNPJ.

Não há endpoints HTTP para curtidas, matches, listagem ou autenticação. Os
handlers HTTP atuais expõem somente:

- `POST /candidatos`;
- `POST /empresas`;
- `POST /vagas`.

O frontend não implementa curtidas ou matches conectados ao backend; suas
listagens são locais e não constituem o fluxo de match do backend.

## 7. Anonimato e restrições

No frontend, `anonimizarCandidato` remove nome, email, CPF e CEP antes de
renderizar a lista de candidatos. `anonimizarEmpresa` remove nome, email, CNPJ
e CEP antes de renderizar vagas. As telas exibem atributos profissionais ou
locacionais restantes, como formação, idade, estado, competências e descrição.

No backend, o match libera os campos definidos pelo `MatchDAO`, mas não senha,
CPF ou CNPJ. Essa é a restrição de dados confirmada para a saída de matches.

Não se deve ampliar a exposição de identificadores, documentos ou senhas ao
adicionar fluxos. Também não se deve assumir que a anonimização do frontend
protege a API ou o terminal: o código atual do menu imprime `toString()` de
candidatos e empresas, que inclui dados identificadores. Essa diferença deve
ser preservada como comportamento conhecido ou corrigida explicitamente em
uma mudança futura, nunca mascarada na documentação.

## 8. Banco de dados

### Script efetivamente usado pelo backend

O script referenciado pelas classes/documentação do backend é
[`src/main/groovy/org/alan/database/linketinder.sql`](./src/main/groovy/org/alan/database/linketinder.sql).
Ele cria `candidatos`, `empresa`, `vagas`, `competencias`,
`candidato_competencia`, `vagas_competencias`,
`curtidas_candidato_vaga`, `curtidas_empresa_candidato` e `matches`, além de
inserir dados de exemplo e uma curtida/match inicial.

A conexão é fixa em `jdbc:postgresql://localhost:5432/linketinder`, com usuário
`postgres`. A senha é obrigatória na variável de ambiente `DB_SENHA`; sem ela,
`PostgresConnectionFactory` lança `IllegalStateException`.

Configuração confirmada:

```bash
createdb linketinder
psql -U postgres -d linketinder \
  -f src/main/groovy/org/alan/database/linketinder.sql
export DB_SENHA='SUA_SENHA'
```

O uso de `createdb`/`psql` pressupõe que o PostgreSQL esteja instalado e
acessível localmente; o repositório não contém configuração Docker ou migração
automatizada.

### Fonte oficial

Para este projeto, considere apenas
[`src/main/groovy/org/alan/database`](./src/main/groovy/org/alan/database) como
a área oficial de banco de dados. O script oficial é
[`linketinder.sql`](./src/main/groovy/org/alan/database/linketinder.sql), usado
pela configuração documentada acima e compatível com
`PostgresConnectionFactory`.

A pasta `banco/` deve ser ignorada por agentes: não use seus scripts,
README ou diagrama para inferir tabelas, relacionamentos, regras ou comandos
de configuração. Caso algum documento ainda a mencione, priorize o código e o
script em `src/main/groovy/org/alan/database`.

## 9. Testes

Os testes do backend ficam em
[`src/test/groovy`](./src/test/groovy), organizados por `model`, `service` e
`dao`. Eles usam Spock; os testes de service usam stubs de interfaces e o teste
de `CompetenciaDAO` usa mocks de conexão/fábrica, sem depender de um banco real.
Não há testes de handlers HTTP nem testes automatizados do frontend.

Comandos confirmados:

```bash
./gradlew test
cd frontend && npm install
cd frontend && npm run build
```

`./gradlew test` executa a suíte Groovy/Spock. `npm run build` executa `tsc` e
depois o build Vite. O build do frontend deve ser executado a partir de
`frontend/`, onde está o `package.json`; executá-lo na raiz falha por ausência
desse arquivo.

## 10. Execução

### Terminal

1. Configure o PostgreSQL e o banco conforme a seção anterior.
2. Exporte `DB_SENHA`.
3. Execute:

   ```bash
   ./gradlew run
   ```

4. Use o menu principal para entrar nas áreas de candidato, empresa, vagas ou
   competências. As opções de curtida e consulta de matches ficam nas áreas de
   candidato e empresa.

### API

Com o PostgreSQL configurado e `DB_SENHA` definida:

```bash
./gradlew runApi
```

A API escuta `http://localhost:8080` e aceita somente os `POST` descritos na
seção de matches. Os handlers respondem JSON; cadastros bem-sucedidos retornam
`201` com o id. JSON/campos inválidos retornam `400`, CPF/CNPJ duplicado
retorna `409` nos handlers correspondentes e falhas não classificadas retornam
`500`. Métodos diferentes de `POST` retornam `405`.

### Frontend

```bash
cd frontend
npm install
npm run dev
```

## Desenvolvimento assistido por IA

Para implementar novas funcionalidades, utilize o processo definido em:

`skills/feature-com-spec/SKILL.md`

Respeite as etapas de análise, especificação, aprovação,
planejamento, implementação incremental, validação e commits.

O Vite serve a interface em `http://localhost:5173`. Para uma prévia da saída
gerada, use `npm run preview` depois de `npm run build`. A interface usa os
dados de `frontend/src/data` e não exige o backend para iniciar.

## 11. Convenções e boas práticas existentes

- Use nomes e camadas já adotados: model, repository, DAO, service,
  controller, view e api.
- Injete `ConnectionFactory` nos DAOs e dependa de interfaces nos services;
  evite abrir conexões diretamente em regra de negócio.
- Mantenha SQL parametrizado com `PreparedStatement`; não concatene valores de
  entrada em consultas.
- Preserve transações e `ON CONFLICT` no fluxo de curtidas/matches.
- Feche conexões no `finally`, como fazem os DAOs atuais.
- No frontend, mantenha modelos, services e views separados e passe os
  services pelas funções de tela em vez de criar estado global adicional.
- Respeite as verificações de `tsconfig.json`, especialmente
  `noUnusedLocals`, `noUnusedParameters`, `noFallthroughCasesInSwitch` e
  `noEmit`.
- Use Spock e stubs/mocks para testar services/DAOs sem banco quando o teste
  não exigir integração.
- Atualize documentação diretamente relacionada quando alterar comandos,
  contratos, esquema ou comportamento observável.
- Não trate `frontend` como cliente da API até que uma integração explícita
  seja implementada.

## Dúvidas, limitações e pontos para revisão humana

- A pasta `banco/` contém material que não deve ser considerado fonte do
  projeto; a fonte oficial é `src/main/groovy/org/alan/database`.
- Não há autenticação/autorização implementada nos menus nem na API; os ids
  recebidos pelos fluxos de terminal não são associados a uma sessão de
  usuário.
- As senhas de candidatos e empresas são atualmente armazenadas e recuperadas
  em texto puro.
- Não existe mecanismo de hash de senhas na implementação atual; essa é uma
  limitação de segurança conhecida do projeto.
- A anonimização existe no frontend, mas listagens do terminal exibem dados
  identificadores e a API não oferece listagem anonimizada.
- A documentação fala em interface/API e em match, mas não há endpoints HTTP
  para essas operações de match nem integração frontend-backend.
- Não há cobertura automatizada de API, frontend, banco real ou testes de
  integração.
- O repositório contém artefatos gerados (`build` e `frontend/dist`) e um
  diretório `skills/` não rastreado no estado analisado. Não altere esses
  itens incidentalmente ao trabalhar no código.
