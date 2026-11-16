package br.edu.oficinacampus.dominio.conteudo;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/** Raiz do agregado material de apoio; guarda a identidade da oficina a que pertence. */
public record MaterialApoio(Controle controle, String titulo, String endereco, String descricao, String oficina) implements Cadastro<MaterialApoio>, VinculadoOficina {
    public MaterialApoio {
        Objects.requireNonNull(controle);
        titulo = Validacao.texto("Título", titulo, 3, 100, true);
        endereco = Validacao.endereco("Endereço do material", endereco);
        descricao = Validacao.texto("Descrição", descricao, 0, 300, false);
        oficina = Validacao.referencia("Oficina", oficina, true);
    }
    public MaterialApoio comControle(Controle novo) { return new MaterialApoio(novo, titulo, endereco, descricao, oficina); }
}
