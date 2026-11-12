# Requisitos

Cada integrante fica responsável por quatro funcionalidades; o calendário faz parte
da parte de Cadastros. A classificação de complexidade é a proposta do grupo e
depende da avaliação do professor. Todas as funcionalidades permitem consulta. As fichas da
organização também permitem cadastro, alteração, arquivamento e restauração; sugestões e atendimentos
são enviados por Lucas no portal e ficam somente para consulta.

Textos são normalizados retirando espaços nas extremidades. Campos opcionais
podem ficar vazios; números opcionais podem ser nulos. Nomes e títulos não
precisam ser únicos. Uma edição deve informar a versão recebida na consulta;
versões antigas são recusadas com conflito, sem modificar a ficha.

As histórias abaixo usam as personas de [dominio.md](dominio.md). Os cenários
automatizados ficam em `apresentacao-backend/src/test/resources/features/<contexto>/`.

## Regras comuns

### Vínculo com a oficina

A oficina é o conceito central. As fichas que existem por causa de uma oficina
guardam a identidade dela no campo **Oficina**, sempre obrigatório, e aparecem nas
abas da página da oficina. Equipamentos e instituições parceiras são cadastros
gerais que se relacionam com várias oficinas.

| Situação | Regra |
|---|---|
| Criar ficha com oficina | A oficina precisa existir (senão, 400) e estar ativa (senão, 409). |
| Editar ficha mantendo a oficina | Permitido, mesmo que a oficina tenha sido arquivada depois. |
| Editar ficha trocando a oficina | A nova oficina precisa existir e estar ativa. |
| Arquivar uma oficina | Permitido. As fichas ligadas mantêm o vínculo e continuam consultáveis. |
| Consulta por oficina | `GET /api/registros/{modulo}?oficina={id}` lista as fichas da oficina (também para equipamentos e parceiras). |

A oficina segue a mesma regra para o espaço e os instrutores. Não há exclusão
definitiva, então um vínculo nunca aponta para um registro apagado.

### Perfis de uso

Não há login. O seletor de perfil no topo organiza as telas de cada persona.

| Perfil | Persona | Telas |
|---|---|---|
| Organização | Marina | Visão geral, Calendário, Cadastros, Atividades internas, página da oficina com abas, apresentação e prévia do portal |
| Apoio | Beatriz | Minhas próximas oficinas (minicalendário e checklist por oficina), Calendário, Oficinas para consulta, abas da oficina, Atendimentos e Sugestões (somente consulta). Não altera os dados principais da oficina |
| Comunidade | Lucas | Portal com as abas Oficinas, Comunicados e Perguntas frequentes; Tenho interesse, Avaliar (oficinas realizadas), Sugerir uma oficina e Falar com a organização |

Os envios de Lucas usam rotas públicas (`/api/public/...`) e seguem as mesmas
validações das fichas criadas pela organização. Essas histórias complementam as
funcionalidades existentes e não são contadas como funcionalidades novas.

## Cadastros (Artur Antunes)

### Oficinas (`oficinas`)

Como organizadora, quero manter oficinas com data, local, instrutores e limite de participantes, sem conflito de agenda, para planejar a programação.

| Campo | Regra |
|---|---|
| Título | Obrigatório. 1 a 100 caracteres. |
| Apresentação | Obrigatório. 1 a 2000 caracteres. |
| Modalidade | Obrigatório. Opções: Presencial, Online, Híbrida. |
| Duração em minutos | Obrigatório. Inteiro de 1 a 480. |
| Limite de participantes | Obrigatório. Inteiro de 1 a 1000. |
| Data e horário | Obrigatório. Data e horário de início válidos (AAAA-MM-DDTHH:MM). |
| Espaço | Espaço ativo do cadastro de Espaços. Obrigatório para Presencial e Híbrida; opcional para Online. |
| Instrutores | Obrigatório. Uma ou mais fichas ativas de Instrutores, sem repetição. |

**Persona:** Marina. **Onde fica:** menu Cadastros.

