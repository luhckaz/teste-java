package gov.goias.smart.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

    @Value("${project.version}")
    private String projectVersion;

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("gov.goias.smart"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        /*
         * Exemplo de Dados, ADEQUE ao seu PROJETO e EQUIPE.
         */
        Contact contact = new Contact("SEDI - STI - SubSecretaria de Tecnologia da Informação"
                , "https://www.ti.go.gov.br/catalogo-de-servicos/gest%C3%A3o-e-governan%C3%A7a-de-ti.html"
                , "henrique.rocha@goias.gov.br");

        return new ApiInfoBuilder()
                .title("SMART - Sistema Modelo de ARquitetura e Tecnologia")
                .description("API do Sistema Modelo de Arquitetura e Tecnologia. " +
                        "Para exemplificar o uso do OpenAPI(Swagger) versão 3, que é o modelo de Interoperabilidade utilizado no Governo de Goiás.")
                .termsOfServiceUrl("https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md")
                .contact(contact)
                .license("The Apache License, Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
                .version(projectVersion)
                .extensions(new ArrayList<>())
                .build();
    }
}