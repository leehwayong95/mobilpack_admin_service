package com.mobilpack.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mobilpack.manager.Service.JwtService;

@Component
public class JWTInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String name = null;
		System.out.println("INTERCEPTOR >> " + request.getMethod() + ", " + request.getServletPath());
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		} else {
			String token = request.getHeader("authorization");
			if (token != null && token.length() > 0) {
				name = (String)jwtService.getInfo(token).get("name");
				System.out.println("요청자 : " + name + ", 토큰 해석 결과 : true");
				return true;
			} else {
				System.out.println("요청자 : " + name + ", 토큰 해석 결과 : false");
				throw new RuntimeException("NojwtTokenError");
			}
		}
	}
}
