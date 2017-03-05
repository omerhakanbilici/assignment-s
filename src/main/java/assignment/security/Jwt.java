package assignment.security;

import assignment.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class Jwt {

	private static final Logger logger = LoggerFactory.getLogger(Jwt.class);

	public static Authentication parseToken(String token) throws Exception {
		try {
			if(token == null) {
				throw new JwtException("Token is empty");
			}
			Claims body = getClaimsFromToken(token);
			User user = userFromClaims(body);
			return new UsernamePasswordAuthenticationToken(user, null, null);

		} catch (Exception e) {
			throw e;
		}

	}

	private static User userFromClaims(Claims body) {
		User user = new User();
		user.setUsername((String) body.get("username"));
		return user;
	}

	public static String usernameFromToken(String token) {
		Claims body = getClaimsFromToken(token);
		return (String) body.get("username");
	}

	public static String generateToken(User user) {

		Claims claims = Jwts.claims();
		claims.put("username", user.getUsername());

		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + 900 * 1000)) // 15 dakika
//                .setExpiration(new Date(System.currentTimeMillis() + 20 * 1000))
				.signWith(SignatureAlgorithm.HS256, "assignment-s")
				.compact();
	}

	private static Claims getClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey("assignment-s")
				.parseClaimsJws(token)
				.getBody();
	}

}