- O limite de participantes de uma oficina presencial ou híbrida não pode passar da capacidade do espaço (400).
- Conflito de agenda (409): duas oficinas ativas com horários sobrepostos não podem usar o mesmo espaço, o mesmo instrutor ou o mesmo equipamento reservado. Uma oficina termina no horário de início mais a duração; começar exatamente quando a outra termina é permitido. Oficinas online ocupam o instrutor, mas não ocupam espaço.
- A agenda também é conferida ao editar e ao restaurar uma oficina arquivada. Oficinas arquivadas não ocupam a agenda.
- O limite não pode ficar abaixo do número de participantes ativos (409).
- A oficina é **realizada** quando o horário de término passa. Ela aparece acinzentada nas telas de Marina e no portal até ser arquivada; o portal desativa "Tenho interesse" e mostra "Avaliar".
- Enquanto ativa, aparece no portal com as vagas restantes. Arquivar a retira do portal e do calendário e impede novos vínculos; as fichas já ligadas a ela são preservadas.

O estado inicial é **ATIVO**. Depois de arquivada, a ficha continua disponível para consulta. Para editar, restaure a ficha; os dados são mantidos.

### Calendário

Como organizadora, quero ver as oficinas em um calendário interativo, com instrutores e locais, para evitar conflitos de data e horário.

- Mostra, mês a mês, as oficinas ativas com horário, título, local e instrutores; as realizadas aparecem acinzentadas.
- Clicar em uma oficina abre a página dela. Marina pode criar uma oficina a partir de um dia do calendário.
- Abaixo da grade, a agenda do mês lista as mesmas oficinas, também usada em telas estreitas.
- O calendário não guarda dados próprios: ele é uma consulta das oficinas. Os conflitos são recusados ao salvar a oficina ou a reserva de equipamento.

**Persona:** Marina e Beatriz. **Depende de:** Oficinas.

### Instrutores (`instrutores`)

Como organizadora, quero manter perfis de instrutores para escolher quem ministra cada oficina.

| Campo | Regra |
|---|---|
| Nome | Obrigatório. 1 a 100 caracteres. |
| Apresentação | Obrigatório. 1 a 2000 caracteres. |
| Especialidade | Obrigatório. 1 a 150 caracteres. |
| Contato profissional | Opcional. 0 a 254 caracteres. |

**Persona:** Marina. **Onde fica:** menu Cadastros.

- Na consulta de um instrutor aparecem as oficinas que ele ministra.

O estado inicial é **ATIVO**. Depois de arquivada, a ficha continua disponível para consulta. Para editar, restaure a ficha; os dados são mantidos.

### Espaços (`espacos`)

Como organizadora, quero manter os espaços com sua capacidade para escolher onde cada oficina acontece.

| Campo | Regra |
|---|---|
| Nome | Obrigatório. 1 a 100 caracteres. |
| Localização | Obrigatório. 1 a 200 caracteres. |
| Descrição | Opcional. 0 a 2000 caracteres. |
| Capacidade | Obrigatório. Inteiro de 1 a 1000. |

**Persona:** Marina. **Onde fica:** menu Cadastros.

- A capacidade limita as oficinas presenciais e híbridas que usam o espaço. Reduzir a capacidade depois é permitido: as oficinas ativas com limite acima da nova capacidade mostram o alerta "Limite acima da capacidade do espaço" e precisam de ajuste na próxima edição.

O estado inicial é **ATIVO**. Depois de arquivada, a ficha continua disponível para consulta. Para editar, restaure a ficha; os dados são mantidos.

## Recursos (Luca)

### Equipamentos (`equipamentos`)

Como equipe de recursos, quero manter os equipamentos da instituição e reservá-los para as oficinas, sem dois usos no mesmo horário.

| Campo | Regra |
|---|---|
| Nome | Obrigatório. 2 a 80 caracteres. |
| Tipo | Obrigatório. Opções: Audiovisual, Informática, Ferramenta, Outro. |
| Instruções de uso | Opcional. 0 a 500 caracteres. |
| Reservado para | Opcional. Nenhuma ou várias fichas ativas de Oficinas, sem repetição. |

**Persona:** Marina. **Onde fica:** aba Recursos da oficina e menu Atividades internas. Beatriz também usa esta tela.

- O equipamento é um cadastro geral da instituição (menu Atividades internas) e pode ser reservado para várias oficinas.
- Na aba Recursos de uma oficina, "Reservar equipamento" acrescenta a oficina ao equipamento, e "Cancelar reserva" a remove.
- Conflito de agenda (409): o mesmo equipamento não pode estar reservado para duas oficinas ativas em horários sobrepostos. Remarcar ou restaurar uma oficina também confere as reservas.

