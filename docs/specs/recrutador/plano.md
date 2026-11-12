# Plano executável — Feature Recrutador

**Especificação:** [`docs/specs/recrutador/spec.md`](./spec.md)  
**Status:** Task 1 confirmada; aguardando início da Task 2  
**Branch:** `feature/recrutador`

## Regras de execução

- Implementar somente o que está na SPEC aprovada.
- Executar uma tarefa por vez, na ordem indicada.
- Não iniciar uma tarefa dependente enquanto a anterior não estiver concluída,
  validada e commitada.
- Cada tarefa de implementação deve ter exatamente um commit próprio.
- Antes de cada tarefa, revisar a SPEC, o diff acumulado e as alterações
  preexistentes.
- Implementar backend e frontend nesta feature. O frontend deve ser funcional
  e local, sem integração HTTP, consumo da API Groovy ou persistência
  compartilhada com o backend.
- Não criar endpoints HTTP novos nem alterar `ApiMain.groovy` para adicionar
  rotas de recrutador.
- Não usar a pasta `banco/`; o único script oficial é
  `src/main/groovy/org/alan/database/linketinder.sql`.
- Não prosseguir com decisões de negócio pendentes, testes falhando ou
  alterações fora do escopo.
- Após cada tarefa, executar `git diff --check`, conferir os arquivos staged,
  criar o commit com o trailer de coautoria e registrar o hash neste plano.

## Dependências globais e gates

As decisões bloqueadoras da Task 1 foram aprovadas e registradas na SPEC.
Nenhuma tarefa posterior deve reabrir esses contratos sem nova aprovação.

## Quadro de acompanhamento diário

Registrar no máximo uma confirmação de tarefa por dia. A tarefa somente deve
ser marcada como **Confirmada** após a execução, validação e atendimento dos
critérios de conclusão correspondentes. Usar **Pendente** para tarefas que
ainda não começaram, **Em andamento** para tarefas iniciadas e **Bloqueada**
quando houver impedimento, registrando o motivo nas observações. Preencher o
hash do commit quando a tarefa for concluída e commitada.

| Tarefa | Status | Data da confirmação | Hash do commit | Observações |
|---|---|---|---|---|
| 1. Fechando contratos pendentes da feature | Confirmada | 2026-11-12 | b528f3a | Decisões aprovadas e registradas na SPEC. |
| 2. Implementando a experiência completa do recrutador no frontend local | Confirmada | 2026-11-12 | 9abf64b | Frontend local implementado; build TypeScript/Vite aprovado. |
| 3. Modelando recrutador, vínculo e histórico no banco | Confirmada | 2026-11-12 | f047844 | Schema executado em PostgreSQL temporário; CPF exclusivo e preservação histórica validados. |
| 4. Implementando modelo e persistência de recrutadores | Confirmada | 2026-11-12 | eeb3ab4 | CRUD, autorização por empresa, vínculo ativo, histórico e validações testados. |
| 5. Integrando login simples e permissões no terminal | Pendente | — | — | Depende da tarefa 4. |
| 6. Integrando recrutadores ao gerenciamento de vagas | Pendente | — | — | Depende das tarefas 4 e 5. |
| 7. Integrando curtidas, responsáveis e matches | Pendente | — | — | Depende das tarefas 3, 4 e 5. |
| 8. Validando segurança, privacidade e regressões da feature | Pendente | — | — | Depende das tarefas 2 a 7. |
| 9. Verificando a fronteira de integração sem conectá-la | Pendente | — | — | Depende da tarefa 8. |

## Tarefas

### 1. Fechando contratos pendentes da feature

**Dependências:** nenhuma.  
**Objetivo:** transformar as pendências da SPEC em contratos implementáveis,
sem adicionar regras não aprovadas.

**Atividades:**

- Registrar os status `ATIVO` e `INATIVO`, com transições livres e remoção
  definitiva do cadastro.
- Registrar o histórico com status anterior, novo status, data e responsável.
- Registrar o uso de CPF + senha para candidato e recrutador e CNPJ + senha
  para empresa, nos fluxos independentes de backend e frontend local.
- Registrar a exclusividade de CPF entre candidatos e recrutadores, impedindo
  login ambíguo sem escolha livre do tipo de conta.
- Registrar as permissões da empresa e do recrutador limitado ao vínculo
  ativo.
- Registrar a validação estrutural com campos obrigatórios, formato básico e
  limites de tamanho.
