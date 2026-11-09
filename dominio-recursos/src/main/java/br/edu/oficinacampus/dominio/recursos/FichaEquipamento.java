package br.edu.oficinacampus.dominio.recursos;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;

/**
 * Raiz do agregado equipamento. Pertence à instituição e guarda a identidade
 * das oficinas em que está reservado; conflitos de horário são conferidos pela aplicação.
 */
public record FichaEquipamento(Controle controle, String nome, String tipo, String instrucoes, List<String> oficinas) implements Cadastro<FichaEquipamento>, VinculadoOficinas {
    public FichaEquipamento {
        Objects.requireNonNull(controle);
        nome = Validacao.texto("Nome", nome, 2, 80, true);
        tipo = Validacao.opcao("Tipo", tipo, "Audiovisual", "Informática", "Ferramenta", "Outro");
        instrucoes = Validacao.texto("Instruções de uso", instrucoes, 0, 500, false);
        oficinas = Validacao.referencias("Reservado para", oficinas, 0);
    }
    public FichaEquipamento comControle(Controle novo) { return new FichaEquipamento(novo, nome, tipo, instrucoes, oficinas); }
}
