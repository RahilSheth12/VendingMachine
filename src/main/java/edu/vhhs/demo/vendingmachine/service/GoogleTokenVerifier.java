package edu.vhhs.demo.vendingmachine.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

@Component
public class GoogleTokenVerifier {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleTokenVerifier.class);

	public String client_id;

	public GoogleTokenVerifier() {
		super();
	}

	public GoogleTokenVerifier(String clientId) {
		super();
		this.client_id = clientId;
	}

	public Payload verifyToken(String authToken) {

		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(client_id)).build();

		GoogleIdToken googleToken;

		try {
			if (authToken.startsWith("Bearer")) {
				googleToken = verifier.verify(authToken.split(" ")[1]);
			} else {
				googleToken = verifier.verify(authToken);
			}
		} catch (GeneralSecurityException | IOException e) {
			LOGGER.warn("authentication failed while decoding token");
			throw new AuthenticationServiceException("Error verifying auth token", e);
		}

		if (googleToken == null) {
			LOGGER.warn("authentication failed: no token");
			throw new BadCredentialsException("Invalid token");
		}

		return googleToken.getPayload();

	}

}
