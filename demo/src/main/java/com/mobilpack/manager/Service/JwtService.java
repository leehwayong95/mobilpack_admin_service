package com.mobilpack.manager.Service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mobilpack.manager.Model.AdminModel;
import com.mobilpack.manager.Model.UserModel;

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
	//expireTime value
	
	//관리자 jwt토큰 발급 method
	public String createJWT(final AdminModel admin)
	{
		final JwtBuilder builder = Jwts.builder();
		builder.setHeaderParam("typ","JWT");
		
		builder.setSubject("logintoken")
				.setExpiration(new Date(System.currentTimeMillis()*1000+60 * expireMin))
				.claim("admin_id", admin.getAdmin_id())
				.claim("name", admin.getName());
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		
		final String jwt = builder.compact();
		return jwt;
	}
	//사용자용 토큰발급 Overload//
	public String createJWT (final UserModel user) {
		final JwtBuilder builder = Jwts.builder();
		builder.setHeaderParam("typ","JWT");
		builder.setSubject("logintoken")
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
				.claim("user_id",user.getUser_id())
				.claim("name", user.getName());
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		
		final String jwt = builder.compact();
		return jwt;
	}
	//jwt Decryption method
	public Map<String, Object> getInfo(final String jwt) throws Exception
	{
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		} catch (Exception e) { 
			throw new Exception(e.getMessage());
		}
		return claims.getBody();
	}
	
}
