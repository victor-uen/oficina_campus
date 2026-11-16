package br.edu.oficinacampus.infraestrutura.configuracao;

import br.edu.oficinacampus.dominio.conteudo.*;
import br.edu.oficinacampus.aplicacao.conteudo.*;
import br.edu.oficinacampus.aplicacao.compartilhada.Eventos;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import org.springframework.context.annotation.*;

/** Monta os serviços do contexto Conteúdo. */
@Configuration
public class ConteudoConfiguracao {
    @Bean public MaterialApoioServico materiaisServico(MaterialApoioRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) { return new MaterialApoioServico(repositorio, eventos, oficinas); }
    @Bean public GuiaPreparacaoServico guiasServico(GuiaPreparacaoRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) { return new GuiaPreparacaoServico(repositorio, eventos, oficinas); }
    @Bean public LeituraComplementarServico leiturasServico(LeituraComplementarRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) { return new LeituraComplementarServico(repositorio, eventos, oficinas); }
    @Bean public ComunicadoServico comunicadosServico(ComunicadoRepositorio repositorio, Eventos eventos) { return new ComunicadoServico(repositorio, eventos); }
}
