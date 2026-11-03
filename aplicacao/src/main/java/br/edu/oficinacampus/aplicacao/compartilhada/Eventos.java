package br.edu.oficinacampus.aplicacao.compartilhada;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/** Observer: consumidores independentes recebem alterações já confirmadas. */
public final class Eventos {
    private final CopyOnWriteArrayList<Consumer<Alteracao>> observadores = new CopyOnWriteArrayList<>();
    public void observar(Consumer<Alteracao> observador) { observadores.add(observador); }
    public void publicar(Alteracao alteracao) { observadores.forEach(o -> o.accept(alteracao)); }
}
