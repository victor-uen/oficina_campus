package br.edu.oficinacampus.dominio.cadastros;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado instrutor; valida seus próprios valores sem consultar outros cadastros. */
public record Instrutor(Controle controle, String nome, String apresentacao, String especialidade, String contato) implements Cadastro<Instrutor> {
    public Instrutor {
        Objects.requireNonNull(controle);
        nome = Validacao.texto("Nome", nome, 1, 100, true);
        apresentacao = Validacao.texto("Apresentação", apresentacao, 1, 2000, true);
        especialidade = Validacao.texto("Especialidade", especialidade, 1, 150, true);
        contato = Validacao.texto("Contato profissional", contato, 0, 254, false);
    }
    public Instrutor comControle(Controle novo) { return new Instrutor(novo, nome, apresentacao, especialidade, contato); }
}
