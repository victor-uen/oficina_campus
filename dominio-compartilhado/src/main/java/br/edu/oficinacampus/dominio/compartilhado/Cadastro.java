package br.edu.oficinacampus.dominio.compartilhado;

/** Compartilha somente o ciclo de vida; os atributos de negócio ficam nos agregados. */
public interface Cadastro<T extends Cadastro<T>> {
    Controle controle();
    T comControle(Controle controle);
}
