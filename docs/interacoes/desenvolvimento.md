# Desenvolvimento e apoio de IA

O Oficina Campus foi organizado a partir do escopo, dos documentos funcionais, do enunciado e da referência do professor reunidos por Artur. Esse material orientou as funcionalidades, a divisão das partes e a estrutura do código. O projeto passou a reunir os artefatos de Requisitos de Software e o recorte de concorrência utilizado em FCCPD.

## Definição do escopo

O ponto de partida foi a organização de informações sobre oficinas acadêmicas. Foram definidos os conceitos usados no sistema, os dados de cada ficha e as operações necessárias para manter essas informações. As histórias foram relacionadas às atividades de preparar oficinas, ouvir interesses da comunidade, organizar recursos e disponibilizar orientações.

As personas ajudaram a apresentar essas necessidades e a organizar o mapa de histórias. Elas foram tratadas como hipóteses do projeto. Os dados de exemplo também foram mantidos como fictícios, para permitir a demonstração sem depender de informações pessoais reais.

A primeira versão tratava todas as fichas como independentes. Na revisão do modelo, a oficina passou a ter data e horário, local (um espaço do cadastro), instrutores e limite de participantes, e ficou definido que pessoas, kits, acessibilidade, preparação e execução só fazem sentido ligados a uma oficina. Equipamentos e instituições parceiras passaram a ser cadastros gerais que se relacionam com várias oficinas.

Numa segunda revisão, o sistema passou a evitar conflitos de agenda (mesmo espaço, instrutor ou equipamento no mesmo horário), a controlar as vagas pela capacidade do espaço e pelo limite da oficina, e a reconhecer oficinas já realizadas. A manifestação de interesse passou a ser feita por Lucas dentro do cartão da oficina, com nome, e-mail, telefone e profissão; a organização aceita (o que inclui a pessoa como participante) ou recusa. O menu foi reorganizado em Cadastros e Atividades internas, com as fichas da oficina em abas e um calendário interativo. Procedimentos deixaram de existir, e as trilhas de estudo deram lugar às leituras complementares. Login, inscrição automática, pagamentos e envio real de e-mails ficaram fora desta versão.

## Organização das partes

A divisão do grupo foi organizada em cinco contextos, com quatro funcionalidades para cada responsável:

| Integrante | Contexto | Funcionalidades |
|---|---|---|
| Artur Antunes | Cadastros | Oficinas, instrutores, espaços e calendário |
| Luca | Recursos | Equipamentos (com reserva), instituições parceiras, kits de materiais e acessibilidade |
| Ricardo | Relacionamento | Manifestações de interesse, participantes, avaliações e atendimentos |
| Victor | Conteúdo | Materiais de apoio, guias de preparação, leituras complementares e comunicados |
| João Cláudio | Operação | Tarefas, ocorrências, sugestões e perguntas frequentes |

Essa divisão orientou a separação dos arquivos e dos assuntos a revisar e apresentar. Cada contexto reúne suas regras, seus serviços e sua persistência, além dos formulários e cenários de teste correspondentes. As responsabilidades atribuídas ao grupo e as contribuições efetivamente realizadas são informações distintas; as revisões e alterações individuais são registradas nos commits correspondentes.

## Modelagem e implementação

A estrutura do exemplo do professor serviu de referência para separar domínio, aplicação, infraestrutura e apresentação em módulos Maven. O vocabulário do projeto foi usado nos nomes das classes e nos modelos CML, mantendo a relação entre requisitos e código.

Os conceitos foram representados por agregados próprios, com atributos e validações específicos. Os serviços de aplicação ficaram responsáveis por coordenar as operações, enquanto as interfaces de repositório permitiram separar as regras da implementação de persistência. O acesso ao banco foi concentrado nos adaptadores JPA da infraestrutura.

Na apresentação, os formulários foram organizados por contexto e ligados aos controladores REST. A interface passou a permitir cadastro, consulta, pesquisa, edição, arquivamento e restauração das fichas. O protótipo com dados fictícios foi mantido para facilitar a navegação e a apresentação sem iniciar o servidor.

As diferenças entre os contextos foram preservadas. Conteúdos começam ocultos e dependem de publicação manual para aparecer no portal. Fichas operacionais permitem edição mesmo quando arquivadas, mantendo a situação informada. Nos cadastros comuns, a restauração permite retomar a edição.

## Verificação e relação com FCCPD

Os testes foram organizados para conferir as regras dos agregados, os serviços de aplicação e os contratos HTTP. Os cenários Cucumber descrevem os fluxos de cada funcionalidade, incluindo preenchimento inválido, alteração de dados, mudanças de estado e tentativa de salvar uma versão antiga.

Também foram exercitados os formulários no navegador, acompanhando a informação desde a tela até o banco. A persistência foi conferida após reiniciar a aplicação, usando dados e banco próprios para os testes.

O cenário de concorrência da primeira entrega de FCCPD foi aproveitado na estrutura atual. Clientes HTTP disputam a edição da mesma oficina usando a mesma versão, com títulos diferentes. A verificação exige que apenas uma edição seja aceita, que as demais recebam conflito e que o banco preserve os dados da edição vencedora. A implementação atual usa transações JPA e controle de versão no H2.

## Decisões de implementação

- Interface web em HTML, CSS e JavaScript, integrada à API Java/JPA.
- Protótipo navegável com dados fictícios e modo offline.
- Vínculo com a oficina guardado como identidade e conferido na aplicação, sem chave estrangeira entre agregados.
- Agenda, vagas e oficinas realizadas conferidas nos serviços de aplicação, com a porta `RegraOficina` para as regras de outros contextos.
- Três perfis de uso (Marina, Beatriz e Lucas) e rotas públicas para o portal da comunidade, sem autenticação.
- Uso de Strategy, Template Method, Decorator e Observer nas responsabilidades compartilhadas.
- Controle de versão para evitar a sobrescrita silenciosa de alterações concorrentes.
- Requisitos, mapa de histórias, modelo CML e testes organizados junto ao código.

## Participação das ferramentas de IA

ChatGPT/Codex auxiliou a organização dos requisitos, a análise da referência do professor e a discussão da estrutura. Esse apoio incluiu geração e alteração de código, interface, testes e documentação, além da execução de comandos e preparação de alterações no repositório.

As solicitações partiram do material do projeto e das regras já definidas para as funcionalidades. As propostas foram conferidas em relação a esse escopo e submetidas às verificações automatizadas descritas acima. A utilização das ferramentas acompanhou o desenvolvimento dos artefatos e deve ser considerada junto às revisões e contribuições individuais registradas pelo grupo.

Claude (Anthropic) auxiliou as revisões que introduziram os vínculos com a oficina, as telas por perfil, o calendário, o controle de vagas, o aceite de interesses e a nova organização do menu. Esse apoio incluiu a proposta das relações, alterações no domínio, nos serviços, na persistência, na interface e nos testes, a atualização dos documentos e a reorganização das etapas de envio. As decisões sobre as regras (quais fichas dependem da oficina, como funcionam as vagas, o que acontece com oficinas realizadas e a nova divisão das partes) foram tomadas por Victor nessas revisões, para confirmação do grupo.

Este documento apresenta uma síntese do processo e do uso de IA. As conversas originais complementam o registro de interações solicitado no enunciado.
