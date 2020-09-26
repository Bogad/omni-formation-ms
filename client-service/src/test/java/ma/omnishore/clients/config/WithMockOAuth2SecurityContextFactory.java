package ma.omnishore.clients.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockOAuth2SecurityContextFactory implements WithSecurityContextFactory<WithMockOAuth2Conext> {

	@Override
	public SecurityContext createSecurityContext(WithMockOAuth2Conext mockOAuth2Context) {

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		AccessToken accessToken = new AccessToken();
		accessToken.setName("Test User");

		KeycloakSecurityContext keycloakContext = new KeycloakSecurityContext("someTokenString", accessToken, "1111", null);
		KeycloakPrincipal<KeycloakSecurityContext> principal = new KeycloakPrincipal<KeycloakSecurityContext>("Test User", keycloakContext);
		List<String> securedActions = new ArrayList<>();
		if (!mockOAuth2Context.authorities().isEmpty()) {

			if (mockOAuth2Context.authorities().contains(" ")) {
				StringTokenizer stringTokenizer = new StringTokenizer(mockOAuth2Context.authorities(), " ");
				if (stringTokenizer.countTokens() > 0) {
					while (stringTokenizer.hasMoreTokens()) {
						securedActions.add("ROLE_".concat(stringTokenizer.nextToken()));
					}
				}
			} else {
				securedActions.add("ROLE_".concat(mockOAuth2Context.authorities()));
			}
		}

		RefreshableKeycloakSecurityContext ksc = new RefreshableKeycloakSecurityContext(null, null, "accessTokenString", accessToken, "idTokenString", null, "refreshTokenString");

		SimpleKeycloakAccount account = new SimpleKeycloakAccount(principal, new HashSet<String>(securedActions), ksc);

		context.setAuthentication(new KeycloakAuthenticationToken(account, false));

		return context;
	}
}