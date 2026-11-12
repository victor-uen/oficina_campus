package br.edu.oficinacampus.infraestrutura.persistencia.relacionamento;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="AvaliacaoExperienciaJpa") @Table(name="avaliacoes")
public class AvaliacaoExperienciaJpa extends FichaJpa {
 @Column(name="nota",nullable=false)
 public Integer nota;
 @Column(name="aspecto",nullable=false,length=100)
 public String aspecto;
 @Column(name="comentario",nullable=false,length=500)
 public String comentario;
 @Column(name="nome",nullable=true,length=100)
 public String nome;
 @Column(name="oficina_id",nullable=false,length=64)
 public String oficina;

    public AvaliacaoExperienciaJpa() { }
}
