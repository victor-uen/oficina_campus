package br.edu.oficinacampus.infraestrutura.persistencia.recursos;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="FichaEquipamentoJpa") @Table(name="equipamentos")
public class FichaEquipamentoJpa extends FichaJpa {
 @Column(name="nome",nullable=false,length=80)
 public String nome;
 @Column(name="tipo",nullable=false,length=100)
 public String tipo;
 @Column(name="instrucoes",nullable=true,length=500)
 public String instrucoes;
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="equipamentos_oficinas",joinColumns=@JoinColumn(name="registro_id")) @OrderColumn(name="ordem") @Column(name="oficina_id",length=64,nullable=false)
 public List<String> oficinas=new ArrayList<>();

    public FichaEquipamentoJpa() { }
}