O estado inicial é **ATIVO**. Depois de arquivada, a ficha continua disponível para consulta. Para editar, restaure a ficha; os dados são mantidos.

### Instituições parceiras (`parceiras`)

Como equipe de recursos, quero manter as instituições parceiras e as oficinas que cada uma apoia.

| Campo | Regra |
|---|---|
| Nome | Obrigatório. 2 a 120 caracteres. |
| Modalidade de apoio | Obrigatório. Opções: Divulgação, Cultural, Institucional. |
| Informações de contato | Opcional. 0 a 300 caracteres. |
| Oficinas apoiadas | Opcional. Nenhuma ou várias fichas ativas de Oficinas, sem repetição. |

**Persona:** Marina. **Onde fica:** aba Recursos da oficina e menu Cadastros. Beatriz também usa esta tela.

- A instituição é um cadastro geral (menu Cadastros) e pode apoiar várias oficinas.
- Na aba Recursos de uma oficina, "Adicionar instituição" acrescenta a oficina às apoiadas, e "Remover apoio" a retira.

O estado inicial é **ATIVO**. Depois de arquivada, a ficha continua disponível para consulta. Para editar, restaure a ficha; os dados são mantidos.

### Kits de materiais (`kits`)

Como equipe de recursos, quero manter o kit de materiais de cada oficina para registrar, em uma lista textual, o que precisa ser separado.

| Campo | Regra |
|---|---|
| Nome | Obrigatório. 2 a 80 caracteres. |
| Finalidade | Opcional. 0 a 300 caracteres. |
| Componentes | Obrigatório. Lista com pelo menos um item, cada um com até 100 caracteres. |
| Oficina | Obrigatório. Ficha ativa de Oficinas. |

**Persona:** Beatriz. **Onde fica:** aba Recursos da oficina.

- Aparece na aba Recursos da oficina.

O estado inicial é **ATIVO**. Depois de arquivada, a ficha continua disponível para consulta. Para editar, restaure a ficha; os dados são mantidos.

### Acessibilidade (`acessibilidade`)

Como equipe de recursos, quero registrar os recursos de acessibilidade de cada oficina para orientar sua utilização.

| Campo | Regra |
|---|---|
| Nome | Obrigatório. 2 a 80 caracteres. |
| Categoria | Obrigatório. Opções: Visual, Auditiva, Mobilidade, Comunicação, Outra. |
| Orientação de utilização | Obrigatório. 1 a 500 caracteres. |
| Oficina | Obrigatório. Ficha ativa de Oficinas. |

**Persona:** Beatriz. **Onde fica:** aba Recursos da oficina.

- Aparece na aba Recursos da oficina.

O estado inicial é **ATIVO**. Depois de arquivada, a ficha continua disponível para consulta. Para editar, restaure a ficha; os dados são mantidos.

## Relacionamento (Ricardo)

### Manifestações de interesse (`interesses`)

Como organizadora, quero aceitar ou recusar as manifestações de interesse; ao aceitar, o participante entra na oficina.

| Campo | Regra |
|---|---|
| Nome | Obrigatório. 2 a 100 caracteres. |
| E-mail | Obrigatório. Formato de e-mail; não confirma existência nem exclusividade. |
| Telefone | Obrigatório. De 8 a 15 dígitos, com DDD; aceita espaços, parênteses, hífen e "+". |
| Profissão | Obrigatório. 2 a 100 caracteres. |
| Oficina | Obrigatório. Ficha ativa de Oficinas. |
| Situação | Definida pelo sistema; não aparece no formulário. Opções: Pendente, Aceita, Recusada. |

**Persona:** Marina. **Onde fica:** aba Pessoas da oficina. Beatriz também usa esta tela.

**Pelo portal (Lucas):** Como aluno, quero manifestar interesse em uma oficina pelo portal, informando meus contatos, para ser incluído como participante.

