package br.edu.oficinacampus.infraestrutura.persistencia.cadastros;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="OficinaJpa") @Table(name="oficinas")
public class OficinaJpa extends FichaJpa {
 @Column(name="titulo",nullable=false,length=100)
 public String titulo;
 @Column(name="apresentacao",nullable=false,length=2000)
 public String apresentacao;
 @Column(name="modalidade",nullable=false,length=100)
 public String modalidade;
 @Column(name="duracao",nullable=false)
 public Integer duracao;
 @Column(name="limite",nullable=false)
 public Integer limite;
 @Column(name="data_hora",nullable=false,length=16)
 public String data;
 @Column(name="espaco_id",nullable=true,length=64)
 public String espaco;
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="oficinas_instrutores",joinColumns=@JoinColumn(name="registro_id")) @OrderColumn(name="ordem") @Column(name="instrutor_id",length=64,nullable=false)
 public List<String> instrutores=new ArrayList<>();

    public OficinaJpa() { }
}
