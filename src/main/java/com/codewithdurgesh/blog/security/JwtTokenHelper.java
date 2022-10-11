package com.codewithdurgesh.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private String secret = "jwtTokenKey";

	// retrieve username from jwt token
	public String getUserNamefromToken(String token) {
		System.out.println(" getUserNamefromToken() ");
		return getClaimsfromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		System.out.println(" getExpirationDateFromToken() ");
		return getClaimsfromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimsfromToken(String token, Function<Claims, T> claimsResolver) {
		System.out.println(" getClaimsfromToken() ");
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		System.out.println(" getAllClaimsFromToken() ");
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private boolean isTokenExpired(String token) {
		System.out.println(" isTokenExpired() ");
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(UserDetails userDetails) {
		System.out.println(" generateToken() ");
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	// while generating the token
	// 1. Define claims of the token , like issuer, expiration, subject and the ID
	// 2. Siglgoritn the JWT using HS512 hmac and secret key
	// 3. According to JWS Compact Serialization (
	// compaction of the jwt to a URL-safe String

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		System.out.println(" doGenerateToken() ");
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		System.out.println(" validateToken() ");
		final String userName = getUserNamefromToken(token);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
}
