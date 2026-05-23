package br.com.gabrielbcunha.sistemaraizesdonordeste.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String tokenDeSeguranca = "bearerAuth";

        return new OpenAPI()
                .info(new Info().title("Raízes do Nordeste")
                        .version("1.0.0")
                        .description("Api para gestão de unidades da franquia de restaurantes de comida nordestina Raízes do Nordeste")
                        .contact(new Contact()
                                .name("Gabriel Cunha RU:4697616")
                                .email("4697616@alunouninter.com"))
                )

                .addSecurityItem(new SecurityRequirement().addList(tokenDeSeguranca))

                .components(new Components()
                        .addSecuritySchemes(tokenDeSeguranca, new SecurityScheme()
                                .name(tokenDeSeguranca)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                );
    }
}
