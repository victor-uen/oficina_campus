# language: pt
# Regras do contexto Recursos.
@luca @recursos
Funcionalidade: Gerenciamento de instituições parceiras
  Como equipe de recursos, quero manter as instituições parceiras e as oficinas que cada uma apoia.

  Contexto:
    Dado o cadastro de "parceiras" com dados válidos

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



  Cenário: Apoiar várias oficinas
    Quando relaciono a ficha a "oficinas no mesmo horário"
    E crio a ficha
    Então a resposta deve ter código 201
    E a ficha deve aparecer na consulta de cada oficina

  Cenário: Manter uma parceira sem oficinas apoiadas
    Quando relaciono a ficha a "nenhuma oficina"
    E crio a ficha
    Então a resposta deve ter código 201

  Cenário: Recusar apoio novo a oficina arquivada
    Quando relaciono a ficha a "uma oficina arquivada"
    E crio a ficha
    Então a resposta deve ter código 409
