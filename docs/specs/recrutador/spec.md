# Especificação — Recrutador

**Status:** aprovada para implementação após conclusão da Task 1
**Localização:** `docs/specs/recrutador/spec.md`  
**Branch:** `feature/recrutador`  
**Escopo desta etapa:** especificação da feature; nenhuma implementação está
incluída neste documento.

## 1. Objetivo e contexto

Adicionar o tipo de usuário **recrutador** ao Linketinder. O recrutador será um
colaborador de uma empresa e poderá analisar candidatos e vagas, registrar
curtidas em nome da empresa e administrar vagas da empresa à qual está
vinculado.

A feature deve preservar o fluxo atual de candidatos, empresas, vagas e
matches, incorporando a identidade do recrutador responsável sem transformar a
curtida em uma curtida independente da empresa.

## 2. Decisões de negócio confirmadas

As decisões abaixo foram informadas durante o levantamento:

1. Cada recrutador pertence a uma empresa.
2. A empresa cadastra, edita e remove seus recrutadores.
3. O vínculo entre recrutador e empresa possui status e histórico.
4. A curtida realizada pelo recrutador é uma curtida da empresa.
5. O recrutador que realizou a curtida deve ser armazenado.
6. Duas curtidas de recrutadores da mesma empresa para o mesmo candidato devem
   ser tratadas como uma única curtida empresarial, mantendo os nomes dos
   recrutadores que curtiram.
7. Após o match, o candidato verá os dados da empresa.
8. A área de atuação do recrutador é informativa e não restringe o acesso às
   vagas.
9. O recrutador poderá ver todas as vagas da empresa à qual está vinculado.
10. O recrutador poderá criar, editar e excluir vagas da empresa à qual está
    vinculado.
11. Após o match, o anonimato deixa de ser aplicado entre candidato e empresa.
12. O CPF identifica o candidato e o recrutador no login; o CNPJ identifica a
    empresa. A senha é definida no cadastro e as credenciais devem ser
    validadas.
13. Não pode existir o mesmo CPF cadastrado simultaneamente como candidato e
    recrutador. Essa regra também vale para os dados locais do frontend.
14. As validações seguirão uma política estrutural: campos obrigatórios,
    formato básico e limites de tamanho.
15. Recrutadores removidos serão excluídos.
16. O cadastro de recrutadores será realizado pela empresa.
17. Nenhum endpoint novo será criado nesta etapa.
18. O terminal continuará sendo a superfície de uso do backend.
19. O frontend também será implementado nesta feature, mas permanecerá sem
    integração com o backend.

## 3. Escopo incluído

### Incluído

- Modelo de recrutador no backend.
- Persistência do recrutador e do vínculo com a empresa.
- Status e histórico do vínculo recrutador-empresa.
- Cadastro, edição e remoção de recrutadores pela empresa no terminal.
- Login simples para os tipos de usuário definidos, conforme contrato a ser
  aprovado.
- Acesso do recrutador às vagas da empresa vinculada.
- Criação, edição e exclusão de vagas pelo recrutador, limitada à empresa do
  vínculo ativo.
- Registro do recrutador responsável por uma curtida empresarial.
- Consolidação de curtidas da mesma empresa para o mesmo candidato.
- Preservação do match entre candidato, vaga e empresa.
- Testes backend necessários para os comportamentos incluídos.
- Modelo, service, telas e fluxos locais do recrutador no frontend TypeScript.
- Experiência local de login, área/painel do recrutador, vagas, candidatos,
  curtidas, matches e gestão de recrutadores pela empresa, usando os models,
  services, dados e views existentes.
- Atualização do script oficial de banco em
  `src/main/groovy/org/alan/database`.

### Fora do escopo de integração

- A integração frontend-backend: chamadas HTTP, consumo da API Groovy,
  persistência compartilhada no PostgreSQL e sincronização entre as duas
  aplicações.
- Novos endpoints HTTP para suportar essa integração.

As seguintes restrições de domínio continuam válidas e não representam
exclusões de implementação:

- Rejeições, comentários ou justificativas de curtidas, pois não existem no
  fluxo atual e não foram solicitados.
- Atuação de um recrutador em múltiplas empresas.
- Vagas de outras empresas.

## 4. Comportamento atual a preservar

