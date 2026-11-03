package br.edu.oficinacampus.infraestrutura.configuracao;

import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.aplicacao.cadastros.*;
import br.edu.oficinacampus.aplicacao.compartilhada.Eventos;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.*;

/** Monta os serviços do contexto Cadastros. */
@Configuration
public class CadastrosConfiguracao {
    @Bean public InstrutorServico instrutoresServico(InstrutorRepositorio repositorio, Eventos eventos) { return new InstrutorServico(repositorio, eventos); }
    @Bean public EspacoServico espacosServico(EspacoRepositorio repositorio, Eventos eventos) { return new EspacoServico(repositorio, eventos); }
    @Bean public OficinaServico oficinasServico(OficinaRepositorio repositorio, Eventos eventos, EspacoRepositorio espacos, InstrutorRepositorio instrutores, ObjectProvider<RegraOficina> regras) { return new OficinaServico(repositorio, eventos, espacos, instrutores, () -> regras.orderedStream().toList()); }
}
