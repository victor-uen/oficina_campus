package br.edu.oficinacampus.infraestrutura.persistencia;

import br.edu.oficinacampus.dominio.compartilhado.*;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/** Algoritmo ORM compartilhado; cada adaptador faz seu mapeamento explícito. */
public abstract class RepositorioJpa<T extends Cadastro<T>, J extends FichaJpa> implements Repositorio<T> {
    @PersistenceContext private EntityManager em;
    private final Class<J> tipo;
    private final Supplier<J> fabrica;
    protected RepositorioJpa(Class<J> tipo, Supplier<J> fabrica) { this.tipo = tipo; this.fabrica = fabrica; }
    protected abstract T dominio(J row);
    protected abstract void preencher(T agregado, J row);
    @Transactional(readOnly=true)
    public Optional<T> buscar(String id) { return Optional.ofNullable(em.find(tipo, id)).map(this::dominio); }
    @Transactional(readOnly=true)
    public List<T> listar() {
        var criteria = em.getCriteriaBuilder();
        var query = criteria.createQuery(tipo);
        var from = query.from(tipo);
        query.orderBy(criteria.desc(from.get("criadoEm")), criteria.asc(from.get("id")));
        return em.createQuery(query).getResultList().stream().map(this::dominio).toList();
    }
    @Transactional
    public T salvar(T agregado, boolean novo) {
        Controle controle = agregado.controle();
        J row;
        if (novo) {
            row = fabrica.get(); row.id = controle.id(); row.criadoEm = controle.criadoEm();
        } else {
            row = em.find(tipo, controle.id());
            if (row == null) throw ErroDominio.ausente();
            if (row.versao != controle.versao()) throw ErroDominio.conflito("Esta ficha mudou. Recarregue e revise antes de salvar.");
        }
        row.estado = controle.estado().name(); row.atualizadoEm = controle.atualizadoEm();
        preencher(agregado, row);
        if (novo) em.persist(row);
        em.flush();
        return dominio(row);
    }
}
