package io.agileintelligence.ppmtool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.agileintelligence.ppmtool.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

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
	
	
	public boolean validateToken(String token){
		try{
			Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
			return true;			
		}catch (SignatureException ex) {
			System.out.println("Invalid JWT signature");
		}catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT token");
		}catch (ExpiredJwtException ex) {
			System.out.println("Jwt token expired");
		}catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT token");
		}catch (IllegalArgumentException ex) {
			System.out.println("Jwt claims string is empty");
		}			
		return false;
	}
	
	
	  public Long getUserIdFromJWT(String token){
	        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
	        String id = (String)claims.get("id");

	        return Long.parseLong(id);
	    }
}
