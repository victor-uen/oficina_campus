package br.edu.oficinacampus.infraestrutura.configuracao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import java.time.*;

/**
 * Relógio usado para saber se uma oficina já aconteceu.
 * Em testes e demonstrações, {@code campus.agora=AAAA-MM-DDTHH:MM} fixa a data atual.
 */
@Configuration
public class RelogioConfiguracao {
    @Bean public Clock relogio(@Value("${campus.agora:}") String agora) {
        if (agora == null || agora.isBlank()) return Clock.systemDefaultZone();
        ZoneId zona = ZoneId.systemDefault();
        return Clock.fixed(LocalDateTime.parse(agora).atZone(zona).toInstant(), zona);
    }
}
