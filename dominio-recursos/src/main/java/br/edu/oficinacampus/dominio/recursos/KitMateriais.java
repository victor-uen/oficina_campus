package br.edu.oficinacampus.dominio.recursos;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado kit de materiais; guarda a identidade da oficina a que pertence. */
public record KitMateriais(Controle controle, String nome, String finalidade, List<String> componentes, String oficina) implements Cadastro<KitMateriais>, VinculadoOficina {
    public KitMateriais {
        Objects.requireNonNull(controle);
        nome = Validacao.texto("Nome", nome, 2, 80, true);
        finalidade = Validacao.texto("Finalidade", finalidade, 0, 300, false);
        componentes = Validacao.componentes(componentes);
        oficina = Validacao.referencia("Oficina", oficina, true);
    }
    public KitMateriais comControle(Controle novo) { return new KitMateriais(novo, nome, finalidade, componentes, oficina); }
}
