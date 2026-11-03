package br.edu.oficinacampus.infraestrutura.persistencia.cadastros;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="EspacoJpa") @Table(name="espacos")
public class EspacoJpa extends FichaJpa {
 @Column(name="nome",nullable=false,length=100)
 public String nome;
 @Column(name="localizacao",nullable=false,length=200)
 public String localizacao;
 @Column(name="descricao",nullable=true,length=2000)
 public String descricao;
 @Column(name="capacidade",nullable=false)
 public Integer capacidade;

    public EspacoJpa() { }
}
