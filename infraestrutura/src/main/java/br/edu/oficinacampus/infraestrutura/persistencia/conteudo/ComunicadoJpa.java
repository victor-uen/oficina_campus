package br.edu.oficinacampus.infraestrutura.persistencia.conteudo;

import br.edu.oficinacampus.infraestrutura.persistencia.FichaJpa;
import jakarta.persistence.*;
import java.util.*;

@Entity(name="ComunicadoJpa") @Table(name="comunicados")
public class ComunicadoJpa extends FichaJpa {
 @Column(name="titulo",nullable=false,length=100)
 public String titulo;
 @Column(name="mensagem",nullable=false,length=2000)
 public String mensagem;
 @Column(name="etiqueta",nullable=true,length=40)
 public String etiqueta;

    public ComunicadoJpa() { }
}
