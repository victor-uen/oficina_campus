package br.edu.oficinacampus.aplicacao.recursos;

import br.edu.oficinacampus.dominio.recursos.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.aplicacao.cadastros.ConferenciaOficinas;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import java.util.List;
import java.util.Objects;

/** Instituições parceiras; uma mesma instituição pode apoiar várias oficinas. */
public final class InstituicaoParceiraServico extends ServicoCadastro<InstituicaoParceira> {
    private final OficinaRepositorio oficinas;
    public InstituicaoParceiraServico(InstituicaoParceiraRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) {
        super(repositorio, eventos, "parceiras", new PoliticaCiclo.Padrao());
        this.oficinas = oficinas;
    }
    @Override
    protected void conferir(InstituicaoParceira nova, InstituicaoParceira anterior) {
        ConferenciaOficinas.exigirNovasAtivas(oficinas, nova.oficinas(), anterior == null ? List.of() : anterior.oficinas());
    }
    protected List<String> valoresPesquisa(InstituicaoParceira agregado) {
        return List.of(Objects.toString(agregado.nome(), ""), Objects.toString(agregado.modalidade(), ""), Objects.toString(agregado.contato(), ""));
    }
}
