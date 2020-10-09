package ma.omnishore.clients.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

/**
 * Configures the Feign OAuth2 request interceptor.
 * 
 * Created by Abouaggad on 28/11/2018.
 */
@Configuration
@ConditionalOnProperty(prefix = "keycloak", name = "auth-server-url", matchIfMissing = false)
public class OAuth2FeignAutoConfiguration {

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor();
	}
}