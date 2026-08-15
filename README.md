# Linketinder

**Autor:** Alan Ferreira Oliveira

Aplicação backend de recrutamento inspirada no Linkedin e no Tinder, feita em Groovy.

## Tecnologias

- Groovy 4
- Gradle

## Como executar

```bash
git clone https://github.com/AlanF-Oliveira/linketinder.git
cd linketinder
./gradlew run
```

Ou rode a classe `org.alan.Terminal.Menu` diretamente pela IDE.

## Sobre a solução

O projeto é organizado em pacotes (`Model`, `usuarios`, `Terminal`). Candidatos e empresas compartilham atributos comuns (nome, e-mail, CEP, estado e descrição) através da interface `Pessoa` e da classe abstrata `Usuario`, da qual `Candidato` e `Empresa` herdam. Cada candidato tem CPF, idade e uma lista de competências; cada empresa tem CNPJ, país e uma lista de competências desejadas.

O sistema mantém 5 candidatos e 5 empresas pré-cadastrados em memória, e o menu no terminal permite listar todos os candidatos e todas as empresas.