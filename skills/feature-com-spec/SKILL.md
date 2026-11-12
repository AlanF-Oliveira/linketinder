---
name: feature-com-spec
description: "Use when implementing any feature with Spec-Driven Development (SDD): understand the project, define and approve a specification, create an executable plan, implement one task at a time, validate behavior, and commit each task separately."
argument-hint: "Descreva a feature e indique o projeto onde ela será implementada"
user-invocable: true
---

# Feature com especificação

## Objetivo

Implementar funcionalidades seguindo um processo de Spec-Driven Development (SDD), garantindo que cada mudança seja planejada, aprovada, implementada incrementalmente e validada.

Fluxo de trabalho:

```text
descrição da feature
    ↓
análise do projeto
    ↓
especificação
    ↓
aprovação da spec
    ↓
plano executável
    ↓
implementação incremental
    ↓
validação por tarefa
    ↓
commit por tarefa
    ↓
validação final
```

O processo deve transformar uma necessidade em comportamento verificável, limitar o escopo e manter um histórico rastreável das alterações.

## Entradas

- **Descrição da feature:** objetivo, comportamento esperado, restrições, usuários afetados e critérios de sucesso conhecidos.
- **Projeto:** repositório, diretório de trabalho, stack, convenções existentes e arquivos relevantes.

Antes de editar qualquer arquivo:

- Leia as instruções do projeto, especialmente `AGENTS.md` e documentos relacionados.
- Identifique a stack e a arquitetura.
- Examine a estrutura e os módulos relevantes.
- Verifique funcionalidades semelhantes já existentes.
- Identifique contratos, regras de negócio e restrições existentes.
- Verifique o estado do Git, a branch atual e alterações não commitadas.

Não presuma integrações, contratos, funcionalidades ou regras de negócio que não estejam demonstrados no código ou na documentação.

Preserve todas as alterações preexistentes. Nunca descarte trabalho não relacionado à feature.

## Processo

### Etapa 1 — Entender o projeto e delimitar a feature

Analise o projeto antes de propor qualquer solução.

Identifique:

- Arquitetura e organização das camadas.
- Tecnologias e dependências utilizadas.
- Entidades, relacionamentos e estruturas de persistência.
- Fluxos e regras de negócio existentes.
- Contratos entre módulos e integrações.
- Testes existentes e convenções de desenvolvimento.
- Restrições de segurança, privacidade e compatibilidade.
- Arquivos e componentes potencialmente afetados.

Apresente um resumo da análise, indicando:

1. Como o projeto funciona atualmente.
2. Quais partes serão afetadas pela feature.
3. Quais funcionalidades existentes precisam ser preservadas.
4. Quais limitações técnicas foram identificadas.
5. Quais dúvidas ou decisões precisam ser esclarecidas.

Não implemente código nesta etapa.

Não invente regras de negócio para preencher lacunas. Apresente as dúvidas ao usuário com opções concretas quando possível.

### Etapa 2 — Criar e aprovar a especificação

Gere ou atualize `docs/spec.md`, ou utilize o local de documentação adotado pelo projeto.

A especificação deve conter:

- Objetivo e contexto.
- Escopo incluído e explicitamente excluído.
- Comportamento atual que deve ser preservado.
- Comportamento pretendido.
- Regras de negócio.
- Contratos de entrada, saída, erros, persistência e compatibilidade.
- Restrições de segurança e privacidade.
- Critérios de aceite mensuráveis.
- Riscos, ambiguidades e decisões que exigem aprovação.
- Estratégia de testes.

Revise a especificação considerando a descrição da feature, o código existente e as instruções do projeto.

Garanta que:

- Cada mudança de comportamento tenha justificativa.
- Cada requisito relevante tenha um critério de aceite verificável.
- As regras existentes estejam preservadas.
- Os requisitos de segurança e privacidade sejam considerados.
- O escopo não inclua funcionalidades não autorizadas.

**Aprovação obrigatória:**

Após elaborar e revisar a especificação:

1. Apresente a spec completa ao usuário.
2. Destaque decisões tomadas, dúvidas e pontos de atenção.
3. Aguarde a aprovação explícita do usuário.
4. Caso sejam solicitadas alterações, atualize a spec e apresente novamente.

