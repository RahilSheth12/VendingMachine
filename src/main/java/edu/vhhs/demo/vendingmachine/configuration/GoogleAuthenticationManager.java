package edu.vhhs.demo.vendingmachine.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import edu.vhhs.demo.vendingmachine.entity.UserDetails;
import edu.vhhs.demo.vendingmachine.entity.UserRole;
import edu.vhhs.demo.vendingmachine.repository.UserDetailsRepository;
import edu.vhhs.demo.vendingmachine.service.GoogleTokenVerifier;

public class GoogleAuthenticationManager implements AuthenticationManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleAuthenticationManager.class);

	private final UserDetailsRepository userDetailsRepository;

	private final GoogleTokenVerifier googleTokenVerifier;

	public GoogleAuthenticationManager(UserDetailsRepository userDetailsRepository,
			GoogleTokenVerifier googleTokenVerifier) {
		this.userDetailsRepository = userDetailsRepository;
		this.googleTokenVerifier = googleTokenVerifier;
	}

	@Override
	public Authentication authenticate(final Authentication authentication) {
		LOGGER.info("start authentication...");

		Payload payload = googleTokenVerifier.verifyToken((String) authentication.getCredentials());

		String email = payload.getEmail();
		String name = (String) payload.get("name");
		String picture = (String) payload.get("picture");

		try {
			LOGGER.debug(payload.toPrettyString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.warn("fail to read token payload!");
			throw new BadCredentialsException("Invalid token payload");
		}

		Optional<UserDetails> optUserDetails = userDetailsRepository.findByEmail(email);

		UserDetails userDetails;

		if (optUserDetails.isEmpty()) {
			userDetails = new UserDetails(name, email, picture);
			userDetails.setRole(UserRole.USER_ROLE);
			userDetailsRepository.save(userDetails);
		} else {
			userDetails = optUserDetails.get();
			userDetails.setName(name);
			userDetails.setEmail(email);
			userDetails.setPicture(picture);
			userDetailsRepository.save(userDetails);
		}

		return new UsernamePasswordAuthenticationToken(userDetails, (String) authentication.getCredentials(),
				Arrays.asList(new SimpleGrantedAuthority(userDetails.getRole().name())));
	}

}
