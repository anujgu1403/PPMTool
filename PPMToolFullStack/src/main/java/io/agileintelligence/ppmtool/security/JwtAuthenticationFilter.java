package io.agileintelligence.ppmtool.security;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.services.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getJwtTokenFromRequest(request);
			if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
				Long userId = tokenProvider.getUserIdFromJWT(token);
				User userDetails = customUserDetailsService.findUserById(userId);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, Collections.emptyList());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			logger.error("Could not set authentication", ex);
		}
		filterChain.doFilter(request, response);
	}

	public String getJwtTokenFromRequest(HttpServletRequest request) {
		String jwtToekn = request.getHeader(SecurityConstants.HEADER_STRING);
		if (null!= jwtToekn && StringUtils.hasText(SecurityConstants.TOKEN_PREFIX))
			return jwtToekn.substring(7, jwtToekn.length());

		return null;
	}
}