- Registrar a manutenção temporária de senhas em texto puro, sem escolher
  hash implicitamente.
- Registrar sessão em memória até logout ou encerramento do processo.
- Registrar que somente dados da empresa aparecem após o match.
- Registrar a preservação histórica em registro separado com ID, nome e área
  congelados, sem credenciais ou acesso operacional.

**Critérios de conclusão:**

- Nenhum item bloqueador da seção de gates permanece sem decisão.
- A SPEC contém os contratos aprovados e seus limites.
- A alternativa de preservação histórica foi aprovada antes de definir
  `ON DELETE`, chaves estrangeiras ou modelo de responsáveis.
- Não há alteração de produção, testes ou banco nesta tarefa.

**Validação:**

- Revisão da SPEC aprovada pelo responsável do produto/Tutor.
- `git diff --check`.

**Commit sugerido:** `docs: fecha contratos da feature recrutador`  
**Commit:** pendente.

### 2. Implementando a experiência completa do recrutador no frontend local

**Dependências:** tarefa 1. Esta tarefa é independente das tarefas de backend.
**Objetivo:** implementar no frontend TypeScript a experiência funcional local
do recrutador e da empresa, sem depender de nenhuma implementação backend.

**Arquivos/superfícies prováveis:**

- `frontend/src/models/Recrutador.ts`
- `frontend/src/services/RecrutadorService.ts`
- `frontend/src/data/recrutadores.ts`
- `frontend/src/views/areaEmpresa.ts`
- `frontend/src/views/areaRecrutador.ts`
- novas views de login, painel, cadastro/edição/listagem de recrutadores,
  vagas, candidatos e matches, reutilizando as views e services existentes;
- `frontend/src/main.ts`, somente para compor o estado local e a navegação.

**Atividades:**

- Reaproveitar `Usuario`, `Empresa`, `Candidato`, `Vaga`, os services
  existentes e a navegação atual por views/callbacks, evitando duplicação.
- Criar o modelo, dados e service locais do recrutador com vínculo, status e
  histórico conforme os contratos aprovados.
- Criar login local com CPF + senha para candidato e recrutador e CNPJ + senha
  para empresa, identificando o tipo de usuário durante a sessão sem
  autenticação compartilhada com o backend.
- Criar acesso à área/painel do recrutador, sem integração ou autenticação real
  com o backend.
- Permitir ao recrutador visualizar todas as vagas da empresa vinculada e
  criar, editar e excluir essas vagas localmente.
- Permitir listar e visualizar perfis de candidatos, preservando a
  anonimização antes do match.
- Implementar curtidas locais em nome da empresa, consolidação de curtidas da
  mesma empresa e visualização de matches conforme as regras aprovadas.
- Adicionar à área da empresa cadastro, edição, listagem e remoção de
  recrutadores.
- Não criar `fetch`, cliente HTTP, endpoint ou dependência da API Groovy.

**Critérios de conclusão:**

- O login local direciona o usuário para a área compatível com seu tipo.
- Os fluxos atuais de candidato e empresa continuam funcionando.
- O painel do recrutador permite visualizar as vagas da empresa vinculada.
- O recrutador consegue criar, editar e excluir vagas localmente somente da
  empresa vinculada.
- O recrutador consegue listar e visualizar candidatos, curtir em nome da
  empresa e consultar matches.
- A anonimização dos candidatos é preservada antes do match.
- Após o match, as telas apresentam os dados conforme as regras de
  visibilidade aprovadas.
- A empresa consegue cadastrar, editar, listar e remover recrutadores
  localmente.
- O frontend representa vínculo, status e histórico aprovados durante a
  sessão.
- Alterações realizadas localmente são refletidas nas telas durante a sessão.
- Não há chamadas ao backend nem expectativa de persistência PostgreSQL.
- O build TypeScript/Vite permanece válido.

**Testes/verificações:**

```bash
cd frontend && npm run build
```

**Commit sugerido:** `feat: adiciona fluxo local de recrutadores no frontend`  
**Commit:** pendente.

### 3. Modelando recrutador, vínculo e histórico no banco

**Dependências:** tarefa 1.  
**Objetivo:** criar o modelo relacional oficial para recrutadores, vínculos e
histórico.

**Arquivos/superfícies:**

- `src/main/groovy/org/alan/database/linketinder.sql`
- eventuais arquivos SQL versionados adicionais, somente se aprovados pela
  organização do projeto.

