package br.edu.oficinacampus.infraestrutura.persistencia.relacionamento;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="ManifestacaoInteresseJpa") @Table(name="interesses")
public class ManifestacaoInteresseJpa extends FichaJpa {
 @Column(name="nome",nullable=false,length=100)
 public String nome;
 @Column(name="email",nullable=false,length=254)
 public String email;
 @Column(name="telefone",nullable=false,length=20)
 public String telefone;
 @Column(name="profissao",nullable=false,length=100)
 public String profissao;
 @Column(name="oficina_id",nullable=false,length=64)
 public String oficina;
 @Column(name="situacao",nullable=true,length=100)
 public String situacao;

    public ManifestacaoInteresseJpa() { }
}
