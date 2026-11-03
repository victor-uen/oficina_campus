# Domínio

O Oficina Campus apoia a organização de oficinas acadêmicas: planejar a agenda,
controlar as vagas, decidir quem participa e reunir o que cada oficina precisa.
Essas informações costumam ficar espalhadas em mensagens e planilhas, e é fácil
marcar duas oficinas no mesmo espaço ou passar do limite de uma sala.

A oficina é o centro do domínio. Instrutores e espaços existem antes dela;
pessoas, recursos, preparação e execução existem por causa dela.

## Personas

São personas hipotéticas usadas para orientar o projeto. Cada uma tem um perfil
de uso com telas próprias.

| Persona | Perfil | Objetivo | Telas |
|---|---|---|---|
| Marina, administradora | Organização | Montar a programação sem conflitos, decidir quem participa e manter as informações atualizadas | Visão geral, Calendário, Cadastros, Atividades internas, página da oficina com abas, prévia do portal |
| Beatriz, professora | Apoio | Ver, por oficina, quem vai participar, os recursos reservados e o que falta fazer | Minhas próximas oficinas, Calendário, Oficinas (consulta), abas da oficina, Atendimentos e Sugestões (somente consulta) |
| Lucas, aluno | Comunidade | Encontrar oficinas com vaga, manifestar interesse, se preparar, avaliar depois, sugerir temas e tirar dúvidas | Portal com as abas Oficinas, Comunicados e Perguntas frequentes, e os botões Sugerir uma oficina e Falar com a organização |

## Vocabulário

| Termo | Significado |
|---|---|
| Oficina | Título, apresentação, modalidade, duração, limite de participantes, data e horário, espaço e instrutores |
| Oficina realizada | Oficina cujo horário de término já passou; aparece acinzentada até ser arquivada |
| Conflito de agenda | Duas oficinas ativas, em horários sobrepostos, com o mesmo espaço, o mesmo instrutor ou o mesmo equipamento |
| Capacidade | Número máximo de pessoas de um espaço; limita as oficinas presenciais e híbridas |
| Vagas | Limite da oficina menos os participantes ativos |
| Manifestação de interesse | Pedido de Lucas para participar de uma oficina, com nome, e-mail, telefone e profissão; fica pendente até ser aceito ou recusado |
| Participante | Pessoa incluída em uma oficina, à mão ou ao aceitar uma manifestação de interesse |
| Equipamento | Item da instituição que pode ser reservado para várias oficinas em horários diferentes |
| Instituição parceira | Organização que pode apoiar várias oficinas |
| Material de apoio | Link de estudo da oficina |
| Leitura complementar | Livro, capítulo ou artigo indicado para a oficina, com autor, trecho e ordem sugerida |
| Arquivado | Registro preservado fora da listagem ativa |
| Visível / oculto | Estado escolhido manualmente para a exibição de conteúdo no portal |
| Situação | Andamento informado em uma ficha operacional ou em uma manifestação de interesse |
| Atendimento | Dúvida ou problema que Lucas envia pelo portal, com assunto, descrição e categoria |
| Sugestão | Oficina que Lucas propõe pelo portal e que ainda não existe |

## Organização das telas

- **Cadastros**: Oficinas, Instrutores, Espaços e Instituições parceiras.
- **Atividades internas**: Equipamentos, Sugestões, Atendimentos, Perguntas frequentes e Comunicados. Sugestões e atendimentos são enviados por Lucas no portal; Marina e Beatriz apenas os consultam.
- **Página da oficina**, com abas: Resumo; Pessoas (manifestações de interesse,
  participantes e avaliações); Recursos (kits, acessibilidade, equipamentos
  reservados e parceiras que apoiam); Preparação (materiais, guias e leituras);
  Execução (tarefas e ocorrências).
- **Calendário** interativo com as oficinas, seus instrutores e locais.
- **Portal da comunidade**, com as abas Oficinas, Comunicados e Perguntas frequentes.

## Escopo

Não há login, inscrição automática, pagamento nem envio real de e-mails. Lucas
manifesta interesse e a organização decide: aceitar inclui a pessoa como
participante, desde que haja vaga. A mensagem exibida a Lucas apenas avisa que o
contato será feito por e-mail. Nomes e títulos podem se repetir. Os exemplos da
interface são fictícios.
