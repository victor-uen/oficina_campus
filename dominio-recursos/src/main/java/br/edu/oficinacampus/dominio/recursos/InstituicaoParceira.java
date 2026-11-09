package br.edu.oficinacampus.dominio.recursos;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado instituição parceira; guarda a identidade das oficinas que apoia. */
public record InstituicaoParceira(Controle controle, String nome, String modalidade, String contato, List<String> oficinas) implements Cadastro<InstituicaoParceira>, VinculadoOficinas {
    public InstituicaoParceira {
        Objects.requireNonNull(controle);
        nome = Validacao.texto("Nome", nome, 2, 120, true);
        modalidade = Validacao.opcao("Modalidade de apoio", modalidade, "Divulgação", "Cultural", "Institucional");
        contato = Validacao.texto("Informações de contato", contato, 0, 300, false);
        oficinas = Validacao.referencias("Oficinas apoiadas", oficinas, 0);
    }
    public InstituicaoParceira comControle(Controle novo) { return new InstituicaoParceira(novo, nome, modalidade, contato, oficinas); }
}
