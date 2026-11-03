package br.edu.oficinacampus.infraestrutura.configuracao;

import br.edu.oficinacampus.aplicacao.compartilhada.*;
import org.springframework.context.annotation.*;

@Configuration
public class EventosConfiguracao {
    @Bean public Eventos eventos() { return new Eventos(); }
    @Bean public Historico historico(Eventos eventos) {
        Historico historico = new Historico();
        eventos.observar(historico::registrar);
        return historico;
    }
}