Não avance para a criação do plano enquanto a especificação não estiver aprovada.

Não implemente código de produção enquanto houver decisões de contrato ou regras de negócio pendentes.

### Etapa 3 — Criar o plano executável

Após a aprovação da spec, gere ou atualize `docs/plano.md`, ou utilize o local de documentação adotado pelo projeto.

O plano deve referenciar o caminho real da especificação aprovada.

Divida a implementação em tarefas pequenas, ordenadas e independentes o suficiente para revisão.

Para cada tarefa, registre:

- Objetivo.
- Arquivos e superfícies afetados.
- Atividades de implementação.
- Critérios de conclusão.
- Testes e verificações.
- Dependências de outras tarefas.
- Mensagem de commit sugerida.

Organize as tarefas de modo que cada incremento produza um resultado verificável.

Inclua uma regra de bloqueio:

- Não iniciar a próxima tarefa quando a atual falhar.
- Não avançar com decisões pendentes.
- Não prosseguir quando houver alterações fora do escopo aprovado.
- Não considerar uma tarefa concluída sem evidências de validação.

Leia o plano novamente e confirme que ele cobre todos os critérios da spec sem incluir trabalho não autorizado.

### Etapa 4 — Implementar uma tarefa por vez

Execute as tarefas rigorosamente na ordem definida no plano.

Antes de cada tarefa:

1. Marque a tarefa como `em andamento` no plano, se o formato adotado permitir.
2. Releia a tarefa e a especificação aprovada.
3. Examine o diff acumulado.
4. Inspecione as superfícies afetadas e procure padrões existentes.
5. Identifique possíveis impactos em funcionalidades existentes.

Durante a implementação:

- Implemente somente o incremento descrito na tarefa.
- Atualize os testes relacionados.
- Atualize a documentação diretamente relacionada.
- Preserve contratos e comportamentos existentes.
- Respeite as convenções e tecnologias do projeto.
- Evite refatorações ou correções fora do escopo.

Não misture funcionalidades, refatorações ou correções não previstas na tarefa.

Preserve as regras de negócio, os requisitos de segurança, a privacidade e as invariantes arquiteturais identificadas durante a análise.

### Etapa 5 — Revisar e validar a tarefa

Após implementar cada tarefa:

1. Leia o diff completo e explique o que foi alterado.
2. Verifique se todas as alterações pertencem ao escopo aprovado.
3. Execute os testes que cobrem a alteração.
4. Execute o build, compilação e linter aplicáveis.
5. Execute a aplicação ou uma verificação equivalente quando houver fluxo executável.
6. Compare os resultados com a spec e os critérios de aceite.
7. Verifique possíveis regressões em funcionalidades existentes.
8. Corrija falhas antes de prosseguir.
9. Marque a tarefa como concluída no plano, registrando as evidências.

**Registro de evidências:**

Registre os comandos efetivamente executados e seus resultados.

Nunca declare testes, builds, verificações ou validações como aprovados sem tê-los executado.

Caso alguma validação não possa ser realizada:

- Informe o motivo.
- Identifique o impacto.
- Registre a limitação no plano.
- Solicite aprovação antes de considerar a tarefa concluída.

Falhas de teste, comportamento inesperado, ambiguidades ou alterações fora do escopo devem bloquear a próxima tarefa até serem resolvidos ou aprovados explicitamente.

### Etapa 6 — Consolidar um commit por tarefa

Depois que a tarefa passar pela revisão e validação:

1. Execute `git diff --check`.
2. Confira o estado do Git.
3. Revise individualmente os arquivos que serão incluídos.
4. Garanta que alterações preexistentes e arquivos não relacionados não sejam incluídos.
5. Confira os arquivos staged.
6. Crie exatamente um commit para aquela tarefa.
7. Utilize uma mensagem clara e específica.
8. Inclua o trailer de coautoria exigido pelo projeto, quando aplicável.
9. Confirme o commit e o estado da árvore de trabalho.
10. Registre o hash e a mensagem do commit no plano.

Nunca utilize comandos destrutivos para descartar alterações que não pertencem à tarefa.

Somente avance para a próxima tarefa após a conclusão do commit atual.

### Etapa 7 — Validação final e encerramento

Após concluir todas as tarefas:

