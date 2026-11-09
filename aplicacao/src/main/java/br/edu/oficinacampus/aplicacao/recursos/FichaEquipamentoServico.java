package br.edu.oficinacampus.aplicacao.recursos;

import br.edu.oficinacampus.dominio.recursos.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.aplicacao.cadastros.ConferenciaOficinas;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/**
 * Equipamentos da instituição. Um equipamento pode ser reservado para várias oficinas,
 * desde que elas não aconteçam em horários sobrepostos.
 */
public final class FichaEquipamentoServico extends ServicoCadastro<FichaEquipamento> {
    private final OficinaRepositorio oficinas;
    public FichaEquipamentoServico(FichaEquipamentoRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) {
        super(repositorio, eventos, "equipamentos", new PoliticaCiclo.Padrao());
        this.oficinas = oficinas;
    }
    @Override
    protected void conferir(FichaEquipamento novo, FichaEquipamento anterior) {
        ConferenciaOficinas.exigirNovasAtivas(oficinas, novo.oficinas(), anterior == null ? List.of() : anterior.oficinas());
        conferirReservas(novo);
    }
    @Override
    protected void conferirTransicao(FichaEquipamento novo, FichaEquipamento anterior) {
        if (novo.controle().estado() == Estado.ATIVO) conferirReservas(novo);
    }
    private void conferirReservas(FichaEquipamento equipamento) {
        ConferenciaOficinas.sobreposicao(oficinas, equipamento.oficinas()).ifPresent(par -> {
            throw ErroDominio.conflito("Conflito de agenda: " + equipamento.nome() + " não pode ser reservado para \"" + par[0].titulo()
                + "\" e \"" + par[1].titulo() + "\", que acontecem no mesmo horário.");
        });
    }
    protected List<String> valoresPesquisa(FichaEquipamento agregado) {
        return List.of(Objects.toString(agregado.nome(), ""), Objects.toString(agregado.tipo(), ""), Objects.toString(agregado.instrucoes(), ""));
    }
}
