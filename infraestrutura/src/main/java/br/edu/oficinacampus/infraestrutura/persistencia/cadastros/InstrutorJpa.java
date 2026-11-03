package br.edu.oficinacampus.infraestrutura.persistencia.cadastros;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="InstrutorJpa") @Table(name="instrutores")
public class InstrutorJpa extends FichaJpa {
 @Column(name="nome",nullable=false,length=100)
 public String nome;
 @Column(name="apresentacao",nullable=false,length=2000)
 public String apresentacao;
 @Column(name="especialidade",nullable=false,length=150)
 public String especialidade;
 @Column(name="contato",nullable=true,length=254)
 public String contato;

    public InstrutorJpa() { }
}
