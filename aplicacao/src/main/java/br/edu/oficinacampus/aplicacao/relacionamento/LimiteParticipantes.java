package br.edu.oficinacampus.aplicacao.relacionamento;

import br.edu.oficinacampus.aplicacao.cadastros.RegraOficina;
import br.edu.oficinacampus.dominio.cadastros.Oficina;
import br.edu.oficinacampus.dominio.compartilhado.*;
import br.edu.oficinacampus.dominio.relacionamento.FichaParticipanteRepositorio;

/** O limite de uma oficina não pode ficar abaixo do número de participantes já incluídos. */
public final class LimiteParticipantes implements RegraOficina {
    private final FichaParticipanteRepositorio participantes;
    public LimiteParticipantes(FichaParticipanteRepositorio participantes) { this.participantes = participantes; }
    public void conferir(Oficina oficina) {
        String id = oficina.controle().id();
        long ocupadas = participantes.listar().stream().filter(p -> p.controle().estado() == Estado.ATIVO && id.equals(p.oficina())).count();
        if (oficina.limite() < ocupadas)
            throw ErroDominio.conflito("O limite não pode ser menor que o número de participantes já incluídos (" + ocupadas + ").");
    }
}
