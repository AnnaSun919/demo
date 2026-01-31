package demo.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import demo.security.TokenHelper;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private static final String HEADER = "Authorization";
	private static final String PREFIX = "Bearer ";

	private TokenHelper tokenHelper;

	public TokenAuthenticationFilter(TokenHelper tokenHelper) {
		this.tokenHelper = tokenHelper;
	}

	private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	@Nullable
	public static String getBearerToken(HttpServletRequest request) {
		return StringUtils.defaultString(request.getHeader(HEADER)).replace(PREFIX, "");
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		if (!checkToken(request, response)) {
			SecurityContextHolder.clearContext();
			chain.doFilter(request, response);
			return;
		}

		String tokenStr = getBearerToken(request);

		if (tokenStr != null) {
			tokenHelper.decrypt(tokenStr).ifPresent(appToken -> {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(appToken.getUserId(), null, null);
				SecurityContextHolder.getContext().setAuthentication(auth);
			});
		}

		SecurityContextHolder.clearContext();
		chain.doFilter(request, response);
	}

	private boolean checkToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}

}
