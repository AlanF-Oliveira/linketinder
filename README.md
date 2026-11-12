# Linketinder

**Autor:** Alan Ferreira Oliveira

Aplicação de recrutamento inspirada no LinkedIn e no Tinder, desenvolvida em Groovy. O backend utiliza PostgreSQL para persistência e possui uma interface por terminal e uma API REST.

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

Para usar o menu no terminal:

```bash
./gradlew run
```

A classe principal do menu é `org.alan.Main`.

## Curtidas e matches

O sistema permite que candidatos e empresas demonstrem interesse sem revelar
os dados completos uns dos outros antes do match.

### Fluxo

1. Na área do candidato, escolha `Curtir vaga` e informe o ID do candidato e
   da vaga.
2. Na área da empresa, escolha `Curtir candidato` e informe o ID da empresa e
   do candidato.
3. O match é criado automaticamente quando:
   - o candidato curtiu uma vaga da empresa; e
   - a empresa curtiu esse candidato.
4. Depois do match, ambos podem escolher `Visualizar matches` para consultar
   os dados liberados.

As curtidas são armazenadas nas tabelas `curtidas_candidato_vaga` e
`curtidas_empresa_candidato`. Os matches são armazenados na tabela `matches`.
As combinações repetidas não são duplicadas.

### Informações liberadas após o match

- Nome, e-mail, descrição e localização do candidato.
- Nome, e-mail, descrição e localização da empresa.
- Nome, descrição e localização da vaga.

Senhas, CPF e CNPJ não são exibidos.

## Modelo do Match

O sistema utiliza duas tabelas para registrar as curtidas:

- `curtidas_candidato_vaga`: relaciona o candidato à vaga curtida.
- `curtidas_empresa_candidato`: relaciona a empresa ao candidato curtido.

Quando o candidato curte uma vaga, o sistema registra o relacionamento entre
`id_candidato` e `id_vaga`.

Quando a empresa curte esse candidato, o sistema verifica se existe alguma
curtida do candidato em uma vaga pertencente à empresa. Se existir, um registro
é criado na tabela `matches`.

A tabela `matches` relaciona:

- `id_vaga`
- `id_candidato`
- `id_empresa`

As chaves estrangeiras garantem a integridade dos dados e o `ON DELETE CASCADE`
remove as curtidas e matches relacionados quando um candidato, empresa ou vaga
é excluído.

As chaves primárias e a restrição `UNIQUE (id_vaga, id_candidato)` impedem
registros duplicados.

## API REST

A API foi criada com o `HttpServer` do Java, sem framework. Ela recebe dados em JSON e utiliza os controllers, services e DAOs existentes para salvar no PostgreSQL.

Com o banco configurado e `DB_SENHA` definida, execute:

```bash
./gradlew runApi
```

A API ficará disponível em `http://localhost:8080`.

- `POST /candidatos`: cadastra um candidato.
- `POST /empresas`: cadastra uma empresa.
- `POST /vagas`: cadastra uma vaga usando o CNPJ de uma empresa existente.
Os cadastros bem-sucedidos retornam `201 Created` com o ID gerado.

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
- `view`: contém os menus e a interação com o usuário.
- `controller`: recebe as ações dos menus e da API e chama os services.
- `api`: recebe as requisições HTTP e devolve as respostas em JSON.
- `database`: contém a criação da conexão e o script SQL.

Cada entidade possui seu próprio DAO e sua própria interface de repositório:

- `CandidatoDAO` e `CandidatoRepository`.
- `EmpresaDAO` e `EmpresaRepository`.
- `VagaDAO` e `VagaRepository`.
- `CompetenciaDAO` e `CompetenciaRepository`.

## Refatoração, Clean Code e SOLID

As principais melhorias realizadas foram:

- Separação da antiga classe de banco em DAOs por entidade.
- Criação da `PostgresConnectionFactory`.
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

## Padrões de projeto

- Factory: a interface `ConnectionFactory` define a criação de conexões, implementada pela `PostgresConnectionFactory`. Os DAOs recebem a fábrica pelo construtor, permitindo substituir a implementação sem alterar como solicitam conexões.
- DAO: concentra as operações de persistência de cada entidade, separando o SQL das regras da aplicação.

A escolha da fábrica fica no `Menu` e no `ApiMain`. Isso reduz o acoplamento dos DAOs à criação de conexões e facilita os testes. A troca de banco ainda pode exigir ajustes no SQL.

Foi adicionado um teste com mocks para verificar se o `CompetenciaDAO` utiliza a fábrica recebida, sem acessar o banco real.

## MVC

O sistema Groovy segue o fluxo:

```text
Menu (View) → Controller → Service → DAO → Banco
```

Na API, os handlers recebem as requisições HTTP e chamam os controllers. Os models representam os dados da aplicação.

## Frontend

O frontend foi desenvolvido em TypeScript e mantém seus dados em memória no navegador. Ele ainda não está integrado à API.

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