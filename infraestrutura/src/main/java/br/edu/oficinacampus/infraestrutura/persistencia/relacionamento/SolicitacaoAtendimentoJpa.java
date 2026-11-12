package br.edu.oficinacampus.infraestrutura.persistencia.relacionamento;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="SolicitacaoAtendimentoJpa") @Table(name="atendimentos")
public class SolicitacaoAtendimentoJpa extends FichaJpa {
 @Column(name="assunto",nullable=false,length=120)
 public String assunto;
 @Column(name="descricao",nullable=false,length=1000)
 public String descricao;
 @Column(name="categoria",nullable=false,length=100)
 public String categoria;

    public SolicitacaoAtendimentoJpa() { }
}
