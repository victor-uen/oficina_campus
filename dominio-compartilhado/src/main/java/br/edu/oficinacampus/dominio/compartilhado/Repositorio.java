package br.edu.oficinacampus.dominio.compartilhado;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T extends Cadastro<T>> {
    Optional<T> buscar(String id);
    List<T> listar();
    T salvar(T agregado, boolean novo);
}
