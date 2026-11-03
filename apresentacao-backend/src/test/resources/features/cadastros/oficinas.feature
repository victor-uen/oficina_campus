# language: pt
# Regras do contexto Cadastros.
@artur @cadastros
Funcionalidade: Gerenciamento de oficinas
  Como organizadora, quero manter oficinas com data, local, instrutores e limite de participantes, sem conflito de agenda, para planejar a programação.

  Contexto:
    Dado o cadastro de "oficinas" com dados válidos

  Cenário: Criar, corrigir, arquivar e recuperar sem perder os dados
    Quando crio a ficha
    Então a resposta deve ter código 201
    E o estado deve ser "ATIVO"
    E a ficha deve continuar disponível para consulta
    Quando altero um campo com informação válida
    Então a resposta deve ter código 200
    Quando solicito "arquivar"
    Então a resposta deve ter código 200
    E o estado deve ser "ARQUIVADO"
    E a ficha deve continuar disponível para consulta
    Quando solicito "restaurar"
    Então a resposta deve ter código 200
    E o estado deve ser "ATIVO"
    E os dados da ficha devem ser preservados




  Cenário: Rejeitar campos obrigatórios vazios sem persistir
    Dado a quantidade de fichas antes da tentativa
    Quando deixo um campo obrigatório vazio
    E crio a ficha
    Então a resposta deve ter código 400
    E nenhuma ficha deve ser adicionada




  Cenário: Respeitar a regra de edição de registros arquivados
    Quando crio a ficha
    E solicito "arquivar"
    E altero um campo com informação válida
    Então a resposta deve ter código 409
    E o estado deve ser "ARQUIVADO"
    E a ficha deve continuar disponível para consulta




  Cenário: Impedir perda de alterações por versão desatualizada
    Quando crio a ficha
    Dado uma cópia anterior da versão
    Quando altero um campo com informação válida
    E tento sobrescrever usando a versão anterior
    Então a resposta deve ter código 409
    E a ficha deve continuar disponível para consulta
    E os dados da ficha devem ser preservados




  Cenário: Mostrar no portal da comunidade somente oficinas ativas
    Quando crio a ficha
    Então a oficina "deve" aparecer no portal da comunidade
    Quando solicito "arquivar"
    Então a oficina "não deve" aparecer no portal da comunidade
    Quando solicito "restaurar"
    Então a oficina "deve" aparecer no portal da comunidade




  Cenário: Exigir espaço para oficinas presenciais e híbridas
    Dado a quantidade de fichas antes da tentativa
    Quando informo o campo "espaco" com ""
    E informo o campo "modalidade" com "Presencial"
    E crio a ficha
    Então a resposta deve ter código 400
    E nenhuma ficha deve ser adicionada
    Quando informo o campo "modalidade" com "Online"
    E crio a ficha
    Então a resposta deve ter código 201




  Cenário: Recusar data e horário inválidos
    Quando informo o campo "data" com "2026-02-31T10:00"
    E crio a ficha
    Então a resposta deve ter código 400




  Cenário: Recusar instrutor que não está no cadastro
    Quando informo um instrutor inexistente
    E crio a ficha
    Então a resposta deve ter código 400




  Cenário: Recusar instrutor arquivado
    Dado que o instrutor escolhido está arquivado
    Quando crio a ficha
    Então a resposta deve ter código 409




  Cenário: Recusar espaço arquivado
    Dado que o espaço escolhido está arquivado
    Quando crio a ficha
    Então a resposta deve ter código 409




  Cenário: Manter o instrutor quando ele é arquivado depois
    Quando crio a ficha
    E o instrutor da oficina é arquivado
    E altero um campo com informação válida
    Então a resposta deve ter código 200
    E os dados da ficha devem ser preservados



  Cenário: Exigir o limite de participantes
    Dado a quantidade de fichas antes da tentativa
    Quando deixo o campo "limite" vazio
    E crio a ficha
    Então a resposta deve ter código 400
    E nenhuma ficha deve ser adicionada

  Cenário: Não passar da capacidade do espaço
    Quando informo um limite acima da capacidade do espaço
    E crio a ficha
    Então a resposta deve ter código 400
    Quando informo o campo "modalidade" com "Online"
    E crio a ficha
    Então a resposta deve ter código 201

  Cenário: Recusar o mesmo espaço no mesmo horário
    Dado que já existe outra oficina "no mesmo espaço" e horário
    Quando crio a ficha
    Então a resposta deve ter código 409
    E a mensagem deve começar com "Conflito de agenda"

  Cenário: Recusar o mesmo instrutor no mesmo horário
    Dado que já existe outra oficina "com o mesmo instrutor" e horário
    Quando crio a ficha
    Então a resposta deve ter código 409
    E a mensagem deve começar com "Conflito de agenda"

  Cenário: Aceitar o mesmo espaço em outro horário
    Dado que já existe outra oficina "no mesmo espaço" e horário
    Quando informo o campo "data" com "2026-11-05T16:00"
    E crio a ficha
    Então a resposta deve ter código 201
