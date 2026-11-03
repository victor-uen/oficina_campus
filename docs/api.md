# API local

Base: `http://127.0.0.1:8080/api`. JSON em UTF-8. O servidor padrão é local, sem autenticação. A interface web é servida pelo mesmo processo.

| Método e caminho | Resultado |
|---|---|
| `GET /meta` | Definições das telas e de seus campos, e a data atual do servidor (`agora`) |
| `GET /registros/{modulo}` | Lista com `items`, `total`, `offset` e `limite` |
| `GET /registros/{modulo}/{id}` | Uma ficha com dados, estado, versão e datas |
| `POST /registros/{modulo}` | Cria uma ficha; retorna 201 e cabeçalho Location |
| `PUT /registros/{modulo}/{id}` | Substitui os dados da ficha, exigindo a versão atual |
| `POST /registros/{modulo}/{id}/arquivar` | Arquiva sem excluir os dados |
| `POST /registros/{modulo}/{id}/restaurar` | Restaura conforme a política do módulo |
| `POST /registros/interesses/{id}/aceitar` | Aceita a manifestação e inclui a pessoa como participante da oficina |
| `POST /registros/interesses/{id}/recusar` | Recusa a manifestação |
| `GET /historico` | Até 100 alterações desta execução, em memória |

Conteúdos (materiais, guias, leituras, comunicados e perguntas frequentes) também aceitam as ações `publicar` e `ocultar`.

Manifestações de interesse não são criadas nem editadas pelas rotas da organização: `POST` e `PUT` em `/registros/interesses` retornam 409. Sugestões e atendimentos são somente consulta para a organização: `POST`, `PUT` e as ações de estado em `/registros/sugestoes` e `/registros/atendimentos` retornam 409.

## Portal da comunidade

Rotas usadas pelo perfil de Lucas. Os envios seguem as mesmas validações das rotas da organização.

| Método e caminho | Resultado |
|---|---|
| `GET /public/oficinas` | Somente oficinas ativas |
| `GET /public/vagas` | Vagas ocupadas por oficina (participantes ativos), como `{"id-da-oficina": 3}` |
| `POST /public/interesses` | Envia uma manifestação de interesse; ela começa pendente. Retorna 201 |
| `POST /public/avaliacoes` | Avalia uma oficina já realizada; retorna 201 |
| `POST /public/sugestoes` | Sugere uma oficina; retorna 201 |
| `POST /public/atendimentos` | Envia uma dúvida ou um problema à organização; retorna 201 |
| `GET /public/conteudo` | Materiais, guias, leituras e comunicados publicados (visíveis) |
| `GET /public/faq` | Perguntas frequentes publicadas |

As demais funcionalidades não têm rota pública.

## Exemplo: criar oficina

```http
POST /api/registros/oficinas
Content-Type: application/json

{
  "dados": {
    "titulo": "Introdução à lógica de programação",
    "apresentacao": "Uma oficina prática de primeiros passos.",
    "modalidade": "Presencial",
    "duracao": 120,
    "limite": 20,
    "data": "2026-11-05T14:00",
    "espaco": "a91c...",
    "instrutores": ["5be2...", "c0d4..."]
  }
}
```

`espaco` e `instrutores` recebem os `id` de registros ativos de Espaços e Instrutores; o espaço pode ficar vazio só em oficinas online. O `limite` de oficinas presenciais e híbridas não pode passar da capacidade do espaço (400). Uma oficina ativa no mesmo horário com o mesmo espaço, o mesmo instrutor ou o mesmo equipamento reservado retorna 409 com a mensagem "Conflito de agenda: ...".

## Exemplo: manifestar interesse pelo portal

```http
POST /api/public/interesses
Content-Type: application/json

{
  "dados": {
    "nome": "Lucas Pereira",
    "email": "lucas@example.com",
    "telefone": "(81) 97777-0000",
    "profissao": "Estudante",
    "oficina": "7f3c2d1e-..."
  }
}
```

A situação começa como `Pendente`. Oficina arquivada, já realizada ou sem vagas retorna 409.

## Exemplo: reservar um equipamento

Equipamentos e instituições parceiras guardam a lista `oficinas`. Para reservar, edite o equipamento acrescentando a oficina:

```http
PUT /api/registros/equipamentos/9d1e...
Content-Type: application/json

{
  "dados": {
    "nome": "Projetor portátil",
    "tipo": "Audiovisual",
    "instrucoes": "Conectar o cabo HDMI antes de ligar.",
    "oficinas": ["7f3c...", "b812..."]
  },
  "versao": 2
}
```

Duas oficinas ativas da lista em horários sobrepostos retornam 409.

O campo `oficina` das fichas ligadas recebe o `id` de uma oficina ativa. Oficina inexistente retorna 400; oficina arquivada retorna 409, a menos que a ficha já estivesse ligada a ela.

Para editar, envie o mesmo objeto `dados` com todos os campos e acrescente `"versao"`, usando a versão recebida na última consulta. Para uma ação, envie apenas `{"versao": 0}`. A versão aumenta em cada mudança; uma versão antiga retorna 409.

## Consulta e erros

Exemplo de filtro: `/registros/oficinas?q=logica&estado=ATIVO&offset=0&limite=20`. O parâmetro `oficina={id}` lista as fichas daquela oficina, inclusive equipamentos e parceiras que a incluem: `/registros/kits?oficina=7f3c...`. A pesquisa ignora maiúsculas e minúsculas, mas preserva a distinção entre caracteres acentuados. `limite` deve ser de 1 a 100; o cliente deve percorrer as páginas quando houver mais registros.

- 400: campos, formato, oficina inexistente, limite acima da capacidade ou paginação inválida.
- 404: módulo, registro ou ação inexistente.
- 409: tela somente de consulta, versão antiga, transição incompatível, vínculo novo com oficina arquivada, conflito de agenda, vagas esgotadas, avaliação antes da oficina ou interesse já decidido.

Os erros de negócio retornam uma propriedade `mensagem`. Não existe exclusão definitiva pela API. As regras particulares estão em [requisitos.md](requisitos.md) e nos testes Cucumber.

As rotas são implementadas por controladores e formulários tipados por conceito. Metadados visuais em `/api/meta` não validam o domínio. Números devem ser inteiros JSON; textos numéricos e decimais não são convertidos silenciosamente. Em testes e demonstrações, a propriedade `campus.agora=AAAA-MM-DDTHH:MM` fixa a data usada para saber se uma oficina já aconteceu.
