package br.edu.oficinacampus.infraestrutura.persistencia.recursos;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="KitMateriaisJpa") @Table(name="kits")
public class KitMateriaisJpa extends FichaJpa {
 @Column(name="nome",nullable=false,length=80)
 public String nome;
 @Column(name="finalidade",nullable=true,length=300)
 public String finalidade;
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="kits_componentes",joinColumns=@JoinColumn(name="registro_id")) @OrderColumn(name="ordem") @Column(name="descricao",length=100,nullable=false)
 public List<String> componentes=new ArrayList<>();
 @Column(name="oficina_id",nullable=false,length=64)
 public String oficina;

    public KitMateriaisJpa() { }
}
