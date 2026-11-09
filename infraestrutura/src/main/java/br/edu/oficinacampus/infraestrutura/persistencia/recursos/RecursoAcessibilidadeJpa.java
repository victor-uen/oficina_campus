package br.edu.oficinacampus.infraestrutura.persistencia.recursos;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="RecursoAcessibilidadeJpa") @Table(name="acessibilidade")
public class RecursoAcessibilidadeJpa extends FichaJpa {
 @Column(name="nome",nullable=false,length=80)
 public String nome;
 @Column(name="categoria",nullable=false,length=100)
 public String categoria;
 @Column(name="orientacao",nullable=false,length=500)
 public String orientacao;
 @Column(name="oficina_id",nullable=false,length=64)
 public String oficina;

    public RecursoAcessibilidadeJpa() { }
}
