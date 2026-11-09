package br.edu.oficinacampus.dominio.recursos;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado recurso de acessibilidade; guarda a identidade da oficina a que pertence. */
public record RecursoAcessibilidade(Controle controle, String nome, String categoria, String orientacao, String oficina) implements Cadastro<RecursoAcessibilidade>, VinculadoOficina {
    public RecursoAcessibilidade {
        Objects.requireNonNull(controle);
        nome = Validacao.texto("Nome", nome, 2, 80, true);
        categoria = Validacao.opcao("Categoria", categoria, "Visual", "Auditiva", "Mobilidade", "Comunicação", "Outra");
        orientacao = Validacao.texto("Orientação de utilização", orientacao, 1, 500, true);
        oficina = Validacao.referencia("Oficina", oficina, true);
    }
    public RecursoAcessibilidade comControle(Controle novo) { return new RecursoAcessibilidade(novo, nome, categoria, orientacao, oficina); }
}
