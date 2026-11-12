package br.edu.oficinacampus.dominio.relacionamento;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/**
 * Raiz do agregado manifestação de interesse. É enviada pelo portal e fica pendente
 * até a organização aceitar (o que inclui a pessoa como participante) ou recusar.
 */
public record ManifestacaoInteresse(Controle controle, String nome, String email, String telefone, String profissao, String oficina, String situacao) implements Cadastro<ManifestacaoInteresse>, VinculadoOficina {
    public ManifestacaoInteresse {
        Objects.requireNonNull(controle);
        nome = Validacao.texto("Nome", nome, 2, 100, true);
        email = Validacao.email("E-mail", email);
        telefone = Validacao.telefone("Telefone", telefone, true);
        profissao = Validacao.texto("Profissão", profissao, 2, 100, true);
        oficina = Validacao.referencia("Oficina", oficina, true);
        situacao = situacao == null || situacao.isBlank() ? PENDENTE : Validacao.opcao("Situação", situacao, PENDENTE, ACEITA, RECUSADA);
    }
    public static final String PENDENTE = "Pendente", ACEITA = "Aceita", RECUSADA = "Recusada";
    public boolean pendente() { return PENDENTE.equals(situacao); }
    public ManifestacaoInteresse comSituacao(String nova) { return new ManifestacaoInteresse(controle, nome, email, telefone, profissao, oficina, nova); }
    public ManifestacaoInteresse comControle(Controle novo) { return new ManifestacaoInteresse(novo, nome, email, telefone, profissao, oficina, situacao); }
}
