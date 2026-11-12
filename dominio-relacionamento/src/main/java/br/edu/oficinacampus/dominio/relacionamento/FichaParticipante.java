package br.edu.oficinacampus.dominio.relacionamento;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado participante; o limite de vagas da oficina é conferido pela aplicação. */
public record FichaParticipante(Controle controle, String nome, String email, String telefone, String profissao, String observacoes, String oficina) implements Cadastro<FichaParticipante>, VinculadoOficina {
    public FichaParticipante {
        Objects.requireNonNull(controle);
        nome = Validacao.texto("Nome", nome, 2, 100, true);
        email = Validacao.email("E-mail", email);
        telefone = Validacao.telefone("Telefone", telefone, true);
        profissao = Validacao.texto("Profissão", profissao, 2, 100, true);
        observacoes = Validacao.texto("Observações", observacoes, 0, 500, false);
        oficina = Validacao.referencia("Oficina", oficina, true);
    }
    public FichaParticipante comControle(Controle novo) { return new FichaParticipante(novo, nome, email, telefone, profissao, observacoes, oficina); }
}