1. Execute a suíte completa de testes aplicável.
2. Execute o build completo.
3. Verifique os critérios de aceite da especificação.
4. Revise o diff acumulado.
5. Confira o histórico de commits.
6. Verifique se não existem alterações inesperadas.
7. Atualize a documentação com os resultados finais.

Registre na spec e no plano:

- Funcionalidades implementadas.
- Arquivos e componentes alterados.
- Testes executados e respectivos resultados.
- Critérios de aceite atendidos.
- Limitações conhecidas.
- Decisões finais.
- Commits realizados.

Ao finalizar, apresente um resumo objetivo do trabalho, incluindo eventuais pendências e limitações.

Não declare a feature concluída se houver critérios de aceite não atendidos ou validações obrigatórias pendentes.

## Regras obrigatórias

- Não escrever código de produção antes da aprovação explícita da spec.
- Não criar o plano antes da aprovação da especificação.
- Não inventar regras de negócio, contratos ou integrações.
- Implementar uma tarefa por vez.
- Revisar o diff após cada tarefa.
- Executar testes e verificações antes de concluir cada tarefa.
- Registrar evidências reais das validações.
- Fazer exatamente um commit por tarefa concluída.
- Não acumular tarefas diferentes em um único commit.
- Não alterar arquivos, contratos ou comportamentos fora do escopo aprovado.
- Não iniciar a próxima tarefa com testes falhando, decisões pendentes ou alterações não autorizadas.
- Preservar alterações preexistentes e nunca desfazer trabalho não relacionado.
- Respeitar as instruções, convenções e arquitetura do projeto.
- Preservar regras de negócio, segurança, privacidade e compatibilidade.
- Preferir mudanças pequenas, tipadas, testáveis e consistentes com a stack.
- Registrar no plano as evidências de conclusão e os commits correspondentes.
- Comunicar problemas e limitações de forma transparente.

## Modelos

### Estrutura esperada de `spec.md`

```markdown
# Especificação — <nome da feature>

## 1. Objetivo
<problema, resultado esperado e motivação>

## 2. Contexto e comportamento existente
<arquitetura, funcionalidades e contratos relevantes>

## 3. Escopo

### Incluído
- <superfícies e comportamentos incluídos>

### Fora do escopo
- <superfícies e comportamentos excluídos>

## 4. Comportamento pretendido
<cenários, fluxos e regras novas>

## 5. Regras e restrições
- <regras de negócio>
- <segurança e privacidade>
- <compatibilidade e invariantes>

## 6. Contratos e decisões
- Entradas e saídas.
- Erros e validações.
- Persistência.
- Compatibilidade.
- Decisões pendentes.
- Decisões aprovadas e justificativas.

## 7. Critérios de aceite
1. <critério verificável>
2. <critério verificável>

## 8. Estratégia de testes
- Testes unitários.
- Testes de integração.
- Testes de regressão.
- Testes de segurança e privacidade, quando aplicáveis.
- Validação manual, quando necessária.

## 9. Riscos e limitações
- <riscos identificados>

## 10. Aprovação
- Status: Pendente / Aprovada.
- Decisões aprovadas.
```

### Estrutura esperada de `plano.md`

```markdown
# Plano SDD — <nome da feature>

## Referência oficial
Este plano deve ser executado conforme:
[Especificação](<caminho-real-da-spec>)

## Tarefa 1 — <verbo e resultado>

### Status
Pendente / Em andamento / Concluída / Bloqueada

### Objetivo
<incremento>

### Dependências
<tarefas necessárias>

### Arquivos e superfícies
- <arquivos, módulos ou endpoints>

### Atividades
- <passos limitados ao escopo>

### Critérios de conclusão
- <resultado verificável>

### Testes e verificações
- <comandos e cenários>

### Evidências
- <comandos executados e resultados>

### Commit
- Mensagem: <tipo>: <mensagem>
- Hash: <hash do commit>

## Tarefa N — ...

## Ordem e regra de bloqueio

Tarefa 1 → Tarefa 2 → ... → Tarefa N

Não iniciar uma tarefa posterior quando a anterior falhar,
houver decisão pendente ou o diff indicar alteração fora
do escopo aprovado.

## Validação final

- Testes:
- Build:
- Critérios de aceite:
- Limitações:
- Resultado:
```