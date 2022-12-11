package uz.uzcard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration() {
        Set<String> consumes = new HashSet<>();
        consumes.add(MediaType.APPLICATION_JSON_VALUE);

        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(consumes)
                .produces(consumes)
                .select()
                .apis(RequestHandlerSelectors.basePackage("uz.uzcard"))
                .build()
                .apiInfo(apiDetails());
    }
    private ApiInfo apiDetails() {
        return new ApiInfo("UzCard",
                "Api documentation for uzcard integration .",
                "4.0",
                "Best team.",
                new springfox.documentation.service.Contact("UzCard", "www.uzcard.uz", "otabekhoshimxon@gmail.com"),
                "",
                "",
                Collections.emptyList()
        );
    }




}
