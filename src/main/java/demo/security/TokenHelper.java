package demo.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;

import org.paseto4j.commons.PasetoException;
import org.paseto4j.commons.SecretKey;
import org.paseto4j.commons.Version;
import org.paseto4j.version3.Paseto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class TokenHelper {

	@Value("${app.token.secret}")
	String secret;

	@Value("${app.token.footer}")
	String footer;

	private static final Logger log = LoggerFactory.getLogger(TokenHelper.class);

	public Optional<String> encrypt(AppToken token) {
		String payload;
		try {
			payload = mapper().writeValueAsString(token);
			return Optional.of(Paseto.encrypt(key(), payload, footer));
		} catch (PasetoException | JsonProcessingException e) {
			return Optional.empty();
		}
	}

	public Optional<AppToken> decrypt(String token) {
		try {
			String payload = Paseto.decrypt(key(), token, footer);
			AppToken appToken = mapper().readValue(payload, AppToken.class);
			if (Instant.now().isAfter(appToken.getExpiresDate())) {
				log.error("token expired");
				return Optional.empty();
			}
			return Optional.of(appToken);
		} catch (Exception e) {
			log.error("Exception " + e);
			return Optional.empty();
		}
	}

	private SecretKey key() {
		return new SecretKey(this.secret.getBytes(StandardCharsets.UTF_8), Version.V3);
	}

	private JsonMapper mapper() {
		JsonMapper mapper = new JsonMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}