- Lucas envia pelo botão "Tenho interesse" no cartão da oficina, informando somente nome, e-mail, telefone e profissão. Depois do envio, a tela avisa que ele receberá mais informações por e-mail (não há envio real).
- A manifestação começa **Pendente**. Não é cadastrada nem editada pela organização: Marina ou Beatriz, na aba Pessoas da oficina, escolhem **Aceitar** ou **Recusar**.
- Aceitar inclui a pessoa como participante da oficina, com os mesmos dados, e muda a situação para **Aceita**. Recusar muda para **Recusada**. Uma manifestação aceita ou recusada não muda mais de situação (409).
- Não é possível manifestar interesse nem aceitar quando a oficina está arquivada, já aconteceu ou não tem vagas (409). No portal, o botão fica desativado com o texto "Vagas esgotadas".

O estado inicial é **ATIVO**, com situação **Pendente**. Arquivar preserva a manifestação; para aceitar ou recusar, ela precisa estar ativa.

### Participantes (`participantes`)

Como equipe de relacionamento, quero manter os participantes de cada oficina, respeitando o limite de vagas.

| Campo | Regra |
|---|---|
| Nome | Obrigatório. 2 a 100 caracteres. |
| E-mail | Obrigatório. Formato de e-mail; não confirma existência nem exclusividade. |
| Telefone | Obrigatório. De 8 a 15 dígitos, com DDD; aceita espaços, parênteses, hífen e "+". |
| Profissão | Obrigatório. 2 a 100 caracteres. |
| Observações | Opcional. 0 a 500 caracteres. |
| Oficina | Obrigatório. Ficha ativa de Oficinas. |

**Persona:** Beatriz. **Onde fica:** aba Pessoas da oficina.

- As vagas ocupadas são os participantes ativos. Um novo participante, a troca de oficina ou a restauração exigem vaga (409 "Vagas esgotadas").
- Arquivar um participante libera a vaga. Aparece na aba Pessoas da oficina.

O estado inicial é **ATIVO**. Depois de arquivada, a ficha continua disponível para consulta. Para editar, restaure a ficha; os dados são mantidos.

### Avaliações (`avaliacoes`)

Como equipe de relacionamento, quero registrar avaliações das oficinas realizadas para conhecer a percepção de quem participou.

| Campo | Regra |
|---|---|
| Nota | Obrigatório. Inteiro de 1 a 5. |
| Aspecto | Obrigatório. Opções: Conteúdo da oficina, Condução do instrutor, Organização e espaço. |
| Comentário | Obrigatório. 5 a 500 caracteres. |
| Nome opcional | Opcional. 2 a 100 caracteres. |
| Oficina | Obrigatório. Ficha ativa de Oficinas. |

**Persona:** Beatriz. **Onde fica:** aba Pessoas da oficina.

**Pelo portal (Lucas):** Como aluno, quero avaliar pelo portal uma oficina depois que ela acontecer, para contar como foi a experiência.

- Só é aceita depois que a oficina acontece (409 antes do término). No portal, "Avaliar" aparece apenas nas oficinas realizadas. Aparece na aba Pessoas da oficina.

O estado inicial é **ATIVO**. Depois de arquivada, a ficha continua disponível para consulta. Para editar, restaure a ficha; os dados são mantidos.

### Atendimentos (`atendimentos`)

Como organizadora, quero consultar as dúvidas e os problemas enviados pela comunidade para acompanhar o que precisa de atenção.

| Campo | Regra |
|---|---|
| Assunto | Obrigatório. 5 a 120 caracteres. |
| Descrição | Obrigatório. 10 a 1000 caracteres. |
| Categoria | Obrigatório. Opções: Dúvida, Problema no portal, Outro. |

**Persona:** Beatriz. **Onde fica:** menu Atividades internas. Envio feito pelo portal; Marina e Beatriz apenas consultam.

**Pelo portal (Lucas):** Como aluno, quero enviar pelo portal uma dúvida ou um problema para a organização.

- Lucas envia pelo botão "Atendimento", no topo do portal, ou "Tirar uma dúvida", no destaque da página. O formulário "Falar com a organização" pede assunto, descrição e categoria.
- Marina e Beatriz **apenas consultam**, no menu Atividades internas: não cadastram, não editam e não arquivam (409 nas rotas da organização). A tela inicial de Beatriz mostra quantos atendimentos foram recebidos.

O estado é **ATIVO** desde o envio e não muda: a ficha fica disponível somente para consulta.
