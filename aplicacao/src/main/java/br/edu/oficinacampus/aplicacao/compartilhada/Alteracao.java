package br.edu.oficinacampus.aplicacao.compartilhada;

import java.time.Instant;
public record Alteracao(String modulo, String registroId, String estado, long versao, Instant instante) { }
