package br.edu.oficinacampus.aplicacao.conteudo;

import br.edu.oficinacampus.dominio.conteudo.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import java.util.List;
import java.util.Objects;

/** Comunicados: cadastro sem vínculo com outros agregados. */
public final class ComunicadoServico extends ServicoCadastro<Comunicado> {
    public ComunicadoServico(ComunicadoRepositorio repositorio, Eventos eventos) {
        super(repositorio, eventos, "comunicados", new PoliticaCiclo.Conteudo());
    }
    protected List<String> valoresPesquisa(Comunicado agregado) {
        return List.of(Objects.toString(agregado.titulo(), ""), Objects.toString(agregado.mensagem(), ""), Objects.toString(agregado.etiqueta(), ""));
    }
}
