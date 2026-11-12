# language: pt
# Regras do contexto Relacionamento.
@ricardo @relacionamento
Funcionalidade: Gerenciamento de atendimentos
  Como aluno, quero enviar pelo portal uma dúvida ou um problema para a organização.

  Contexto:
    Dado o cadastro de "atendimentos" com dados válidos

  Cenário: Receber pelo portal da comunidade
    Quando envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 201
    E o estado deve ser "ATIVO"
    E a ficha deve continuar disponível para consulta

  Cenário: A organização apenas consulta
    Dado a quantidade de fichas antes da tentativa
    Quando crio a ficha
    Então a resposta deve ter código 409
    E nenhuma ficha deve ser adicionada
    Quando envio a ficha pelo portal da comunidade
    E altero um campo com informação válida
    Então a resposta deve ter código 409
    Quando solicito "arquivar"
    Então a resposta deve ter código 409
    E o estado deve ser "ATIVO"
    E a ficha deve continuar disponível para consulta

  Cenário: Rejeitar campos obrigatórios vazios sem persistir
    Dado a quantidade de fichas antes da tentativa
    Quando deixo um campo obrigatório vazio
    E envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 400
    E nenhuma ficha deve ser adicionada
