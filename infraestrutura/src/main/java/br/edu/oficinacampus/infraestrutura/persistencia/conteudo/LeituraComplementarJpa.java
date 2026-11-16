package br.edu.oficinacampus.infraestrutura.persistencia.conteudo;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="LeituraComplementarJpa") @Table(name="leituras")
public class LeituraComplementarJpa extends FichaJpa {
 @Column(name="titulo",nullable=false,length=150)
 public String titulo;
 @Column(name="autor",nullable=false,length=150)
 public String autor;
 @Column(name="tipo",nullable=false,length=100)
 public String tipo;
 @Column(name="trecho",nullable=true,length=150)
 public String trecho;
 @Column(name="obrigatoriedade",nullable=false,length=100)
 public String obrigatoriedade;
 @Column(name="ordem",nullable=false)
 public Integer ordem;
 @Column(name="oficina_id",nullable=false,length=64)
 public String oficina;

    public LeituraComplementarJpa() { }
}
