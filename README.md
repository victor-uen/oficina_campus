# Oficina Campus

Projeto de Requisitos de Software e FCCPD.

Equipe: Artur Antunes, Luca, Ricardo, Victor e João Cláudio.
Partes integradas nesta etapa: Cadastros, Recursos (7 funcionalidades e o calendário).

A oficina é o centro do sistema: as fichas dos demais contextos se ligam a ela e
aparecem nas abas da página da oficina.
Use o seletor "Ver como" no topo para alternar entre Marina (organização),
Beatriz (apoio) e Lucas (portal da comunidade).

## Executar

Com JDK 17+ e Maven 3.6.3+, na raiz:

```sh
mvn verify
java -jar apresentacao-backend/target/oficina-campus.jar
```

Abra http://127.0.0.1:8080. O banco H2 fica em `dados/`.
`prototipo.html` contém a demonstração com dados fictícios no navegador.

## Material

- [Domínio](docs/dominio.md)
- [Requisitos](docs/requisitos.md)
- [API](docs/api.md)
- [Modelo CML](oficina-campus.cml)
- [Desenvolvimento e apoio de IA](docs/interacoes/desenvolvimento.md)

A estrutura segue https://github.com/profsauloaraujo/sgb-2025-01.
O grupo trabalha na branch `main`. Atualize com `git pull --ff-only` antes de
iniciar uma alteração e teste a parte antes de enviar.
