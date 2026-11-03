package br.edu.oficinacampus.aplicacao.cadastros;

import br.edu.oficinacampus.aplicacao.compartilhada.ConferenciaVinculo;
import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.dominio.compartilhado.Estado;
import java.util.List;
import java.util.Optional;

/** Apoio aos contextos que se relacionam com várias oficinas ao mesmo tempo. */
public final class ConferenciaOficinas {
    private ConferenciaOficinas() { }
    /** Oficinas recém-escolhidas precisam existir e estar ativas; as já vinculadas são preservadas. */
    public static void exigirNovasAtivas(OficinaRepositorio oficinas, List<String> novas, List<String> anteriores) {
        for (String id : novas)
            if (!anteriores.contains(id))
                ConferenciaVinculo.exigirAtivo(oficinas, id, "Oficina não encontrada. Escolha oficinas do cadastro.",
                    "Uma das oficinas escolhidas está arquivada. Restaure a oficina ou escolha outra.");
    }
    /** Primeiro par de oficinas ativas, entre as informadas, com horários sobrepostos. */
    public static Optional<Oficina[]> sobreposicao(OficinaRepositorio oficinas, List<String> ids) {
        List<Oficina> ativas = ids.stream().map(oficinas::buscar).flatMap(Optional::stream)
            .filter(o -> o.controle().estado() == Estado.ATIVO).toList();
        for (int i = 0; i < ativas.size(); i++)
            for (int j = i + 1; j < ativas.size(); j++)
                if (ativas.get(i).sobrepoe(ativas.get(j))) return Optional.of(new Oficina[] { ativas.get(i), ativas.get(j) });
        return Optional.empty();
    }
}
