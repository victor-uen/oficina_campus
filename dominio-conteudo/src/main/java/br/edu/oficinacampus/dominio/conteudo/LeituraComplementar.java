package br.edu.oficinacampus.dominio.conteudo;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/**
 * Raiz do agregado leitura complementar: livro, capítulo ou artigo indicado para a oficina.
 * Diferente do material de apoio, que é um link, a leitura indica autor, trecho e ordem sugerida.
 */
public record LeituraComplementar(Controle controle, String titulo, String autor, String tipo, String trecho, String obrigatoriedade, Integer ordem, String oficina) implements Cadastro<LeituraComplementar>, VinculadoOficina {
    public LeituraComplementar {
        Objects.requireNonNull(controle);
        titulo = Validacao.texto("Título", titulo, 3, 150, true);
        autor = Validacao.texto("Autor", autor, 2, 150, true);
        tipo = Validacao.opcao("Tipo", tipo, "Livro", "Capítulo", "Artigo", "Outro");
        trecho = Validacao.texto("Trecho indicado", trecho, 0, 150, false);
        obrigatoriedade = Validacao.opcao("Obrigatoriedade", obrigatoriedade, "Obrigatória", "Opcional");
        ordem = Validacao.inteiro("Ordem sugerida", ordem, 1, 50, true);
        oficina = Validacao.referencia("Oficina", oficina, true);
    }
    public LeituraComplementar comControle(Controle novo) { return new LeituraComplementar(novo, titulo, autor, tipo, trecho, obrigatoriedade, ordem, oficina); }
}
