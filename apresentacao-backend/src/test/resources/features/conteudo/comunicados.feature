# language: pt
# Regras do contexto Conteúdo.
@victor @conteudo
Funcionalidade: Gerenciamento de comunicados
  Como organizadora de conteúdo, quero publicar comunicados no mural para compartilhar informações gerais.

  Contexto:
    Dado o cadastro de "comunicados" com dados válidos

  Cenário: Criar, corrigir, arquivar e recuperar sem perder os dados
    Quando crio a ficha
    Então a resposta deve ter código 201
    E o estado deve ser "OCULTO"
    E a ficha deve continuar disponível para consulta
    Quando altero um campo com informação válida
    Então a resposta deve ter código 200
    Quando solicito "arquivar"
    Então a resposta deve ter código 200
    E o estado deve ser "ARQUIVADO"
    E a ficha deve continuar disponível para consulta
    Quando solicito "restaurar"
    Então a resposta deve ter código 200
    E o estado deve ser "OCULTO"
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




  Cenário: Controlar publicação e restaurar conteúdo como oculto
    Quando crio a ficha
    Então a ficha "não deve" aparecer na consulta pública
    Quando solicito "publicar"
    Então a resposta deve ter código 200
    E a ficha "deve" aparecer na consulta pública
    Quando solicito "ocultar"
    Então a ficha "não deve" aparecer na consulta pública
    Quando solicito "publicar"
    E solicito "arquivar"
    Então a ficha "não deve" aparecer na consulta pública
    Quando solicito "restaurar"
    Então o estado deve ser "OCULTO"
    E a ficha "não deve" aparecer na consulta pública
