package br.edu.oficinacampus.infraestrutura.persistencia.conteudo;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="GuiaPreparacaoJpa") @Table(name="guias")
public class GuiaPreparacaoJpa extends FichaJpa {
 @Column(name="titulo",nullable=false,length=100)
 public String titulo;
 @Column(name="resumo",nullable=true,length=200)
 public String resumo;
 @Column(name="instrucoes",nullable=false,length=4000)
 public String instrucoes;
 @Column(name="oficina_id",nullable=false,length=64)
 public String oficina;

    public GuiaPreparacaoJpa() { }
}
