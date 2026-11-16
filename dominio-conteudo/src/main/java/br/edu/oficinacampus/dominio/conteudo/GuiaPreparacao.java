package br.edu.oficinacampus.dominio.conteudo;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado guia de preparação; guarda a identidade da oficina a que pertence. */
public record GuiaPreparacao(Controle controle, String titulo, String resumo, String instrucoes, String oficina) implements Cadastro<GuiaPreparacao>, VinculadoOficina {
    public GuiaPreparacao {
        Objects.requireNonNull(controle);
        titulo = Validacao.texto("Título", titulo, 3, 100, true);
        resumo = Validacao.texto("Resumo", resumo, 0, 200, false);
        instrucoes = Validacao.texto("Instruções", instrucoes, 10, 4000, true);
        oficina = Validacao.referencia("Oficina", oficina, true);
    }
    public GuiaPreparacao comControle(Controle novo) { return new GuiaPreparacao(novo, titulo, resumo, instrucoes, oficina); }
}
