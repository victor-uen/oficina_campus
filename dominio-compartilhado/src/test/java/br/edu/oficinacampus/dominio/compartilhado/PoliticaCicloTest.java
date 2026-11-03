package br.edu.oficinacampus.dominio.compartilhado;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PoliticaCicloTest {
    @Test void preservarCiclosIndependentes() {
        var comum = new PoliticaCiclo.Padrao();
        assertEquals(Estado.ATIVO, comum.inicial());
        assertEquals(Estado.ARQUIVADO, comum.transitar(Estado.ATIVO, "arquivar"));
        assertFalse(comum.podeEditar(Estado.ARQUIVADO));
        assertEquals(Estado.ATIVO, comum.transitar(Estado.ARQUIVADO, "restaurar"));
        assertThrows(ErroDominio.class, () -> comum.transitar(Estado.ATIVO, "publicar"));
        assertTrue(new PoliticaCiclo.Operacao().podeEditar(Estado.ARQUIVADO));
    }
    @Test void restauracaoDeConteudoExigeNovaPublicacao() {
        var conteudo = new PoliticaCiclo.Conteudo();
        assertEquals(Estado.OCULTO, conteudo.inicial());
        assertEquals(Estado.VISIVEL, conteudo.transitar(Estado.OCULTO, "publicar"));
        assertEquals(Estado.OCULTO, conteudo.transitar(Estado.VISIVEL, "ocultar"));
        assertEquals(Estado.ARQUIVADO, conteudo.transitar(Estado.VISIVEL, "arquivar"));
        assertEquals(Estado.OCULTO, conteudo.transitar(Estado.ARQUIVADO, "restaurar"));
        assertThrows(ErroDominio.class, () -> conteudo.transitar(Estado.ARQUIVADO, "publicar"));
    }
    @Test void componentesTemCopiaDefensivaEOrdem() {
        var entrada = new ArrayList<>(List.of(" Papel ", "Caneta"));
        var saida = Validacao.componentes(entrada);
        entrada.clear();
        assertEquals(List.of("Papel", "Caneta"), saida);
        assertThrows(UnsupportedOperationException.class, () -> saida.add("Borracha"));
    }
    @Test void referenciaGuardaSomenteAIdentidade() {
        assertEquals("a1b2-c3", Validacao.referencia("Oficina", " a1b2-c3 ", true));
        assertEquals("", Validacao.referencia("Oficina", null, false));
        assertThrows(ErroDominio.class, () -> Validacao.referencia("Oficina", null, true));
        assertThrows(ErroDominio.class, () -> Validacao.referencia("Oficina", "duas palavras", true));
        assertThrows(ErroDominio.class, () -> Validacao.referencia("Oficina", "x".repeat(65), true));
    }
}
