package br.edu.oficinacampus.dominio.cadastros;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado espaço; a capacidade limita as oficinas presenciais e híbridas. */
public record Espaco(Controle controle, String nome, String localizacao, String descricao, Integer capacidade) implements Cadastro<Espaco> {
    public Espaco {
        Objects.requireNonNull(controle);
        nome = Validacao.texto("Nome", nome, 1, 100, true);
        localizacao = Validacao.texto("Localização", localizacao, 1, 200, true);
        descricao = Validacao.texto("Descrição", descricao, 0, 2000, false);
        capacidade = Validacao.inteiro("Capacidade", capacidade, 1, 1000, true);
    }
    public Espaco comControle(Controle novo) { return new Espaco(novo, nome, localizacao, descricao, capacidade); }
}
