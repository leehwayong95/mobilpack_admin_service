package com.mobilpack.manager.Service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mobilpack.manager.Model.AdminModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${jwt.expmin}")
	private Long expireMin;
	
	public String createJWT(final AdminModel admin)
	{
		final JwtBuilder builder = Jwts.builder();
		builder.setHeaderParam("typ","JWT");
		
		builder.setSubject("logintoken")
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
				.claim("admin_id", admin.getAdmin_id())
				.claim("superadmin", admin.getSuperadmin())
				.claim("name", admin.getName());
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		
		final String jwt = builder.compact();
		return jwt;
	}
	
	public void checkExpire(final String jwt)
	{
		Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
	}
	
	public Map<String, Object> getInfo(final String jwt) throws Exception
	{
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		} catch (Exception e) { 
			throw new Exception("KeyExpired");
		}
		return claims.getBody();
	}
}