O backend atual possui as seguintes regras que devem continuar funcionando:

- Candidatos curtem vagas por `id_candidato` e `id_vaga`.
- Empresas curtem candidatos por `id_empresa` e `id_candidato`.
- O match é criado quando a curtida do candidato corresponde a uma vaga da
  empresa que curtiu esse candidato.
- Curtidas repetidas não criam relações duplicadas.
- `matches` possui unicidade por `(id_vaga, id_candidato)`.
- O terminal possui menus separados para candidatos, empresas, vagas,
  competências e matches.
- Vagas pertencem a uma empresa.
- A API atual permanece sem novos endpoints nesta feature.
- O frontend e o backend serão implementados nesta feature, mas permanecerão
  independentes: o frontend continuará baseado em memória e não consumirá a
  API.

## 5. Modelo de domínio pretendido

### Recrutador

Campos confirmados:

- identificador;
- nome;
- área de recrutamento;
- descrição;
- e-mail;
- CPF;
- data de nascimento;
- senha;
- empresa vinculada;
- situação do vínculo.

O documento não define formato, tamanho ou obrigatoriedade de campos além do
que for aprovado na política de validação.

### Vínculo

O vínculo deve representar a associação entre um recrutador e uma empresa,
contendo pelo menos:

- recrutador;
- empresa;
- status;
- informação histórica de alterações do vínculo.

O vínculo usará os status `ATIVO` e `INATIVO`, permitindo transições livres
entre eles. A remoção exclui o cadastro do recrutador e preserva a autoria
histórica em registro separado.

Cada evento histórico registrará status anterior, novo status, data e
responsável.

### Curtida empresarial

A curtida continua representando o interesse da empresa, mas deve registrar
também quem a realizou. Para duas ou mais curtidas da mesma empresa no mesmo
candidato:

- a decisão empresarial permanece única;
- o match não deve ser duplicado por causa de recrutadores diferentes;
- os recrutadores responsáveis devem ser preservados conforme o modelo
  histórico aprovado.

## 6. Regras de negócio propostas para aprovação

As regras abaixo traduzem as decisões confirmadas em comportamento
verificável, mas os detalhes indicados como pendentes ainda não foram
aprovados:

1. Um recrutador só pode ter um vínculo empresarial ativo por vez.
2. Uma empresa só pode cadastrar, editar ou remover recrutadores associados a
   ela.
3. Um recrutador só pode consultar e administrar vagas pertencentes à empresa
   do seu vínculo ativo.
4. A área de atuação deve ser armazenada, mas não deve filtrar vagas ou
   candidatos.
5. Uma curtida feita por um recrutador deve resultar em interesse da empresa.
6. Uma segunda curtida de outro recrutador da mesma empresa para o mesmo
   candidato não deve criar uma nova curtida empresarial nem um novo match.
7. O sistema deve preservar a identificação dos recrutadores que participaram
   da decisão, sem exibir seus dados pessoais indevidos.
8. O match continua sendo definido por candidato, vaga e empresa.
9. Após o match, os dados liberados atualmente para a empresa e o candidato
   continuam disponíveis conforme regra aprovada; o recrutador não deve ser
   tratado como uma nova parte empresarial no resultado sem decisão específica.
10. A exclusão de um recrutador deve encerrar o vínculo e remover seu cadastro.
    A identificação histórica será preservada em registro separado com ID,
    nome e área congelados, sem credenciais ou acesso operacional.

## 7. Login, permissões e autorização

O contrato de identificação foi aprovado:

- candidato entra com CPF e senha;
- empresa entra com CNPJ e senha;
- recrutador entra com CPF e senha;
- a senha é definida no cadastro;
- as credenciais devem ser validadas;
- o usuário não pode escolher um tipo de conta incompatível com suas
  credenciais;
- o recrutador só acessa funcionalidades protegidas enquanto possuir vínculo
  ativo com uma empresa.
- o mesmo CPF não pode existir simultaneamente nos cadastros de candidato e
  recrutador. O login não depende de escolha livre do tipo de conta e rejeita
  credenciais incompatíveis.

Existem dois fluxos independentes:

