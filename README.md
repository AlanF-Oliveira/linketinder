# Linketinder

**Autor:** Alan Ferreira Oliveira

Aplicação backend de recrutamento inspirada no Linkedin e no Tinder, feita em Groovy.

## Tecnologias

- Groovy 4
- Gradle
- Spock Framework (testes unitários)

## Como executar

```bash
git clone https://github.com/AlanF-Oliveira/linketinder.git
cd linketinder
./gradlew run
```

Ou rode a classe `org.alan.Terminal.Menu` diretamente pela IDE.

## Como rodar os testes

```bash
./gradlew test
```

## Sobre a solução

O projeto é organizado em pacotes (`Model`, `usuarios`, `Terminal`, `service`). Candidatos e empresas compartilham atributos comuns (nome, e-mail, CEP, estado e descrição) através da interface `Pessoa` e da classe abstrata `Usuario`, da qual `Candidato` e `Empresa` herdam. Cada candidato tem CPF, idade e uma lista de competências; cada empresa tem CNPJ, país e uma lista de competências desejadas.

O sistema mantém 5 candidatos e 5 empresas pré-cadastrados em memória. O menu no terminal permite listar candidatos, listar empresas, cadastrar um novo candidato e cadastrar uma nova empresa.

O cadastro é feito pelas classes `CandidatoService` e `EmpresaService`, que inserem novos usuários nas listas de `UsuariosCadastrados`.

## Frontend

Versão em TypeScript do frontend do Linketinder. Ainda não se comunica com o backend Groovy, os dados são mantidos em memória, no navegador.

### Tecnologias
- TypeScript
- Vite
- Bootstrap 5
- Chart.js

### Como executar
```bash
cd frontend
npm i
npm run dev
```
Acesse `http://localhost:5173` no navegador.

### Sobre a solução

O sistema é uma SPA (Single Page Application): uma única página HTML, com as telas trocando de conteúdo via JavaScript, sem recarregar. As telas disponíveis são:

- Tela inicial, com escolha de perfil
- Cadastro de candidato e cadastro de empresa
- Lista de vagas disponíveis
- Cadastro de vaga 
- Perfil da empresa: lista de candidatos anônimos e gráfico de barras com a quantidade de candidatos por competência

