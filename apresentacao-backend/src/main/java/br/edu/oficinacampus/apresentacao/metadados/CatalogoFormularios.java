package br.edu.oficinacampus.apresentacao.metadados;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public final class CatalogoFormularios {
    private final List<Formulario> formularios;
    public CatalogoFormularios(ObjectMapper json) throws Exception {
        List<Formulario> todos = new ArrayList<>();
        var recursos = new PathMatchingResourcePatternResolver().getResources("classpath*:formularios/*.json");
        Arrays.sort(recursos, Comparator.comparing(r -> Objects.requireNonNull(r.getFilename())));
        for (var recurso : recursos) try (var entrada = recurso.getInputStream()) { todos.addAll(json.readValue(entrada, new TypeReference<List<Formulario>>() { })); }
        formularios = List.copyOf(todos);
    }
    public List<Formulario> todos() { return formularios; }
    public Formulario obter(String id) { return formularios.stream().filter(f -> f.id().equals(id)).findFirst().orElseThrow(); }
}
