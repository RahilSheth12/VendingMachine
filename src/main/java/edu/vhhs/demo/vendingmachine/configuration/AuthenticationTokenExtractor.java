package edu.vhhs.demo.vendingmachine.configuration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.util.StringUtils;

public class AuthenticationTokenExtractor implements AuthenticationConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenExtractor.class);

	public static final String AUTH_HEADER_STRING = "Authorization";

	@Override
	public Authentication convert(HttpServletRequest request) {
		String path = request.getServletPath();
		String idToken = request.getHeader(AUTH_HEADER_STRING);

		if (!StringUtils.hasText(idToken)) {
			LOGGER.warn("No auth token found for {}", path);
			throw new PreAuthenticatedCredentialsNotFoundException("Auth Token not found");
		}

		LOGGER.debug("Auth token FOUND for {}", path);

		return new PreAuthenticatedAuthenticationToken("", idToken);
	}

}
