package br.edu.oficinacampus.infraestrutura.persistencia.relacionamento;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="FichaParticipanteJpa") @Table(name="participantes")
public class FichaParticipanteJpa extends FichaJpa {
 @Column(name="nome",nullable=false,length=100)
 public String nome;
 @Column(name="email",nullable=false,length=254)
 public String email;
 @Column(name="telefone",nullable=false,length=20)
 public String telefone;
 @Column(name="profissao",nullable=false,length=100)
 public String profissao;
 @Column(name="observacoes",nullable=true,length=500)
 public String observacoes;
 @Column(name="oficina_id",nullable=false,length=64)
 public String oficina;

    public FichaParticipanteJpa() { }
}
