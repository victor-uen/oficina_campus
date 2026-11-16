package br.edu.oficinacampus.infraestrutura.persistencia.conteudo;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="MaterialApoioJpa") @Table(name="materiais")
public class MaterialApoioJpa extends FichaJpa {
 @Column(name="titulo",nullable=false,length=100)
 public String titulo;
 @Lob @Column(name="endereco",nullable=false)
 public String endereco;
 @Column(name="descricao",nullable=true,length=300)
 public String descricao;
 @Column(name="oficina_id",nullable=false,length=64)
 public String oficina;

    public MaterialApoioJpa() { }
}
