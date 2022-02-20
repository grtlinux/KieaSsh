package com.tainweb.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtMain {

	public static void main(String[] args) {
		JwtMain jwtMain = new JwtMain();
		
		String jwt = jwtMain.createToken();
		System.out.println("---> jwt(" + jwt.length() + "): " + jwt);
		
		Map<String, Object> claimMap = jwtMain.verifyToken(jwt);
		System.out.println("---> claimMap: " + claimMap);
		
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
				.signWith(SignatureAlgorithm.HS512, key.getBytes())
				.compact();
		
		return jwt;
	}
	
	public Map<String, Object> verifyToken(String jwt) {
		Map<String, Object> claimMap = null;
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(key.getBytes("utf-8"))
					.parseClaimsJws(jwt)
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
