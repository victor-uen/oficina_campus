package br.edu.oficinacampus.dominio.compartilhado;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

/** Identidade, concorrência e ciclo de vida comuns a todas as fichas. */
public record Controle(String id, Estado estado, long versao, Instant criadoEm, Instant atualizadoEm) {
    public Controle {
        Objects.requireNonNull(id); Objects.requireNonNull(estado);
        Objects.requireNonNull(criadoEm); Objects.requireNonNull(atualizadoEm);
        if (id.isBlank() || versao < 0) throw ErroDominio.validacao("Identidade ou versão inválida.");
    }
    public static Controle novo(Estado estado) {
        Instant agora = Instant.now().truncatedTo(ChronoUnit.MICROS);
        return new Controle(UUID.randomUUID().toString(), estado, 0, agora, agora);
    }
    public Controle atualizar(Estado novo) {
        return new Controle(id, novo, versao, criadoEm, Instant.now().truncatedTo(ChronoUnit.MICROS));
    }
}
