package br.edu.oficinacampus.infraestrutura.persistencia;

import br.edu.oficinacampus.dominio.compartilhado.*;
import jakarta.persistence.*;
import java.time.Instant;

/** Metadados ORM; os atributos de negócio pertencem às entidades específicas. */
@MappedSuperclass
public abstract class FichaJpa {
    @Id public String id;
    @Column(nullable=false) public String estado;
    @Version public Long versao;
    @Column(nullable=false) public Instant criadoEm;
    @Column(nullable=false) public Instant atualizadoEm;
    public Controle controle() { return new Controle(id, Estado.valueOf(estado), versao, criadoEm, atualizadoEm); }
}