**Atividades:**

- Criar tabela de recrutadores com os campos aprovados.
- Criar a associação recrutador-empresa com status e integridade referencial,
  conforme o contrato aprovado.
- Criar a estrutura de histórico de status.
- Criar a estrutura para registrar um ou mais responsáveis por uma curtida
  empresarial sem duplicar a curtida da empresa.
- Definir unicidades, índices, `ON DELETE` e demais constraints.
- Garantir que a exclusão de um recrutador siga a decisão aprovada para
  histórico e matches.
- Manter as tabelas atuais e sua unicidade de curtidas/matches compatíveis com
  o comportamento existente.

**Critérios de conclusão:**

- O script pode ser executado em banco vazio sem referências quebradas.
- Um recrutador pertence a uma empresa conforme a regra aprovada.
- O status e o histórico são persistidos.
- Curtidas de dois recrutadores da mesma empresa não criam duas decisões
  empresariais.
- Chaves estrangeiras impedem vínculos ou responsáveis inexistentes.
- O schema atual de candidatos, empresas, vagas e matches continua compatível.

**Testes/verificações:**

- Revisão SQL e verificação de constraints.
- Executar `git diff --check`.
- Se houver PostgreSQL disponível, executar o script em banco de teste
  descartável e consultar as constraints; registrar indisponibilidade caso
  não seja possível.

**Commit sugerido:** `feat: adiciona persistencia de recrutadores`  
**Commit:** pendente.

### 4. Implementando modelo e persistência de recrutadores

**Dependências:** tarefa 3.  
**Objetivo:** implementar o CRUD de recrutadores e o vínculo empresarial nas
camadas de domínio, repository, DAO e service.

**Arquivos/superfícies prováveis:**

- `src/main/groovy/org/alan/model/Recrutador.groovy`
- `src/main/groovy/org/alan/repository/RecrutadorRepository.groovy`
- `src/main/groovy/org/alan/dao/RecrutadorDAO.groovy`
- `src/main/groovy/org/alan/service/RecrutadorService.groovy`
- `src/main/groovy/org/alan/controller/RecrutadorController.groovy`
- `EmpresaRepository`, `EmpresaDAO` e `EmpresaService`, somente se exigido
  pelo contrato de autorização.

**Atividades:**

- Criar o modelo com os campos aprovados.
- Implementar cadastro pela empresa, edição, busca, listagem e exclusão.
- Validar que as operações administrativas pertencem à empresa correta.
- Implementar a autorização por contexto explícito da empresa/vínculo,
  independentemente da sessão interativa; a tarefa 5 conectará esse contexto
  ao login do terminal.
- Aplicar status e histórico do vínculo.
- Aplicar as validações aprovadas no service/DAO/banco sem duplicar regras
  contraditórias.
- Evitar exposição de CPF, data de nascimento e senha em `toString`,
  listagens ou respostas.
- Preservar o padrão de injeção de `ConnectionFactory` e interfaces de
  repository.

**Critérios de conclusão:**

- A empresa consegue cadastrar, editar e remover apenas seus recrutadores.
- Recrutador sem vínculo ativo não executa operações protegidas.
- O histórico é atualizado em cada transição aprovada.
- Dados privados não são retornados em listagens destinadas à análise.
- Falhas de validação e de vínculo são explícitas e testáveis.

**Testes:**

- Testes Spock do service para cadastro, edição, busca, remoção, vínculo,
  permissões e validações.
- Testes do DAO com mocks da fábrica/conexão para SQL e mapeamento.

**Commit sugerido:** `feat: implementa cadastro de recrutadores`  
**Commit:** pendente.

### 5. Integrando login simples e permissões no terminal

**Dependências:** tarefa 4.  
**Objetivo:** permitir login simples dos tipos de usuário aprovados e aplicar
  o contexto do usuário nas operações do terminal.

**Arquivos/superfícies prováveis:**

- `src/main/groovy/org/alan/view/Menu.groovy`
- novos menus de login/recrutador;
- `CandidatoMenu.groovy`, `EmpresaMenu.groovy` e `VagasMenu.groovy`;
- controllers/services necessários para autorização.

**Atividades:**

- Implementar o fluxo de login sem endpoints HTTP.
- Representar a sessão/contexto no processo do terminal conforme contrato
  aprovado.
