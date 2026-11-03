package br.edu.oficinacampus.aplicacao.cadastros;

import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;

/**
 * Serviço de fichas que pertencem a uma oficina.
 * O catálogo é o contexto fornecedor: os demais contextos guardam a identidade
 * da oficina e esta classe confere, na aplicação, se a oficina existe e está ativa.
 */
public abstract class ServicoVinculadoOficina<T extends Cadastro<T> & VinculadoOficina> extends ServicoCadastro<T> {
    private final OficinaRepositorio oficinas;
    protected ServicoVinculadoOficina(Repositorio<T> repositorio, Eventos eventos, String funcionalidade, PoliticaCiclo ciclo, OficinaRepositorio oficinas) {
        super(repositorio, eventos, funcionalidade, ciclo);
        this.oficinas = oficinas;
    }
    @Override
    protected void conferir(T novo, T anterior) {
        String id = novo.oficina();
        if (id == null || id.isEmpty()) return;
        // Uma ficha já vinculada pode ser corrigida mesmo que a oficina tenha sido arquivada depois.
        if (anterior != null && id.equals(anterior.oficina())) return;
        ConferenciaVinculo.exigirAtivo(oficinas, id, "Oficina não encontrada. Escolha uma oficina do catálogo.",
            "A oficina escolhida está arquivada. Restaure a oficina ou escolha outra.");
    }
    /** Fichas desta funcionalidade que pertencem à oficina informada. */
    public List<T> daOficina(String oficina) {
        return listar().stream().filter(t -> oficina.equals(t.oficina())).toList();
    }
}
