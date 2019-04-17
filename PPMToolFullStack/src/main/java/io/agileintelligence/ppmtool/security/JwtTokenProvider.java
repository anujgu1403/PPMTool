package io.agileintelligence.ppmtool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.agileintelligence.ppmtool.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		
		Date expiryDate = new Date(now.getTime()+SecurityConstants.TOKEN_EXPIRATION_TIME);
		String userId = Long.toString(user.getId());
		Map<String, Object> claims= new HashMap<String, Object>();
		claims.put("id", Long.toString(user.getId()));
		claims.put("fullName", user.getFullName());
		claims.put("username", user.getUsername());
		
		return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).compact();		
	}
}
