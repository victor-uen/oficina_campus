# language: pt
# Regras do contexto Recursos.
@luca @recursos
Funcionalidade: Gerenciamento de equipamentos
  Como equipe de recursos, quero manter os equipamentos da instituição e reservá-los para as oficinas, sem dois usos no mesmo horário.

  Contexto:
    Dado o cadastro de "equipamentos" com dados válidos

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




  Cenário: Manter um equipamento geral, sem reservas
    Quando relaciono a ficha a "nenhuma oficina"
    E crio a ficha
    Então a resposta deve ter código 201

  Cenário: Reservar para oficinas em dias diferentes
    Quando relaciono a ficha a "oficinas em dias diferentes"
    E crio a ficha
    Então a resposta deve ter código 201
    E a ficha deve aparecer na consulta de cada oficina

  Cenário: Recusar reserva de duas oficinas no mesmo horário
    Dado a quantidade de fichas antes da tentativa
    Quando relaciono a ficha a "oficinas no mesmo horário"
    E crio a ficha
    Então a resposta deve ter código 409
    E a mensagem deve começar com "Conflito de agenda"
    E nenhuma ficha deve ser adicionada

  Cenário: Recusar reserva para oficina inexistente
    Quando relaciono a ficha a "uma oficina inexistente"
    E crio a ficha
    Então a resposta deve ter código 400

  Cenário: Recusar reserva nova para oficina arquivada
    Quando relaciono a ficha a "uma oficina arquivada"
    E crio a ficha
    Então a resposta deve ter código 409
