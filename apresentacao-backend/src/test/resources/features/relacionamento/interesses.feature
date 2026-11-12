# language: pt
# Regras do contexto Relacionamento.
@ricardo @relacionamento
Funcionalidade: Gerenciamento de manifestações de interesse
  Como aluno, quero manifestar interesse em uma oficina pelo portal, informando meus contatos, para ser incluído como participante.

  Contexto:
    Dado o cadastro de "interesses" com dados válidos

  Cenário: Receber pelo portal e começar pendente
    Quando envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 201
    E a situação deve ser "Pendente"
    E a ficha deve continuar disponível para consulta

  Cenário: A organização não cadastra nem edita manifestações
    Dado a quantidade de fichas antes da tentativa
    Quando crio a ficha
    Então a resposta deve ter código 409
    E nenhuma ficha deve ser adicionada
    Quando envio a ficha pelo portal da comunidade
    E altero um campo com informação válida
    Então a resposta deve ter código 409

  Cenário: Aceitar inclui a pessoa como participante da oficina
    Quando envio a ficha pelo portal da comunidade
    E solicito "aceitar"
    Então a resposta deve ter código 200
    E a situação deve ser "Aceita"
    E a oficina deve ter 1 participantes
    Quando solicito "aceitar"
    Então a resposta deve ter código 409

  Cenário: Recusar não inclui participante
    Quando envio a ficha pelo portal da comunidade
    E solicito "recusar"
    Então a resposta deve ter código 200
    E a situação deve ser "Recusada"
    E a oficina deve ter 0 participantes
    Quando solicito "aceitar"
    Então a resposta deve ter código 409

  Cenário: Arquivar antes de decidir
    Quando envio a ficha pelo portal da comunidade
    E solicito "arquivar"
    Então o estado deve ser "ARQUIVADO"
    Quando solicito "aceitar"
    Então a resposta deve ter código 409
    Quando solicito "restaurar"
    E solicito "aceitar"
    Então a resposta deve ter código 200

  Cenário: Rejeitar campos obrigatórios vazios sem persistir
    Dado a quantidade de fichas antes da tentativa
    Quando deixo um campo obrigatório vazio
    E envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 400
    E nenhuma ficha deve ser adicionada

  Cenário: Recusar contato sem formato de e-mail
    Quando informo o campo "email" com "contato-sem-arroba"
    E envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 400

  Cenário: Recusar telefone com letras
    Quando informo o campo "telefone" com "(81) abcd-efgh"
    E envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 400

  Cenário: Recusar interesse em oficina lotada
    Dado que a oficina escolhida já está lotada
    Quando envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 409
    E a mensagem deve começar com "Vagas esgotadas"

  Cenário: Recusar interesse em oficina que já aconteceu
    Dado que a oficina escolhida já aconteceu
    Quando envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 409

  Cenário: Exigir a oficina de interesse
    Dado a quantidade de fichas antes da tentativa
    Quando deixo a oficina em branco
    E envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 400
    E nenhuma ficha deve ser adicionada

  Cenário: Recusar interesse em oficina arquivada
    Dado que a oficina escolhida está arquivada
    Quando envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 409
