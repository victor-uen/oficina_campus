package br.edu.oficinacampus.apresentacao.compartilhada;

import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Mantém a rejeição de tipos incorretos do contrato anterior baseado em mapas. */
@Configuration
public class JacksonConfiguracao {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer tiposEstritos() {
        return builder -> builder.postConfigurer(json -> {
            var textos = json.coercionConfigFor(LogicalType.Textual);
            for (var origem : new CoercionInputShape[] { CoercionInputShape.Integer, CoercionInputShape.Float, CoercionInputShape.Boolean }) {
                textos.setCoercion(origem, CoercionAction.Fail);
            }
        });
    }
}
