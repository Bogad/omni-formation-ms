package ma.omnishore.sales.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	

	private static final String OAUTH_NAME = "spring_oauth";
	
	@Value("${keycloak.auth-server-url}")
	private String authServer;

	@Value("${swagger-ui.secret}")
	private String clientSecret;

	@Value("${swagger-ui.client}")
	private String clientID;

	@Value("${keycloak.realm}")
	private String realm;
	
	@Value("${spring.application.name}")
	private String groupName;
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ma.omnishore.sales.api"))
                .paths(PathSelectors.any())
                .build().securitySchemes(Arrays.asList(securityScheme()))
				.securityContexts(Arrays.asList(securityContext()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()

				.title("Sales Management Rest APIs")

				.description("This page lists all the rest apis for Sales Management App.")

				.version("1.0-SNAPSHOT").contact(new Contact("Amine BOUAGGAD", "www.omnidata.ma", "abouaggad@omnidata.ma"))

				.build();
	}
	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().realm(realm).clientId(clientID).clientSecret(clientSecret).appName(groupName).scopeSeparator(" ").build();
	}

	private SecurityScheme securityScheme() {
		GrantType grantType = new AuthorizationCodeGrantBuilder().tokenEndpoint(new TokenEndpoint(authServer + "/realms/" + realm + "/protocol/openid-connect/token", groupName)).tokenRequestEndpoint(new TokenRequestEndpoint(authServer + "/realms/" + realm + "/protocol/openid-connect/auth", clientID, clientSecret)).build();
		return new OAuthBuilder().name(OAUTH_NAME).grantTypes(Arrays.asList(grantType)).build();
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(Arrays.asList(new SecurityReference(OAUTH_NAME, new AuthorizationScope[0]))).forPaths(PathSelectors.any()).build();
	}
}
