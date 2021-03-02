package com.mobilpack.manager.Service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mobilpack.manager.Exception.JwtExpiredException;
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
 * 
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
	 * 관리자 JWT토큰 발급 method OverLoad된 메서드입니다.
	 * 
	 * @param AdminModel을 사용합니다.
	 * @return admin_id와 name을 담은 JWT String 반환
	 */
	public String createJWT(final AdminModel admin) {
		final JwtBuilder builder = Jwts.builder();
		builder.setHeaderParam("typ", "JWT");

		builder.setSubject("logintoken").setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
				.claim("admin_id", admin.getAdmin_id()).claim("name", admin.getName());
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

		final String jwt = builder.compact();
		return jwt;
	}

	/**
	 * 유저 JWT토큰 발급 method OverLoad된 메서드입니다.
	 * 
	 * @param UserModel을 사용합니다.
	 * @return user_id와 name을 담은 JWT String 반환
	 */
	public String createJWT(final UserModel user) {
		final JwtBuilder builder = Jwts.builder();
		builder.setHeaderParam("typ", "JWT");
		builder.setSubject("logintoken").setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
				.claim("user_id", user.getUser_id()).claim("name", user.getName());
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

		final String jwt = builder.compact();
		return jwt;
	}

	/**
	 * JWT Decryption Method 토큰을 해석하여 Body를 반환합니다.
	 * 
	 * @param jwt String
	 * @return claims의 body를 반환합니다.
	 * @throws Exception JWT Key가 만료되었을때 오류가 납니다.
	 */
	public Map<String, Object> getInfo(final String jwt) throws JwtExpiredException {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new JwtExpiredException(e.getMessage());
		}
		return claims.getBody();
	}

	/**
	 * Admin JWT Token을 해석하여 Admin_id를 반환합니다.
	 * 
	 * @param req에 담긴 JWT를 매개변수로 가집니다.
	 * @return Admin의 id를 반환합니다.
	 * @throws Exception
	 */
	public String getAdminID(final String jwt) throws Exception {
		String id = (String) this.getInfo(jwt).get("admin_id");
		return id;
	}

	/**
	 * User JWT Token을 해석하여 User_id를 반환합니다.
	 * 
	 * @param req에 담긴 JWT를 매개변수로 가집니다.
	 * @return User의 id를 반환합니다.
	 * @throws Exception JWT가 만료되었을때 오류가 납니다.
	 */
	public String getUserID(final String jwt) throws Exception {
		String id = (String) this.getInfo(jwt).get("user_id");
		return id;
	}

	/**
	 * User, Admin JWT Token을 해석하여 이름을 반환합니다.
	 * 
	 * @param req에 담긴 JWT를 매개변수로 가집니다.
	 * @return User, Admin의 이름을 반환합니다.
	 * @throws Exception JWT가 만료 되었을 때 오류를 표출합니다.
	 */
	public String getName(final String jwt) throws Exception {
		String name = (String) this.getInfo(jwt).get("name");
		return name;
	}
}
