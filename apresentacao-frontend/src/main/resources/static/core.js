(function(root){
'use strict';
/* Regras compartilhadas pela interface. No protótipo (sem servidor) elas reproduzem as regras
 * dos serviços Java; com a aplicação Java aberta, quem decide é o servidor. */
const fail=(message,status=400)=>{const e=new Error(message);e.status=status;throw e;};
const TEL=/^[0-9()+\- ]+$/;
function validate(m,data){
 const result={};
 if(!data||Object.keys(data).some(k=>!m.fields.some(f=>f.key===k)))fail('O cadastro contém campos desconhecidos.');
 for(const f of m.fields){let v=data[f.key];if(typeof v==='string')v=v.trim();
  if(f.kind==='ref'&&f.multiple){if(v===undefined||v===null)v=[];if(!Array.isArray(v)||v.some(x=>typeof x!=='string'||!/^[A-Za-z0-9_-]+$/.test(x.trim())))fail(`${f.label} deve indicar registros existentes.`);v=v.map(x=>x.trim());if(v.length<(f.required?Math.max(1,f.min):0))fail(`${f.label}: escolha pelo menos ${Math.max(1,f.min)}.`);if(new Set(v).size!==v.length)fail(`${f.label} não pode repetir o mesmo registro.`);result[f.key]=v;continue;}
  if(v===undefined||v===null||v===''){if(f.required)fail(`${f.label} é obrigatório.`);if(f.requiredUnless){const [campo,valor]=f.requiredUnless.split('=');const outro=m.fields.find(x=>x.key===campo);if(String(data[campo]??'').trim()!==valor)fail(`${f.label} é obrigatório, exceto quando ${outro?outro.label:campo} for ${valor}.`);}result[f.key]=f.kind==='number'?null:f.readonly&&f.kind==='select'?f.options[0]:'';continue;}
  if(f.kind==='number'){if(typeof v!=='number'||!Number.isInteger(v)||v<f.min||v>f.max)fail(`${f.label} deve ser um inteiro de ${f.min} a ${f.max}.`);}
  else if(f.kind==='list'){if(!Array.isArray(v)||!v.length||v.some(x=>typeof x!=='string'||!x.trim()||x.trim().length>f.max))fail(`${f.label}: inclua pelo menos um componente válido, com até ${f.max} caracteres.`);v=v.map(x=>x.trim());}
  else {if(typeof v!=='string')fail(`${f.label} deve ser texto.`);if(v.length<f.min||(f.max>0&&v.length>f.max))fail(`${f.label} deve ter ${f.min} a ${f.max} caracteres.`);
   if(f.kind==='select'&&!f.options.includes(v))fail(`${f.label} possui uma opção inválida.`);
   if(f.kind==='singleline'&&/[\r\n]/.test(v))fail(`${f.label} deve ocupar uma única linha.`);
   if(f.kind==='email'&&!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v))fail(`${f.label} deve ter formato de e-mail.`);
   if(f.kind==='tel'){const n=(v.match(/\d/g)||[]).length;if(!TEL.test(v)||n<8||n>15)fail(`${f.label} deve ter de 8 a 15 dígitos, com DDD. Ex.: (81) 99999-0000.`);}
   if(f.kind==='ref'&&!/^[A-Za-z0-9_-]+$/.test(v))fail(`${f.label} deve indicar uma ficha existente.`);
   if(f.kind==='datetime'){const ok=/^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}$/.test(v)&&(()=>{const d=new Date(v+':00Z');return !isNaN(d)&&d.toISOString().slice(0,16)===v;})();if(!ok)fail(`${f.label} deve ser uma data e um horário válidos (AAAA-MM-DDTHH:MM).`);}
   if(f.kind==='url'){try{let u=new URL(v);if(!['http:','https:'].includes(u.protocol)||!u.hostname)throw Error();}catch{fail(`${f.label} deve ser uma URL HTTP ou HTTPS com host.`);}}
  }result[f.key]=v;
 }return result;
}
/** Mesma regra dos serviços Java: vínculo novo exige registro existente e ativo; vínculo mantido continua válido.
 * records: registros por módulo, como {oficinas:[...], instrutores:[...]}. */
