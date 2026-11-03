package br.edu.oficinacampus.dominio.cadastros;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Raiz do agregado oficina. Guarda a identidade do espaço e dos instrutores;
 * a existência, o estado e a agenda deles são conferidos pela aplicação.
 */
public record Oficina(Controle controle, String titulo, String apresentacao, String modalidade, Integer duracao, Integer limite, String data, String espaco, List<String> instrutores) implements Cadastro<Oficina> {
    public Oficina {
        Objects.requireNonNull(controle);
        titulo = Validacao.texto("Título", titulo, 1, 100, true);
        apresentacao = Validacao.texto("Apresentação", apresentacao, 1, 2000, true);
        modalidade = Validacao.opcao("Modalidade", modalidade, "Presencial", "Online", "Híbrida");
        duracao = Validacao.inteiro("Duração em minutos", duracao, 1, 480, true);
        limite = Validacao.inteiro("Limite de participantes", limite, 1, 1000, true);
        data = Validacao.dataHora("Data e horário", data);
        espaco = Validacao.referencia("Espaço", espaco, false);
        instrutores = Validacao.referencias("Instrutores", instrutores, 1);
        if (espaco.isEmpty() && !"Online".equals(modalidade)) throw ErroDominio.validacao("Espaço é obrigatório, exceto quando Modalidade for Online.");
    }
    /** Início da oficina. */
    public LocalDateTime inicio() { return LocalDateTime.parse(data); }
    /** Fim previsto: início mais a duração. */
    public LocalDateTime fim() { return inicio().plusMinutes(duracao); }
    /** A oficina já aconteceu quando o horário de término passou. */
    public boolean realizada(LocalDateTime agora) { return !fim().isAfter(agora); }
    /** Duas oficinas se sobrepõem quando uma começa antes de a outra terminar. */
    public boolean sobrepoe(Oficina outra) { return inicio().isBefore(outra.fim()) && outra.inicio().isBefore(fim()); }
    /** Presenciais e híbridas ocupam o espaço; online não. */
    public boolean usaEspaco() { return !"Online".equals(modalidade) && !espaco.isEmpty(); }
    public Oficina comControle(Controle novo) { return new Oficina(novo, titulo, apresentacao, modalidade, duracao, limite, data, espaco, instrutores); }
}
