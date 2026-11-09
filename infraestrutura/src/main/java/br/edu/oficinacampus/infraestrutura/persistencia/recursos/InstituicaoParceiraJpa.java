package br.edu.oficinacampus.infraestrutura.persistencia.recursos;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="InstituicaoParceiraJpa") @Table(name="parceiras")
public class InstituicaoParceiraJpa extends FichaJpa {
 @Column(name="nome",nullable=false,length=120)
 public String nome;
 @Column(name="modalidade",nullable=false,length=100)
 public String modalidade;
 @Column(name="contato",nullable=true,length=300)
 public String contato;
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="parceiras_oficinas",joinColumns=@JoinColumn(name="registro_id")) @OrderColumn(name="ordem") @Column(name="oficina_id",length=64,nullable=false)
 public List<String> oficinas=new ArrayList<>();

    public InstituicaoParceiraJpa() { }
}
