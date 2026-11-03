package br.edu.oficinacampus.aplicacao.compartilhada;

import java.util.List;
public record Pagina<T>(List<T> items, long total, int offset, int limite) {
    public Pagina { items = List.copyOf(items); }
}
