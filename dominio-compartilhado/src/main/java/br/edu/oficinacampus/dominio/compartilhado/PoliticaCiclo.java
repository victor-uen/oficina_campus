package br.edu.oficinacampus.dominio.compartilhado;

/** Strategy: edição e transições são escolhidas pelo serviço de cada contexto. */
public interface PoliticaCiclo {
    Estado inicial();
    boolean podeEditar(Estado estado);
    Estado transitar(Estado estado, String acao);
    class Padrao implements PoliticaCiclo {
        public Estado inicial() { return Estado.ATIVO; }
        public boolean podeEditar(Estado estado) { return estado != Estado.ARQUIVADO; }
        public Estado transitar(Estado estado, String acao) {
            if ("arquivar".equals(acao) && estado != Estado.ARQUIVADO) return Estado.ARQUIVADO;
            if ("restaurar".equals(acao) && estado == Estado.ARQUIVADO) return inicial();
            throw ErroDominio.conflito("A ação não é permitida no estado atual.");
        }
    }
    class Operacao extends Padrao {
        public boolean podeEditar(Estado estado) { return true; }
    }
    class Conteudo extends Padrao {
        public Estado inicial() { return Estado.OCULTO; }
        public Estado transitar(Estado estado, String acao) {
            if ("publicar".equals(acao) && estado == Estado.OCULTO) return Estado.VISIVEL;
            if ("ocultar".equals(acao) && estado == Estado.VISIVEL) return Estado.OCULTO;
            return super.transitar(estado, acao);
        }
    }
}