- **Backend:** login pelo terminal, com autenticação, sessão e permissões.
- **Frontend:** login local usando os dados e services existentes no próprio
  frontend, também com candidato usando CPF + senha, empresa usando CNPJ +
  senha e recrutador usando CPF + senha. O frontend identifica o tipo de
  usuário durante a sessão e direciona para a área correspondente.

O fluxo do frontend é exclusivamente local. Não realiza chamadas HTTP,
integração ou autenticação compartilhada com o backend, nem deve simular
integração ou autenticação real com ele.

O frontend e o backend devem validar as credenciais contra o cadastro
correspondente e impedir acesso a uma conta incompatível. O frontend mantém
essa validação apenas localmente.

O login não deve criar endpoints HTTP nesta etapa.

## 8. Anonimato e privacidade

Antes do match, a implementação deve preservar o princípio de anonimato já
adotado nas listagens do frontend e as restrições de exposição existentes no
backend. Após o match, o anonimato entre candidato e empresa deixa de existir,
conforme decisão confirmada.

Devem permanecer privados, salvo decisão explícita posterior:

- CPF do recrutador;
- data de nascimento do recrutador;
- senha do recrutador;
- senha de qualquer usuário;
- documentos pessoais de candidato ou empresa.

O candidato verá somente os dados da empresa após o match. Nenhum dado
profissional ou pessoal do recrutador será exibido como parte do match.

O projeto atualmente armazena senhas em texto puro e não possui hash. Essa
limitação de segurança conhecida será mantida temporariamente nesta feature e
deve ser explicitamente registrada na implementação do login.

## 9. Persistência e banco

O script oficial é
`src/main/groovy/org/alan/database/linketinder.sql`. A pasta `banco/` não deve
ser usada como fonte.

As alterações de banco dependerão das decisões finais, mas devem representar:

- tabela de recrutadores;
- vínculo com empresa;
- status do vínculo;
- histórico do vínculo;
- referência do recrutador nas curtidas empresariais;
- forma de manter múltiplos responsáveis sem duplicar a curtida;
- regras de exclusão e integridade referencial;
- unicidades e índices necessários.

Não deve ser criada uma curtida empresarial duplicada apenas para representar
dois recrutadores. A solução de histórico/responsáveis deve preservar a
unicidade da decisão empresarial e o vínculo com os recrutadores.

## 10. Superfícies afetadas

### Backend Groovy

Possíveis superfícies, condicionadas ao contrato aprovado:

- `model/Recrutador.groovy`;
- `repository/RecrutadorRepository.groovy`;
- `dao/RecrutadorDAO.groovy`;
- `service/RecrutadorService.groovy`;
- `controller/RecrutadorController.groovy`;
- menus de empresa, recrutador, vagas e login;
- `Menu.groovy`;
- `MatchDAO`, `MatchRepository`, `MatchService` e `MatchController`;
- `VagaDAO`, `VagaService` e `VagaController`;
- `src/main/groovy/org/alan/database/linketinder.sql`;
- testes Spock correspondentes.

### API

Não haverá novos endpoints. A composição em `ApiMain.groovy` não deve ser
expandida para rotas de recrutador nesta etapa.

### Frontend

Será implementado nesta etapa de forma local e independente do backend,
reaproveitando os modelos, services, dados e views atuais. O fluxo deve ser
funcional dentro da sessão do navegador: login local, painel do recrutador,
visualização e CRUD local de vagas, listagem de candidatos, curtidas, matches e
gestão local de recrutadores pela empresa. Não haverá chamadas HTTP, consumo
da API Groovy, PostgreSQL ou sincronização com o backend.

## 11. Critérios de aceite

Os critérios abaixo serão validados durante a implementação:

1. Uma empresa consegue cadastrar um recrutador vinculado a ela pelo
   terminal.
2. O recrutador persistido possui os campos definidos no modelo aprovado.
3. A empresa consegue editar e remover seus próprios recrutadores.
4. O sistema registra status e histórico do vínculo.
5. Um recrutador com vínculo ativo consegue acessar vagas da sua empresa.
6. Um recrutador não consegue criar, editar ou excluir vaga de outra empresa.
7. Um recrutador pode criar, editar e excluir vagas de sua empresa conforme as
   permissões aprovadas.
8. Uma curtida do recrutador é persistida como curtida da empresa.
9. Duas curtidas de recrutadores da mesma empresa para o mesmo candidato não
   duplicam a curtida empresarial nem o match.
