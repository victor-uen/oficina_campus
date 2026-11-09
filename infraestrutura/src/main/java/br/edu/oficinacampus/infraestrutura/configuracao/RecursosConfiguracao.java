package br.edu.oficinacampus.infraestrutura.configuracao;

import br.edu.oficinacampus.dominio.recursos.*;
import br.edu.oficinacampus.aplicacao.recursos.*;
import br.edu.oficinacampus.aplicacao.compartilhada.Eventos;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.aplicacao.cadastros.RegraOficina;
import org.springframework.context.annotation.*;

/** Monta os serviços do contexto Recursos. */
@Configuration
public class RecursosConfiguracao {
    @Bean public FichaEquipamentoServico equipamentosServico(FichaEquipamentoRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) { return new FichaEquipamentoServico(repositorio, eventos, oficinas); }
    @Bean public InstituicaoParceiraServico parceirasServico(InstituicaoParceiraRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) { return new InstituicaoParceiraServico(repositorio, eventos, oficinas); }
    @Bean public KitMateriaisServico kitsServico(KitMateriaisRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) { return new KitMateriaisServico(repositorio, eventos, oficinas); }
    @Bean public RecursoAcessibilidadeServico acessibilidadeServico(RecursoAcessibilidadeRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) { return new RecursoAcessibilidadeServico(repositorio, eventos, oficinas); }
    @Bean public RegraOficina reservaDeEquipamentos(FichaEquipamentoRepositorio equipamentos, OficinaRepositorio oficinas) { return new ReservaEquipamentos(equipamentos, oficinas); }
}
