package ma.omnishore.clients.config;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.base.Strings;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * The OAuth2 authentication request interceptor. It populates the {@code Authorization} header of any remote service call request based on the current authentication by setting the bearer access token.
 * 
 * Created by Abouaggad on 28/11/2017.
 */

public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

	/**
	 * The logger instance used by this class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2FeignRequestInterceptor.class);

	/**
	 * The authorization header name.
	 */
	private static final String AUTHORIZATION_HEADER = "Authorization";

	/**
	 * The {@code Bearer} token type.
	 */
	private static final String BEARER_TOKEN_TYPE = "Bearer";

	/**
	 * Creates new instance of {@link OAuth2FeignRequestInterceptor}
	 *
	 */
	public OAuth2FeignRequestInterceptor() {
		// Default Constructor
	}

	@Override
	public void apply(RequestTemplate template) {
		KeycloakSecurityContext context = getKeycloakSecurityContext();
		if (template.headers().containsKey(AUTHORIZATION_HEADER)) {
			LOGGER.warn("The Authorization token has been already set");
		} else if (context.getIdToken() == null && Strings.isNullOrEmpty(context.getTokenString())) {
			LOGGER.warn("Can not obtain existing token for request, if it is a non secured request, ignore.");
		} else {
			LOGGER.debug("Constructing Header {} for Token {}", AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE);
			template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, context.getTokenString()));
		}
	}

	protected KeycloakSecurityContext getKeycloakSecurityContext() {
		KeycloakAuthenticationToken principalToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		if (principalToken == null) {
			throw new IllegalStateException("Cannot set authorization header because there is no authenticated principal");
		}
		if (!(principalToken.getPrincipal() instanceof KeycloakPrincipal)) {
			throw new IllegalStateException(String.format("Cannot set authorization header because the principal type %s does not provide the KeycloakSecurityContext", principalToken.getPrincipal().getClass()));
		}
		return ((KeycloakPrincipal<?>) principalToken.getPrincipal()).getKeycloakSecurityContext();
	}

}