package com.tainweb.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtMain {

	public static void main(String[] args) {
		JwtMain jwtMain = new JwtMain();
		
		String token = jwtMain.createToken();
		System.out.println("---> jwt(" + token.length() + "): " + token);
		
		Map<String, Object> claimMap = jwtMain.verifyToken(token);
		System.out.println("---> claimMap: " + claimMap);
		
		System.out.println("---> jti: " + claimMap.get("jti"));
		
		Date exp = new Date(1000L * (Integer) claimMap.get("exp"));
		System.out.println("---> exp: " + exp.toString());
	}
	
	/////////////////////////////////////////////////
	
	private static final String key = "Bamdule";

	public String createToken() {
		// header setting
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS512");
		
		// payload setting
		Map<String, Object> payloads = new HashMap<>();
		payloads.put("data", "My First JWT!!");
		
		Long expiredTime = 1000 * 60L * 60L * 2L;  // 2hours
		
		Date ext = new Date();
		ext.setTime(ext.getTime() + expiredTime);
		System.out.println("---> ext: " + ext.getTime());
		
		// token builder
		String jwt = Jwts.builder()
				.setHeader(headers)
				.setClaims(payloads)
				.setSubject("user")
				.setExpiration(ext)
				.setId(UUID.randomUUID().toString())
				.signWith(SignatureAlgorithm.HS256, key.getBytes())
				//.signWith(SignatureAlgorithm.HS384, key.getBytes())
				//.signWith(SignatureAlgorithm.HS512, key.getBytes())
				//.signWith(SignatureAlgorithm.RS512, key.getBytes())
				.compact();
		
		return jwt;
	}
	
	public Map<String, Object> verifyToken(String token) {
		Map<String, Object> claimMap = null;
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(key.getBytes("utf-8"))
					.parseClaimsJws(token)
					.getBody();
			claimMap = claims;
			
			/*
			Date expiration = claims.get("exp", Date.class);
			String data = claims.get("data", String.class);
			String sub = claims.get("sub", String.class);
			System.out.println("---> expiration: " + expiration.toString());
			System.out.println("---> data: " + data);
			System.out.println("---> sub: " + sub);
			
			System.out.println("--> " + claimMap.get("exp"));
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return claimMap;
	}
}