- Permitir à empresa cadastrar, editar, listar e remover seus recrutadores.
- Permitir ao recrutador operar somente dentro da empresa do vínculo ativo.
- Manter o menu de candidato e seus fluxos existentes.
- Informar falhas de login e autorização sem defaults silenciosos.

**Critérios de conclusão:**

- Candidato, empresa e recrutador entram pelo fluxo de login aprovado.
- Uma empresa não acessa recrutadores de outra empresa.
- Um recrutador sem vínculo ativo não administra vagas.
- O terminal não solicita livremente um `idEmpresa` para substituir o
  contexto autenticado nas operações protegidas.
- Nenhum endpoint novo é criado.

**Testes:**

- Testes unitários dos serviços de autenticação/autorização, se criados.
- Testes dos controllers para contexto e permissões.
- Execução manual documentada do menu, se não houver abstração testável.

**Commit sugerido:** `feat: adiciona login e permissoes no terminal`  
**Commit:** pendente.

### 6. Integrando recrutadores ao gerenciamento de vagas

**Dependências:** tarefas 4 e 5.  
**Objetivo:** permitir que recrutadores gerenciem somente as vagas da empresa
à qual estão vinculados.

**Arquivos/superfícies prováveis:**

- `src/main/groovy/org/alan/dao/VagaDAO.groovy`
- `src/main/groovy/org/alan/service/VagaService.groovy`
- `src/main/groovy/org/alan/controller/VagaController.groovy`
- `src/main/groovy/org/alan/view/VagasMenu.groovy`
- interfaces correspondentes.

**Atividades:**

- Associar as operações de vaga ao contexto da empresa/recrutador.
- Permitir listar e visualizar todas as vagas da empresa, sem filtrar por
  área de atuação.
- Permitir criar, editar e excluir vagas da empresa do recrutador.
- Bloquear acesso, alteração ou exclusão de vaga de outra empresa.
- Preservar o fluxo atual da empresa e os vínculos de competências.

**Critérios de conclusão:**

- Recrutador vê todas as vagas da empresa vinculada.
- Área de atuação é armazenada como informação, mas não restringe vagas.
- Recrutador cria, edita e exclui apenas vagas da empresa vinculada.
- Operações existentes de empresa continuam funcionando.

**Testes:**

- Testes de service/controller para autorização por empresa.
- Testes de DAO para filtros de empresa e operações de vaga.
- Regressão dos testes atuais de `VagaService`.

**Commit sugerido:** `feat: restringe gestao de vagas ao vinculo empresarial`  
**Commit:** pendente.

### 7. Integrando curtidas, responsáveis e matches

**Dependências:** tarefas 3, 4 e 5.  
**Objetivo:** fazer curtidas de recrutadores representarem a empresa e
preservar os responsáveis sem duplicar decisões ou matches.

**Arquivos/superfícies prováveis:**

- `src/main/groovy/org/alan/dao/MatchDAO.groovy`
- `src/main/groovy/org/alan/repository/MatchRepository.groovy`
- `src/main/groovy/org/alan/service/MatchService.groovy`
- `src/main/groovy/org/alan/controller/MatchController.groovy`
- `src/main/groovy/org/alan/view/MatchMenu.groovy`
- `src/main/groovy/org/alan/model/Match.groovy`

**Atividades:**

- Receber o recrutador autenticado ao registrar a curtida.
- Validar vínculo ativo e empresa correspondente.
- Persistir a curtida empresarial uma única vez.
- Registrar todos os recrutadores que participaram da mesma decisão, conforme
  schema aprovado.
- Manter `UNIQUE (id_vaga, id_candidato)` para matches.
- Preservar a regra de match candidato-vaga-empresa.
- Exibir ao candidato os dados da empresa após o match.
- Não expor CPF, nascimento ou senha do recrutador.
- Não exibir o recrutador no match enquanto essa decisão não estiver
  especificada.

**Critérios de conclusão:**

- Curtida de recrutador é reconhecida como curtida da empresa.
- Segunda curtida da mesma empresa não duplica curtida nem match.
- Responsáveis ficam consultáveis segundo o contrato aprovado.
- Match continua único por vaga e candidato.
- Fluxos atuais do candidato continuam funcionando.

**Testes:**

- Testes de service para curtida com vínculo válido/inválido.
- Testes de DAO para consolidação e responsáveis múltiplos.
- Testes de match sem duplicidade.
- Regressão de `MatchServiceSpec`.

