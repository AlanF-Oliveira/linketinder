# Especificação — Recrutador

**Status:** aguardando aprovação  
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
12. Um login simples para candidato, empresa e recrutador faz parte da direção
    aprovada para a feature.
13. As validações seguirão uma política simples.
14. Recrutadores removidos serão excluídos.
15. O cadastro de recrutadores será realizado pela empresa.
16. Nenhum endpoint novo será criado nesta etapa.
17. O terminal continuará sendo a superfície de uso do backend.
18. A implementação do frontend será feita em etapa posterior e separada.

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
- Atualização do script oficial de banco em
  `src/main/groovy/org/alan/database`.

### Explicitamente excluído

- Novos endpoints HTTP.
- Integração do frontend TypeScript com o backend.
- Implementação de telas frontend para recrutadores.
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
- O frontend permanece isolado e baseado em memória.

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

O significado exato dos status e o formato do histórico ainda precisam ser
aprovados.

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
10. A exclusão de um recrutador deve encerrar o vínculo e remover seu cadastro,
    mas o efeito sobre o histórico de curtidas e matches precisa ser definido.

## 7. Login, permissões e autorização

Foi aprovada a direção de criar um login simples para candidato, empresa e
recrutador, mas ainda não foi definido o contrato operacional. A SPEC precisa
ser aprovada com as seguintes decisões:

- quais identificadores serão aceitos no login: email, CPF/CNPJ ou combinação;
- como o terminal representa a sessão ou usuário autenticado;
- como uma empresa é identificada ao cadastrar ou operar recrutadores;
- quais operações são permitidas a cada tipo de usuário;
- se o recrutador pode agir somente quando o vínculo está ativo;
- se a empresa pode consultar e administrar apenas seus próprios
  recrutadores;
- como falhas de login são informadas.

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

O candidato verá os dados da empresa após o match. Ainda precisa ser aprovado
se o candidato verá algum dado profissional do recrutador responsável. A
decisão atual somente confirma a exposição da empresa.

O projeto atualmente armazena senhas em texto puro e não possui hash. Essa
limitação deve ser registrada na implementação do login e não pode ser
ocultada; a política de segurança para novas credenciais ainda requer decisão
do Tutor.

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

Não será alterado nesta etapa. O frontend continua separado, em memória e sem
integração com a API.

## 11. Critérios de aceite propostos

Os critérios abaixo precisam ser confirmados junto com as decisões pendentes:

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
14. O login simples funciona no terminal conforme o contrato aprovado.
15. Não são criados endpoints HTTP novos.
16. O frontend não é alterado nesta etapa.

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

Não serão adicionados testes de frontend ou de integração HTTP nesta etapa,
porque essas superfícies estão fora do escopo confirmado.

## 13. Riscos, limitações e decisões pendentes

### Decisões pendentes antes da aprovação

- Lista exata de status do vínculo e transições permitidas.
- Formato do histórico: eventos, datas, motivo e responsável.
- Regra para exclusão do recrutador e preservação/anonymização do histórico de
  curtidas e matches.
- Identificador e fluxo do login simples.
- Política exata de validação de email, CPF, nascimento e senha.
- Política de hash: manter a limitação atual ou tratar credenciais novas de
  forma diferente.
- Dados profissionais do recrutador que podem ser exibidos após o match.
- Se a empresa pode consultar o histórico de recrutadores removidos.
- Se um recrutador removido deixa de aparecer como responsável histórico ou é
  representado por registro anonimizado.

### Limitações técnicas conhecidas

- O frontend não conversa com o backend.
- A API não possui autenticação nem endpoints de curtidas, matches ou
  recrutadores.
- O terminal atual recebe ids manualmente e não possui sessão.
- Senhas de candidatos e empresas são armazenadas e recuperadas em texto puro,
  sem mecanismo de hash.
- Não há testes de integração com PostgreSQL, API ou frontend.

## 14. Aprovação necessária

Esta especificação não deve avançar para o plano ou para a implementação
enquanto as decisões pendentes não forem confirmadas. Após a aprovação
explícita da SPEC, será criado um plano executável separado, com tarefas,
dependências, testes e critérios de conclusão.
