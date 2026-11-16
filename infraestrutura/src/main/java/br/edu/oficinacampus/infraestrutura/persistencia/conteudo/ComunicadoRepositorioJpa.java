package br.edu.oficinacampus.infraestrutura.persistencia.conteudo;

import br.edu.oficinacampus.dominio.conteudo.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class ComunicadoRepositorioJpa extends RepositorioJpa<Comunicado, ComunicadoJpa> implements ComunicadoRepositorio {
    public ComunicadoRepositorioJpa() { super(ComunicadoJpa.class, ComunicadoJpa::new); }
    protected Comunicado dominio(ComunicadoJpa row) { return new Comunicado(row.controle(), row.titulo, row.mensagem, row.etiqueta); }
    protected void preencher(Comunicado agregado, ComunicadoJpa row) {
        row.titulo = agregado.titulo();
        row.mensagem = agregado.mensagem();
        row.etiqueta = agregado.etiqueta();
    }
}
