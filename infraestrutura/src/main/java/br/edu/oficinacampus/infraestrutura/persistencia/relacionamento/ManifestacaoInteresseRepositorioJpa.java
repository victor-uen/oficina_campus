package br.edu.oficinacampus.infraestrutura.persistencia.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class ManifestacaoInteresseRepositorioJpa extends RepositorioJpa<ManifestacaoInteresse, ManifestacaoInteresseJpa> implements ManifestacaoInteresseRepositorio {
    public ManifestacaoInteresseRepositorioJpa() { super(ManifestacaoInteresseJpa.class, ManifestacaoInteresseJpa::new); }
    protected ManifestacaoInteresse dominio(ManifestacaoInteresseJpa row) { return new ManifestacaoInteresse(row.controle(), row.nome, row.email, row.telefone, row.profissao, row.oficina, row.situacao); }
    protected void preencher(ManifestacaoInteresse agregado, ManifestacaoInteresseJpa row) {
        row.nome = agregado.nome();
        row.email = agregado.email();
        row.telefone = agregado.telefone();
        row.profissao = agregado.profissao();
        row.oficina = agregado.oficina();
        row.situacao = agregado.situacao();
    }
}
