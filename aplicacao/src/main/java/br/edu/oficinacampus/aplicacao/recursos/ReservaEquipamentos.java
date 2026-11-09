package br.edu.oficinacampus.aplicacao.recursos;

import br.edu.oficinacampus.aplicacao.cadastros.RegraOficina;
import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.dominio.compartilhado.*;
import br.edu.oficinacampus.dominio.recursos.*;

/** Ao remarcar ou restaurar uma oficina, os equipamentos reservados não podem ficar com dois usos no mesmo horário. */
public final class ReservaEquipamentos implements RegraOficina {
    private final FichaEquipamentoRepositorio equipamentos;
    private final OficinaRepositorio oficinas;
    public ReservaEquipamentos(FichaEquipamentoRepositorio equipamentos, OficinaRepositorio oficinas) {
        this.equipamentos = equipamentos; this.oficinas = oficinas;
    }
    public void conferir(Oficina oficina) {
        String id = oficina.controle().id();
        for (FichaEquipamento equipamento : equipamentos.listar()) {
            if (equipamento.controle().estado() != Estado.ATIVO || !equipamento.oficinas().contains(id)) continue;
            for (String outraId : equipamento.oficinas()) {
                if (outraId.equals(id)) continue;
                oficinas.buscar(outraId).filter(o -> o.controle().estado() == Estado.ATIVO && o.sobrepoe(oficina)).ifPresent(outra -> {
                    throw ErroDominio.conflito("Conflito de agenda: o equipamento " + equipamento.nome() + " já está reservado para \"" + outra.titulo() + "\" nesse horário.");
                });
            }
        }
    }
}
