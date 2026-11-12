# language: pt
# Regras do contexto Relacionamento.
@ricardo @relacionamento
Funcionalidade: Gerenciamento de avaliações
  Como equipe de relacionamento, quero registrar avaliações das oficinas realizadas para conhecer a percepção de quem participou.

  Contexto:
    Dado o cadastro de "avaliacoes" com dados válidos

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




  Cenário: Exigir a oficina a que a ficha pertence
    Dado a quantidade de fichas antes da tentativa
    Quando deixo a oficina em branco
    E crio a ficha
    Então a resposta deve ter código 400
    E nenhuma ficha deve ser adicionada




  Cenário: Recusar vínculo com oficina que não está no catálogo
    Dado a quantidade de fichas antes da tentativa
    Quando informo uma oficina inexistente
    E crio a ficha
    Então a resposta deve ter código 400
    E nenhuma ficha deve ser adicionada




  Cenário: Recusar vínculo novo com oficina arquivada
    Dado que a oficina escolhida está arquivada
    Quando crio a ficha
    Então a resposta deve ter código 409




  Cenário: Manter o vínculo quando a oficina é arquivada depois
    Quando crio a ficha
    E a oficina vinculada é arquivada
    E altero um campo com informação válida
    Então a resposta deve ter código 200
    E a ficha deve aparecer na consulta da oficina
    Quando vinculo a ficha a uma oficina arquivada
    Então a resposta deve ter código 409



  Cenário: Receber a ficha enviada pelo portal da comunidade
    Quando envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 201
    E o estado deve ser "ATIVO"
    E a ficha deve continuar disponível para consulta



  Cenário: Aceitar avaliação somente depois da oficina
    Dado que a oficina escolhida ainda não aconteceu
    Quando envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 409
    Dado que a oficina escolhida já aconteceu
    Quando envio a ficha pelo portal da comunidade
    Então a resposta deve ter código 201
