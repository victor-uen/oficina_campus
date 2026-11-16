package br.edu.oficinacampus.infraestrutura.persistencia.conteudo;

import br.edu.oficinacampus.dominio.conteudo.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class GuiaPreparacaoRepositorioJpa extends RepositorioJpa<GuiaPreparacao, GuiaPreparacaoJpa> implements GuiaPreparacaoRepositorio {
    public GuiaPreparacaoRepositorioJpa() { super(GuiaPreparacaoJpa.class, GuiaPreparacaoJpa::new); }
    protected GuiaPreparacao dominio(GuiaPreparacaoJpa row) { return new GuiaPreparacao(row.controle(), row.titulo, row.resumo, row.instrucoes, row.oficina); }
    protected void preencher(GuiaPreparacao agregado, GuiaPreparacaoJpa row) {
        row.titulo = agregado.titulo();
        row.resumo = agregado.resumo();
        row.instrucoes = agregado.instrucoes();
        row.oficina = agregado.oficina();
    }
}