function checkLinks(m,data,existing,records){
 for(const f of m.fields.filter(x=>x.kind==='ref')){
  const now=[].concat(data[f.key]||[]).filter(Boolean),before=existing?[].concat(existing.dados[f.key]||[]):[];
  for(const id of now.filter(x=>!before.includes(x))){
   const r=((records||{})[f.ref]||[]).find(x=>x.id===id);
   if(!r)fail(`${f.label}: o registro escolhido não existe no cadastro.`);
   if(r.estado!=='ATIVO')fail(`${f.label}: o registro escolhido está arquivado. Restaure-o ou escolha outro.`,409);
  }
 }
}

/* ---------- Agenda, vagas e oficinas realizadas ---------- */
const minutes=s=>Date.parse(s+':00Z')/60000;
/** Início e fim (em minutos) de uma oficina. */
function span(o){const d=o.dados||o,s=minutes(d.data);return [s,s+Number(d.duracao||0)];}
function overlaps(a,b){const [s1,e1]=span(a),[s2,e2]=span(b);return s1<e2&&s2<e1;}
/** Uma oficina já aconteceu quando o horário de término passou. "agora" no formato AAAA-MM-DDTHH:MM. */
function realized(o,agora){return span(o)[1]<=minutes(agora);}
const usesSpace=d=>d.modalidade!=='Online'&&!!d.espaco;
const list=(records,id)=>(records&&records[id])||[];
const find=(records,mod,id)=>list(records,mod).find(r=>r.id===id);
const nameOf=(records,mod,id,fallback)=>find(records,mod,id)?.dados?.nome||fallback;
/** Vagas ocupadas: participantes ativos da oficina. */
function occupied(records,oficina,ignore){return list(records,'participantes').filter(p=>p.estado==='ATIVO'&&p.dados.oficina===oficina&&p.id!==ignore).length;}
function requireSeat(records,oficina,ignore){const o=find(records,'oficinas',oficina);if(!o)fail('Oficina não encontrada. Escolha uma oficina do cadastro.');const n=occupied(records,oficina,ignore);if(n>=o.dados.limite)fail(`Vagas esgotadas: a oficina "${o.dados.titulo}" já tem ${n} de ${o.dados.limite} participantes.`,409);}
/** Oficina acima da capacidade atual do espaço (a capacidade pode ter sido reduzida depois). */
function overCapacity(o,records){const d=o.dados;if(!usesSpace(d))return false;const e=find(records,'espacos',d.espaco);return !!(e&&e.dados.capacidade!=null&&d.limite>e.dados.capacidade);}
function checkCapacity(d,records){if(!usesSpace(d))return;const e=find(records,'espacos',d.espaco);const cap=e?.dados?.capacidade;if(cap!=null&&d.limite>cap)fail(`O limite de participantes (${d.limite}) passa da capacidade do espaço (${cap}).`);}
function checkAgenda(id,d,records){
 const nova={dados:d};
 for(const outra of list(records,'oficinas')){
  if(outra.id===id||outra.estado!=='ATIVO'||!overlaps(outra,nova))continue;
  if(usesSpace(d)&&usesSpace(outra.dados)&&d.espaco===outra.dados.espaco)fail(`Conflito de agenda: o espaço ${nameOf(records,'espacos',d.espaco,'escolhido')} já está reservado para "${outra.dados.titulo}" nesse horário.`,409);
  const comum=(d.instrutores||[]).find(i=>(outra.dados.instrutores||[]).includes(i));
  if(comum)fail(`Conflito de agenda: ${nameOf(records,'instrutores',comum,'o instrutor escolhido')} já ministra "${outra.dados.titulo}" nesse horário.`,409);
 }
 for(const eq of list(records,'equipamentos')){
  if(eq.estado!=='ATIVO'||!(eq.dados.oficinas||[]).includes(id))continue;
  for(const outraId of eq.dados.oficinas){if(outraId===id)continue;const outra=find(records,'oficinas',outraId);
   if(outra&&outra.estado==='ATIVO'&&overlaps(outra,nova))fail(`Conflito de agenda: o equipamento ${eq.dados.nome} já está reservado para "${outra.dados.titulo}" nesse horário.`,409);}
 }
 const n=occupied(records,id);if(id&&d.limite<n)fail(`O limite não pode ser menor que o número de participantes já incluídos (${n}).`,409);
}
function checkReservations(d,records){
 const ativas=(d.oficinas||[]).map(id=>find(records,'oficinas',id)).filter(o=>o&&o.estado==='ATIVO');
 for(let i=0;i<ativas.length;i++)for(let j=i+1;j<ativas.length;j++)if(overlaps(ativas[i],ativas[j]))
  fail(`Conflito de agenda: ${d.nome} não pode ser reservado para "${ativas[i].dados.titulo}" e "${ativas[j].dados.titulo}", que acontecem no mesmo horário.`,409);
}
function availableOficina(records,id,agora){const o=find(records,'oficinas',id);if(!o)fail('Oficina não encontrada. Escolha uma oficina do cadastro.');if(o.estado!=='ATIVO')fail('A oficina está arquivada.',409);if(realized(o,agora))fail('Esta oficina já aconteceu.',409);return o;}
/** Regras entre fichas, conferidas antes de gravar (existing é nulo na criação). */
function checkRules(m,data,existing,records,agora){
 const id=existing?.id,changed=k=>!existing||existing.dados[k]!==data[k];
 if(m.id==='oficinas'){checkCapacity(data,records);if(!existing||existing.estado==='ATIVO')checkAgenda(id,data,records);}
 if(m.id==='participantes'&&changed('oficina'))requireSeat(records,data.oficina,id);
 if(m.id==='interesses'){if(existing)fail('A manifestação de interesse não é editada: aceite ou recuse.',409);if(data.situacao!=='Pendente')fail('Uma nova manifestação de interesse começa pendente.');availableOficina(records,data.oficina,agora);requireSeat(records,data.oficina);}
 if(m.id==='avaliacoes'&&changed('oficina')){const o=find(records,'oficinas',data.oficina);if(o&&!realized(o,agora))fail(`A oficina "${o.dados.titulo}" ainda não aconteceu. A avaliação fica disponível depois do término.`,409);}
 if(m.id==='equipamentos')checkReservations(data,records);
}
/** Regras ao restaurar uma ficha arquivada. */
function checkRestore(m,r,records){
 if(m.id==='oficinas'){checkCapacity(r.dados,records);checkAgenda(r.id,r.dados,records);}
 if(m.id==='participantes')requireSeat(records,r.dados.oficina,r.id);
 if(m.id==='equipamentos')checkReservations(r.dados,records);
}
/** Aceitar ou recusar uma manifestação: devolve a nova situação; ao aceitar, também os dados do participante. */
function decide(r,action,records,agora){
 if(r.estado!=='ATIVO')fail('Restaure a manifestação antes de aceitar ou recusar.',409);
 if(r.dados.situacao!=='Pendente')fail(`Esta manifestação já foi ${String(r.dados.situacao).toLowerCase()}.`,409);
 if(action==='recusar')return {situacao:'Recusada'};
 if(action!=='aceitar')fail('A ação não é permitida no estado atual.',409);
 availableOficina(records,r.dados.oficina,agora);requireSeat(records,r.dados.oficina);
 const d=r.dados;return {situacao:'Aceita',participante:{nome:d.nome,email:d.email,telefone:d.telefone,profissao:d.profissao,observacoes:'Incluído a partir da manifestação de interesse.',oficina:d.oficina}};
}
function initial(m){return m.policy==='conteudo'?'OCULTO':'ATIVO';}
function canEdit(m,r){return m.edit!==false&&(m.policy==='operacao'||r.estado!=='ARQUIVADO');}
function transition(m,r,action){
 if(action==='arquivar'&&r.estado!=='ARQUIVADO')return 'ARQUIVADO';
 if(action==='restaurar'&&r.estado==='ARQUIVADO')return initial(m);
 if(m.policy==='conteudo'&&action==='publicar'&&r.estado==='OCULTO')return 'VISIVEL';
 if(m.policy==='conteudo'&&action==='ocultar'&&r.estado==='VISIVEL')return 'OCULTO';
 fail('A ação não é permitida no estado atual.',409);
}
const Core={validate,checkLinks,checkRules,checkRestore,decide,occupied,overCapacity,realized,overlaps,span,initial,canEdit,transition,fail};
if(typeof module!=='undefined'&&module.exports)module.exports=Core;else root.CampusCore=Core;
})(typeof window!=='undefined'?window:globalThis);
