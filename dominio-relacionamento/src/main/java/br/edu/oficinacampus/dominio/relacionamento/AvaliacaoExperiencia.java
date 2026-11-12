package br.edu.oficinacampus.dominio.relacionamento;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado avaliação; só é aceita depois que a oficina acontece (regra da aplicação). */
public record AvaliacaoExperiencia(Controle controle, Integer nota, String aspecto, String comentario, String nome, String oficina) implements Cadastro<AvaliacaoExperiencia>, VinculadoOficina {
    public AvaliacaoExperiencia {
        Objects.requireNonNull(controle);
        nota = Validacao.inteiro("Nota", nota, 1, 5, true);
        aspecto = Validacao.opcao("Aspecto", aspecto, "Conteúdo da oficina", "Condução do instrutor", "Organização e espaço");
        comentario = Validacao.texto("Comentário", comentario, 5, 500, true);
        nome = Validacao.texto("Nome opcional", nome, 2, 100, false);
        oficina = Validacao.referencia("Oficina", oficina, true);
    }
    public AvaliacaoExperiencia comControle(Controle novo) { return new AvaliacaoExperiencia(novo, nota, aspecto, comentario, nome, oficina); }
}
