# Changelog

Todas as mudanças relevantes do projeto serão documentadas neste arquivo,
seguindo o formato [Keep a Changelog](https://keepachangelog.com/pt-BR/1.1.0/).

O histórico Git analisado não possui versões ou releases marcadas por tags.
Por isso, as alterações anteriores estão agrupadas como histórico sem versão
publicada.

## [Unreleased]

### Added

- Feature Recrutador completa, com cadastro, vínculo empresarial, status,
  histórico e remoção com preservação de autoria.
- Login local no frontend e login no terminal por CPF/CNPJ + senha, com
  sessão em memória e validação de vínculo ativo.
- Gerenciamento de vagas por recrutadores limitado à empresa vinculada.
- Curtidas de recrutadores registradas em nome da empresa e matches
  empresariais sem duplicidade.
- Gestão local de recrutadores, vagas, candidatos, curtidas e matches no
  frontend, sem integração com o backend.

### Changed

- O frontend passou a identificar o tipo de usuário pelas credenciais, sem
  permitir escolha livre de uma conta incompatível.
- O backend passou a preservar responsáveis históricos após a remoção de
  recrutadores.
- A documentação da feature foi consolidada na SPEC e no plano executável.
- O menu de cadastros do terminal foi separado do menu de gestão, permitindo
  cadastrar candidato ou empresa e retornar ao menu principal para login.

### Security

- CPF ambíguo entre candidato e recrutador é rejeitado no login.
- Operações protegidas de recrutadores exigem vínculo ativo com a empresa.
- Acesso a vagas é filtrado pelo contexto empresarial autenticado.
- A separação entre frontend local e backend foi validada; nenhum endpoint
  novo de recrutador ou cliente HTTP foi criado.

## Histórico sem versão publicada

### Added

- MVP do Linketinder com modelos de candidatos e empresas.
- Serviços de cadastro de candidatos e empresas.
- Modelos de usuário, candidato, empresa e vaga no frontend.
- Dados iniciais de candidatos e empresas no frontend.
- CRUD em memória para candidatos, empresas e vagas no frontend.
- Áreas de candidato e empresa, tela inicial, cadastros, navegação e tooltips
  no frontend.
- CRUD de vagas no backend.
- Modelo e fluxo de curtidas para candidatos e empresas.
- Esquema PostgreSQL e persistência JDBC para candidatos, empresas,
  competências e vagas.
- Persistência completa do backend, incluindo associações com competências.
- API REST para cadastro de candidatos, empresas e vagas usando o
  `HttpServer` do Java.
- Sistema de curtidas e matches, com tabelas de curtidas, tabela de matches,
  transações e prevenção de duplicidades.
- Testes unitários com Spock para services, modelos e o uso da fábrica de
  conexões no DAO de competências.

### Changed

- Modelos de candidato, empresa e vaga foram alinhados ao esquema do banco.
- O backend foi organizado em camadas MVC.
- A criação de conexões foi extraída para `ConnectionFactory` e
  `PostgresConnectionFactory`.
- A persistência foi separada em DAOs por entidade.
- Os services passaram a depender de interfaces de repository e a composição
  das dependências passou a usar injeção pelos construtores.
- A persistência passou a usar cascatas de exclusão nas relações previstas
  pelo esquema do banco.
- O frontend recebeu ajustes de Clean Code, organização e padronização.
- A documentação foi ampliada com instruções de cadastro, testes,
  refatoração, padrões de projeto, API e frontend.

### Fixed

- Tratamento de erros foi adicionado aos fluxos do frontend.

### Removed

- A estrutura antiga de persistência foi removida após a separação em DAOs e
  repositories.
- O ícone de seta não utilizado do frontend foi removido.

### Security

- Foi adicionada anonimização de dados pessoais nas listagens do frontend,
  removendo nome, email, CPF/CNPJ e CEP das visualizações anônimas.

## Critérios do histórico

- Foram considerados commits de implementação, correção, refatoração, teste e
  documentação com mudanças identificáveis no repositório.
- Commits automáticos de sessão, commits de início de turno, checkpoints e
  merges não foram listados como alterações independentes.
- Não foram criadas versões, datas de release ou descrições de comportamento
  que não estivessem evidenciadas pelo histórico Git e pelos arquivos atuais.
- A seção `[Unreleased]` não afirma funcionalidades futuras; ela apenas reserva
  espaço para alterações ainda não publicadas.
