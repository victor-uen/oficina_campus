package br.edu.oficinacampus.dominio.compartilhado;

import java.net.URI;
import java.util.List;
import java.util.Locale;

/** Validações de valores reutilizáveis, sem metamodelo de formulários. */
public final class Validacao {
    private Validacao() { }
    public static String texto(String nome, String valor, int minimo, int maximo, boolean obrigatorio) {
        if (valor == null || valor.isBlank()) {
            if (obrigatorio) throw ErroDominio.validacao(nome + " é obrigatório.");
            return "";
        }
        String normal = valor.trim();
        if (normal.length() < minimo || maximo > 0 && normal.length() > maximo)
            throw ErroDominio.validacao(nome + " deve ter " + minimo + " a " + maximo + " caracteres.");
        return normal;
    }
    public static String linha(String nome, String valor, int minimo, int maximo, boolean obrigatorio) {
        String normal = texto(nome, valor, minimo, maximo, obrigatorio);
        if (normal.contains("\n") || normal.contains("\r")) throw ErroDominio.validacao(nome + " deve ocupar uma única linha.");
        return normal;
    }
    public static String opcao(String nome, String valor, String... opcoes) {
        String normal = texto(nome, valor, 1, 100, true);
        if (!List.of(opcoes).contains(normal)) throw ErroDominio.validacao(nome + " possui uma opção inválida.");
        return normal;
    }
    public static Integer inteiro(String nome, Integer valor, int minimo, int maximo, boolean obrigatorio) {
        if (valor == null) {
            if (obrigatorio) throw ErroDominio.validacao(nome + " é obrigatório.");
            return null;
        }
        if (valor < minimo || valor > maximo) throw ErroDominio.validacao(nome + " deve estar entre " + minimo + " e " + maximo + ".");
        return valor;
    }
    public static String email(String nome, String valor) {
        String normal = texto(nome, valor, 1, 254, true);
        if (!normal.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) throw ErroDominio.validacao(nome + " deve ter formato de e-mail.");
        return normal;
    }
    public static String endereco(String nome, String valor) {
        String normal = texto(nome, valor, 1, 0, true);
        try {
            URI uri = URI.create(normal);
            if (!List.of("http", "https").contains(uri.getScheme() == null ? "" : uri.getScheme().toLowerCase(Locale.ROOT)) || uri.getHost() == null) throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) { throw ErroDominio.validacao(nome + " deve ser uma URL HTTP ou HTTPS com host."); }
        return normal;
    }
    /** Identidade de outro agregado: uma única palavra, sem espaços, com até 64 caracteres. */
    public static String referencia(String nome, String valor, boolean obrigatorio) {
        String normal = texto(nome, valor, 1, 64, obrigatorio);
        if (!normal.isEmpty() && !normal.matches("[A-Za-z0-9_-]+")) throw ErroDominio.validacao(nome + " deve indicar uma ficha existente.");
        return normal;
    }
    /** Lista de identidades de outros agregados, sem repetição e com ordem preservada. */
    public static List<String> referencias(String nome, List<String> valores, int minimo) {
        if (valores == null && minimo == 0) return List.of();
        if (valores == null || valores.size() < minimo) throw ErroDominio.validacao(nome + ": escolha pelo menos " + minimo + ".");
        List<String> normais = valores.stream().map(v -> referencia(nome, v, true)).toList();
        if (normais.stream().distinct().count() != normais.size()) throw ErroDominio.validacao(nome + " não pode repetir o mesmo registro.");
        return normais;
    }
    /** Telefone com DDD: números, espaços, parênteses, hífen ou "+", com 8 a 15 dígitos. */
    public static String telefone(String nome, String valor, boolean obrigatorio) {
        String normal = texto(nome, valor, 8, 20, obrigatorio);
        if (normal.isEmpty()) return normal;
        long digitos = normal.chars().filter(Character::isDigit).count();
        if (!normal.matches("[0-9()+\\- ]+") || digitos < 8 || digitos > 15)
            throw ErroDominio.validacao(nome + " deve ter de 8 a 15 dígitos, com DDD. Ex.: (81) 99999-0000.");
        return normal;
    }
    /** Data e horário locais no formato AAAA-MM-DDTHH:MM, como o campo datetime-local do navegador. */
    public static String dataHora(String nome, String valor) {
        String normal = texto(nome, valor, 16, 16, true);
        try {
            return java.time.LocalDateTime.parse(normal).toString();
        } catch (java.time.format.DateTimeParseException e) {
            throw ErroDominio.validacao(nome + " deve ser uma data e um horário válidos (AAAA-MM-DDTHH:MM).");
        }
    }
    public static List<String> componentes(List<String> valores) {
        if (valores == null || valores.isEmpty()) throw ErroDominio.validacao("Componentes deve conter pelo menos um componente.");
        return valores.stream().map(v -> texto("Componente", v, 1, 100, true)).toList();
    }
}
