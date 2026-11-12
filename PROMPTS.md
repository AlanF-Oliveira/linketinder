# Prompts do projeto Linketinder

Este arquivo registra os principais prompts utilizados na preparação do
repositório e na implementação da feature **Recrutador** com apoio de agentes
de IA. O código produzido foi revisado e validado no repositório após cada
etapa.

## Preparação do repositório

### Contexto permanente

> Analise detalhadamente o repositório Linketinder e crie um arquivo
> `AGENTS.md` na raiz do projeto. Antes de escrever, explore a arquitetura,
> tecnologias, estrutura de diretórios, entidades, relacionamentos, regras de
> negócio, banco de dados, testes e fluxos existentes. Documente apenas
> informações confirmadas pelo repositório.

### Armazenamento de senhas

> Inspecione a implementação de armazenamento de senhas no backend do
> Linketinder. Verifique especificamente `CandidatoDAO`, `EmpresaDAO`, os
> métodos responsáveis por cadastrar e persistir candidatos e empresas e o
> esquema SQL relacionado aos campos de senha. Não faça alterações. Apresente
> como as senhas são armazenadas, os arquivos e métodos inspecionados e as
> evidências encontradas.

### Skill de desenvolvimento

> Adapte a skill de desenvolvimento com SPEC para o Linketinder. As specs
> devem ficar em `docs/specs/<nome-da-feature>/`, o checklist deve incluir o
> Princípio do Anonimato e cada tarefa deve atualizar o `CHANGELOG.md`.

## Definição da feature Recrutador

### Especificação

> Crie uma SPEC para a feature Recrutador seguindo o contexto confirmado do
> repositório. Defina vínculo com empresa, permissões, login, status,
> histórico, curtidas, matches, anonimato, modelo de dados, critérios de
> aceite, impacto no sistema e fora do escopo. Não invente comportamentos e
> registre decisões e limitações.

### Plano de implementação

> Crie um plano executável com tarefas pequenas e numeradas para implementar a
> feature Recrutador no banco, backend Groovy e frontend TypeScript. Para cada
> tarefa informe dependências, arquivos envolvidos, critérios de conclusão e
> validação. Execute uma tarefa por vez e registre o commit correspondente.

### Contratos de negócio aprovados

> Feche os contratos pendentes da feature Recrutador antes da implementação.
> Considere vínculo empresarial, status ATIVO/INATIVO, histórico, login por
> CPF/CNPJ e senha, exclusividade de CPF, permissões, preservação do
> anonimato, responsáveis históricos e manutenção temporária de senhas em
> texto puro. Registre as decisões na SPEC.

## Implementação incremental

### Frontend local

> Implemente a experiência local do recrutador no frontend TypeScript,
> incluindo modelo, dados, service, login, sessão, painel, vagas, candidatos,
> curtidas, matches e gestão de recrutadores pela empresa. O frontend deve
> permanecer independente do backend, sem HTTP, PostgreSQL ou `fetch`.

### Banco de dados

> Modele no script SQL o recrutador, o vínculo com a empresa, status,
> histórico, responsáveis históricos e as relações necessárias para curtidas
> empresariais. Preserve CPF exclusivo entre candidatos e recrutadores e a
> autoria histórica após remoção.

### Backend

> Implemente o modelo, repository, DAO, service, controller e menus do
> recrutador no backend Groovy. A empresa deve cadastrar, editar, listar e
> remover seus recrutadores. Operações protegidas devem respeitar o vínculo
> empresarial ativo e retornar erros explicitamente.

### Login e permissões

> Integre login por CPF/CNPJ e senha no terminal. O tipo de usuário deve ser
> identificado pelas credenciais, sem escolha livre incompatível. O candidato,
> a empresa e o recrutador devem receber áreas próprias, com sessão em memória
> e logout ao sair.

### Vagas

> Restrinja o gerenciamento de vagas pelo recrutador à empresa do vínculo
> ativo. Permita listar, criar, editar e excluir vagas da empresa, sem filtrar
> pela área de atuação do recrutador e sem permitir acesso a vagas de outra
> empresa.

### Curtidas e matches

> Faça a curtida do recrutador representar a empresa. Valide o vínculo ativo,
> registre o responsável histórico, mantenha uma única curtida empresarial e
> não duplique matches quando recrutadores diferentes da mesma empresa curtem
> o mesmo candidato.

## Correções e validações posteriores

### Fluxo de cadastro

> Corrija o fluxo de cadastro do backend quando o estado informado não respeitar
> `CHAR(2)` e simplifique o menu para separar cadastro público de operações
> administrativas.

### Áreas autenticadas

> Ajuste as áreas autenticadas: candidato deve visualizar vagas e empresa
> deve visualizar candidatos anonimizados. Remova ações administrativas das
> telas de consulta e preserve as permissões por perfil.

### Login após cadastro no frontend

> Corrija o login após cadastro no frontend. Garanta que os services de
> cadastro e autenticação usem as mesmas coleções locais, normalize CPF/CNPJ e
> encaminhe o usuário para a tela de login após concluir o cadastro.

### Logout

> Adicione um botão explícito de logout nas áreas autenticadas. O logout deve
> limpar a sessão local e retornar à tela inicial, mantendo a navegação interna
> compatível com o `AuthService`.

### Identificação pública de candidatos

> Ao listar candidatos anonimizados para empresa ou recrutador, exiba somente
> um ID público para permitir a curtida. Não exiba CPF, e-mail, nome, CEP ou
> outros dados identificáveis antes do match.

### Correção do acesso do recrutador

> Corrija o acesso da área autenticada do recrutador quando o contexto da
> empresa for necessário. O CNPJ deve ser obtido da empresa vinculada ao
> recrutador, não de uma propriedade inexistente no próprio recrutador.

### Cobertura de testes

> Amplie a cobertura Spock para autenticação de empresa e recrutador,
> credenciais ambíguas, autorização de curtidas por vínculo ativo, atualização
> e remoção de recrutadores e validações de campos obrigatórios.
