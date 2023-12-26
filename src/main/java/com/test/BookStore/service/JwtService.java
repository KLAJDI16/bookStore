package com.test.BookStore.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.test.BookStore.entities.Student;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

@Data
@Service
public class JwtService {

	@Value("${application.security.jwt.secret-key}")
	private String tokenSecret;
	
	@Value("${application.security.jwt.expiration}")
	private long expirationTime;

	@Value("${application.security.jwt.refresh-token.expiration}")
	private long expirationRefreshTime;
	
	public String generateToken(Map<String,Object> extraClaims ,Student student) {
		
		return Jwts.builder()
				.setClaims(extraClaims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.setSubject(student.getEmail())
				.signWith( getSignInKey(),SignatureAlgorithm.ES256)
				.compact();
				
	}
public String generateToken(Student student) {
		return Jwts.builder()
				.setClaims(new HashMap<>())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.setSubject(student.getEmail())
				.signWith( getSignInKey(),SignatureAlgorithm.ES256)
				.compact();
	}
public String generateRefreshToken(Student student) {
	return Jwts.builder()
			.setClaims(new HashMap<>())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expirationRefreshTime ))
			.setSubject(student.getEmail())
			.signWith( getSignInKey(),SignatureAlgorithm.ES256)
			.compact();
}
public Claims extractAllClaims(String token) {
	return	Jwts.parserBuilder()
	.setSigningKey(getSignInKey())
	.build()
	.parseClaimsJwt(token)
	.getBody();
}

public String extractUsername(String token) {
	return extractAllClaims(token).getSubject();
}
public Date extractExpiration(String token) {
	return extractAllClaims(token).getExpiration();
}
public boolean tokenExpired(String token) {
	return extractExpiration(token).before(new Date());
}
public boolean isTokenValid(String token,UserDetails userDetails ) {
	String username=extractUsername(token);
	return (userDetails.getUsername().equals(username) && !tokenExpired(token));
}
private Key getSignInKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(tokenSecret);
	    return Keys.hmacShaKeyFor(keyBytes);
	  }

}