**Commit sugerido:** `feat: registra curtidas de recrutadores pela empresa`  
**Commit:** pendente.

### 8. Validando segurança, privacidade e regressões da feature

**Dependências:** tarefas 2, 3, 4, 5, 6 e 7.  
**Objetivo:** validar o comportamento completo do backend e os fluxos locais do
frontend, mantendo as duas aplicações independentes e sem alterar regras fora
da SPEC.

**Atividades:**

- Revisar todos os selects, objetos e saídas do terminal para impedir
  exposição de CPF, nascimento e senha do recrutador.
- Verificar que o match libera dados da empresa conforme contrato.
- Verificar que o login aplica as validações aprovadas.
- Verificar que a limitação atual de armazenamento de senhas está
  explicitamente tratada conforme decisão aprovada.
- Executar e verificar os fluxos locais do frontend para login, navegação,
  vagas, candidatos, curtidas, matches e gestão de recrutadores.
- Confirmar que os fluxos atuais de candidato e empresa continuam funcionando
  no frontend.
- Confirmar que as alterações locais são refletidas nas telas durante a
  sessão, sem chamadas HTTP ou autenticação compartilhada.
- Executar testes backend e corrigir somente falhas causadas pela feature.

**Critérios de conclusão:**

- Critérios de aceite 1 a 21 da SPEC possuem evidência.
- Não há endpoints HTTP novos.
- Os fluxos locais do frontend estão cobertos pelo build TypeScript/Vite e
  pelas verificações funcionais disponíveis.
- Não há regressão na suíte backend existente.

**Testes/verificações:**

```bash
./gradlew test
cd frontend && npm run build
git diff --check
```

Se houver decisão de executar o terminal com PostgreSQL, registrar o comando,
configuração e resultado; não declarar integração validada sem banco
disponível.

**Commit sugerido:** `test: valida feature recrutador`  
**Commit:** pendente.

### 9. Verificando a fronteira de integração sem conectá-la

**Dependências:** tarefa 8.  
**Objetivo:** confirmar que o frontend foi implementado localmente, mas continua
deliberadamente separado do backend.

**Atividades:**

- Confirmar que não foi criado cliente HTTP para recrutadores.
- Registrar a limitação: os dados do frontend não são compartilhados com
  PostgreSQL e os dados do backend não aparecem automaticamente na interface.

**Critérios de conclusão:**

- O frontend local está implementado.
- A ausência de integração é explícita e não é tratada como falha desta etapa.
- Nenhum endpoint novo foi criado.

**Testes/verificações:**

```bash
git status --short
git diff --check
```

**Commit sugerido:** não aplicável; não criar commit sem alteração.

## Matriz de cobertura

| Critério da SPEC | Tarefa principal | Evidência |
|---|---:|---|
| Cadastro, edição e remoção de recrutador | 4, 5 | Testes de service/terminal |
| Vínculo, status e histórico | 3, 4 | SQL e testes de persistência |
| Login simples | 5 | Testes de autenticação e execução terminal |
| Gestão de vagas da empresa | 6 | Testes de autorização e regressão |
| Curtida em nome da empresa | 7 | Testes de service/DAO |
| Responsáveis múltiplos sem duplicidade | 3, 7 | Constraints e testes de match |
| Match atual preservado | 7, 8 | `MatchServiceSpec` e suíte Gradle |
| Privacidade e anonimato | 7, 8 | Revisão de saídas e testes |
| Sem endpoints novos | 2, 5, 8 | Diff de `ApiMain` e handlers |
| Frontend local | 2 | Build TypeScript/Vite e fluxos locais |
| Fluxos atuais de candidato e empresa | 2, 8 | Verificações funcionais locais |
| Atualização das telas durante a sessão | 2, 8 | Verificações funcionais locais |
| Integração frontend-backend excluída | 9 | Nenhum cliente HTTP ou endpoint novo |

## Validação final planejada

Depois que todas as tarefas implementáveis forem concluídas:

1. Executar `./gradlew test`.
2. Executar compilação/build do backend.
3. Executar `cd frontend && npm run build` para validar os fluxos locais
   implementados.
4. Executar `git diff --check`.
5. Revisar todos os commits da feature.
6. Conferir os critérios de aceite da SPEC individualmente.
7. Registrar neste plano os hashes, resultados e limitações finais.

O plano não considera a feature concluída se houver decisão pendente, teste
falhando, critério sem evidência ou alteração fora do escopo.
