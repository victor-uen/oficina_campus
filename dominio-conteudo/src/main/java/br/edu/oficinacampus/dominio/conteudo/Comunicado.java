package br.edu.oficinacampus.dominio.conteudo;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado comunicado; valida seus próprios valores sem consultar outros cadastros. */
public record Comunicado(Controle controle, String titulo, String mensagem, String etiqueta) implements Cadastro<Comunicado> {
    public Comunicado {
        Objects.requireNonNull(controle);
        titulo = Validacao.texto("Título", titulo, 3, 100, true);
        mensagem = Validacao.texto("Mensagem", mensagem, 10, 2000, true);
        etiqueta = Validacao.texto("Etiqueta informativa", etiqueta, 0, 40, false);
    }
    public Comunicado comControle(Controle novo) { return new Comunicado(novo, titulo, mensagem, etiqueta); }
}
