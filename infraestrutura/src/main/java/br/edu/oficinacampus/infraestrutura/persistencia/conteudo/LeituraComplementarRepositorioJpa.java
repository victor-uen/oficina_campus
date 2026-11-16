package br.edu.oficinacampus.infraestrutura.persistencia.conteudo;

import br.edu.oficinacampus.dominio.conteudo.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class LeituraComplementarRepositorioJpa extends RepositorioJpa<LeituraComplementar, LeituraComplementarJpa> implements LeituraComplementarRepositorio {
    public LeituraComplementarRepositorioJpa() { super(LeituraComplementarJpa.class, LeituraComplementarJpa::new); }
    protected LeituraComplementar dominio(LeituraComplementarJpa row) { return new LeituraComplementar(row.controle(), row.titulo, row.autor, row.tipo, row.trecho, row.obrigatoriedade, row.ordem, row.oficina); }
    protected void preencher(LeituraComplementar agregado, LeituraComplementarJpa row) {
        row.titulo = agregado.titulo();
        row.autor = agregado.autor();
        row.tipo = agregado.tipo();
        row.trecho = agregado.trecho();
        row.obrigatoriedade = agregado.obrigatoriedade();
        row.ordem = agregado.ordem();
        row.oficina = agregado.oficina();
    }
}
