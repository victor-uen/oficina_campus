package br.edu.oficinacampus.dominio.compartilhado;

/** Falha de negócio; a tradução para HTTP pertence ao adaptador web. */
public final class ErroDominio extends RuntimeException {
    public enum Motivo { VALIDACAO, AUSENTE, CONFLITO }
    private final Motivo motivo;
    public ErroDominio(Motivo motivo, String mensagem) { super(mensagem); this.motivo = motivo; }
    public Motivo motivo() { return motivo; }
    public static ErroDominio validacao(String mensagem) { return new ErroDominio(Motivo.VALIDACAO, mensagem); }
    public static ErroDominio ausente() { return new ErroDominio(Motivo.AUSENTE, "Ficha não encontrada."); }
    public static ErroDominio conflito(String mensagem) { return new ErroDominio(Motivo.CONFLITO, mensagem); }
}
