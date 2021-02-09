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
/**
 * 토큰 발급 및 해석 서비스
 * @author Leehwayong
 *
 */
public class JwtService {
	@Value("${jwt.salt}")
	private String salt;

	@Value("${jwt.expmin}")
	/*
	 * 만료시간 값을 Application properties에서 불러옵니다.
	 */
	private Long expireMin;
	
	
	/**
	 * 관리자 JWT토큰 발급 method
	 * OverLoad된 메서드입니다.
	 * @param AdminModel을 사용합니다.
	 * @return admin_id와 name을 담은 JWT String 반환
	 */
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
	/**
	 * 유저 JWT토큰 발급 method
	 * OverLoad된 메서드입니다.
	 * @param UserModel을 사용합니다.
	 * @return admin_id와 name을 담은 JWT String 반환
	 */
	public String createJWT (final UserModel user) {
		final JwtBuilder builder = Jwts.builder();
		builder.setHeaderParam("typ","JWT");
		builder.setSubject("logintoken")
				.setExpiration(new Date(System.currentTimeMillis() * 1000 + 60 * expireMin))
				.claim("user_id",user.getUser_id())
				.claim("name", user.getName());
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		
		final String jwt = builder.compact();
		return jwt;
	}
	/**
	 * JWT Decryption Method
	 * 토큰을 해석하여 Body를 반환합니다.
	 * @param jwt String
	 * @return claims의 body를 반환합니다.
	 * @throws Exception JWT Key가 만료되었을때 오류가 납니다. 
	 */
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
	/**
	 * 
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public String getAdminID(final String jwt) throws Exception {
		String id = (String)this.getInfo(jwt).get("admin_id");
		return id;
	}
}
