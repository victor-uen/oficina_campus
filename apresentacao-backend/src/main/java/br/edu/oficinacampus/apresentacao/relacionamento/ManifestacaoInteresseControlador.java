package br.edu.oficinacampus.apresentacao.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.ManifestacaoInteresse;
import br.edu.oficinacampus.aplicacao.relacionamento.ManifestacaoInteresseServico;
import br.edu.oficinacampus.dominio.compartilhado.*;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Manifestações de interesse. Elas chegam pelo portal da comunidade; aqui a organização
 * apenas consulta, aceita ou recusa. Não há cadastro manual nem edição.
 */
@RestController
@RequestMapping("/api/registros/interesses")
public class ManifestacaoInteresseControlador extends ControladorCadastro<ManifestacaoInteresse, ManifestacaoInteresseFormulario> {
    private final ManifestacaoInteresseServico servico;
    public ManifestacaoInteresseControlador(ManifestacaoInteresseServico servico) { super("interesses", servico); this.servico = servico; }
    protected ManifestacaoInteresse agregado(ManifestacaoInteresseFormulario dados, Controle controle) {
        return new ManifestacaoInteresse(controle, dados.nome(), dados.email(), dados.telefone(), dados.profissao(), dados.oficina(), dados.situacao());
    }
    protected ManifestacaoInteresseFormulario formulario(ManifestacaoInteresse agregado) {
        return new ManifestacaoInteresseFormulario(agregado.nome(), agregado.email(), agregado.telefone(), agregado.profissao(), agregado.oficina(), agregado.situacao());
    }
    @Override
    public ResponseEntity<Saida<ManifestacaoInteresseFormulario>> criar(@RequestBody Entrada<ManifestacaoInteresseFormulario> entrada) {
        throw ErroDominio.conflito("As manifestações de interesse são enviadas pelo portal da comunidade.");
    }
    /** Envio pelo portal: a manifestação sempre começa pendente. */
    public ResponseEntity<Saida<ManifestacaoInteresseFormulario>> registrar(Entrada<ManifestacaoInteresseFormulario> entrada) {
        if (entrada.dados() == null) throw ErroDominio.validacao("Informe os dados do cadastro.");
        ManifestacaoInteresseFormulario d = entrada.dados();
        if (d.situacao() != null && !d.situacao().isBlank() && !ManifestacaoInteresse.PENDENTE.equals(d.situacao()))
            throw ErroDominio.validacao("A situação é definida pela organização.");
        return super.criar(new Entrada<>(new ManifestacaoInteresseFormulario(d.nome(), d.email(), d.telefone(), d.profissao(), d.oficina(), ManifestacaoInteresse.PENDENTE), null));
    }
    @PostMapping("/{id}/aceitar")
    public Saida<ManifestacaoInteresseFormulario> aceitar(@PathVariable String id, @RequestBody Entrada<ManifestacaoInteresseFormulario> entrada) {
        return saida(servico.aceitar(id, entrada.versao()));
    }
    @PostMapping("/{id}/recusar")
    public Saida<ManifestacaoInteresseFormulario> recusar(@PathVariable String id, @RequestBody Entrada<ManifestacaoInteresseFormulario> entrada) {
        return saida(servico.recusar(id, entrada.versao()));
    }
}
