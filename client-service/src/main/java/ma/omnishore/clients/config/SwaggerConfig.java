package ma.omnishore.clients.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ma.omnishore.clients.api"))
                .paths(PathSelectors.any())
                .build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()

				.title("Client Management Rest APIs")

				.description("This page lists all the rest apis for Clients Management App.")

				.version("1.0-SNAPSHOT").contact(new Contact("Amine BOUAGGAD", "www.omnidata.ma", "abouaggad@omnidata.ma"))

				.build();
	}

}