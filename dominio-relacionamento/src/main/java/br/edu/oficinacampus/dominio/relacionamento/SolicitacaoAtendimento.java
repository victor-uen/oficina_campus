package br.edu.oficinacampus.dominio.relacionamento;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado atendimento; valida seus próprios valores sem consultar outros cadastros. */
public record SolicitacaoAtendimento(Controle controle, String assunto, String descricao, String categoria) implements Cadastro<SolicitacaoAtendimento> {
    public SolicitacaoAtendimento {
        Objects.requireNonNull(controle);
        assunto = Validacao.texto("Assunto", assunto, 5, 120, true);
        descricao = Validacao.texto("Descrição", descricao, 10, 1000, true);
        categoria = Validacao.opcao("Categoria", categoria, "Dúvida", "Problema no portal", "Outro");
    }
    public SolicitacaoAtendimento comControle(Controle novo) { return new SolicitacaoAtendimento(novo, assunto, descricao, categoria); }
}