10. O histórico identifica os recrutadores responsáveis conforme o modelo
    aprovado.
11. O match continua sendo criado uma única vez para a combinação de vaga e
    candidato.
12. O candidato vê os dados liberados da empresa após o match.
13. CPF, nascimento e senha do recrutador não aparecem em listagens ou
    matches.
14. O login por CPF/CNPJ e senha funciona no terminal conforme o contrato
    aprovado, com sessão até logout ou encerramento do processo e bloqueio de
    credenciais incompatíveis.
15. Não são criados endpoints HTTP novos.
16. O frontend possui modelo, service, telas e fluxos locais para recrutadores,
    incluindo login, painel, vagas, candidatos, curtidas, matches e gestão de
    recrutadores pela empresa, sem chamadas à API do backend.
17. Os fluxos atuais de candidato e empresa continuam funcionando no frontend.
18. O recrutador visualiza e gerencia somente vagas da empresa de seu vínculo
    ativo.
19. A anonimização dos candidatos é preservada antes do match.
20. Após o match, as telas apresentam os dados conforme as regras de
    visibilidade aprovadas.
21. Alterações feitas localmente são refletidas nas telas durante a sessão.

## 12. Estratégia de testes

Os testes devem usar Spock e seguir a organização existente em
`src/test/groovy`, preferindo stubs de repositories nos services e mocks da
fábrica/conexão nos DAOs quando não for necessário banco real.

Devem ser cobertos, no mínimo:

- cadastro, edição, busca e remoção de recrutador;
- vínculo com empresa e transições de status;
- preservação do histórico;
- autorização de empresa sobre seus recrutadores;
- login e falhas de login;
- acesso do recrutador às vagas da empresa;
- bloqueio de operações em vagas de outra empresa;
- curtida empresarial realizada por recrutador;
- consolidação de duas curtidas da mesma empresa;
- criação única de match;
- privacidade dos dados do recrutador;
- validações aprovadas.
- build TypeScript do frontend após a inclusão dos fluxos locais.
- fluxo local de login e navegação para as áreas autorizadas;
- painel do recrutador com listagem de vagas da empresa;
- CRUD local de vagas respeitando a empresa vinculada;
- listagem/visualização local de candidatos com anonimização antes do match;
- curtida local em nome da empresa e consolidação de curtidas;
- visualização local de matches;
- CRUD local de recrutadores pela empresa.

Não serão adicionados testes de integração HTTP nesta etapa. O frontend será
validado pelo build TypeScript/Vite e, se houver testes frontend adicionados
pela implementação, por seu runner correspondente.

## 13. Riscos, limitações e decisões pendentes

### Contratos definidos na Task 1

- Status `ATIVO` e `INATIVO`, com transições livres entre eles.
- Histórico com status anterior, novo status, data e responsável.
- Registro histórico separado com ID, nome e área congelados após exclusão.
- CPF exclusivo entre candidatos e recrutadores.
- Validação estrutural com campos obrigatórios, formato básico e limites de
  tamanho.
- Sessão em memória até logout explícito ou encerramento do processo.
- Senhas mantidas temporariamente em texto puro, sem hash.
- Após o match, somente os dados da empresa são exibidos.

### Limitações técnicas conhecidas

- O frontend não conversa com o backend; seus fluxos locais não persistem no
  PostgreSQL nem compartilham estado com a aplicação Groovy.
- A API não possui autenticação nem endpoints de curtidas, matches ou
  recrutadores.
- O terminal atual recebe ids manualmente e não possui sessão.
- Senhas de candidatos e empresas são armazenadas e recuperadas em texto puro,
  sem mecanismo de hash.
- Não há testes de integração com PostgreSQL, API ou entre frontend e backend;
  o frontend será validado localmente pelo build TypeScript/Vite e pelos testes
  frontend que forem adicionados.

## 14. Aprovação necessária

Esta revisão altera o escopo frontend da SPEC anteriormente aprovada e precisa
de nova aprovação explícita antes da implementação. Após essa aprovação, o
plano executável deve incluir todas as telas e os fluxos locais TypeScript,
mantendo exclusivamente a integração com o backend fora do escopo.
