package com.mobilpack.manager.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobilpack.manager.Exception.NoinfoException;
import com.mobilpack.manager.Model.AdminModel;
import com.mobilpack.manager.Service.AdminLoginService;
import com.mobilpack.manager.Service.JwtService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/su/my")
public class AdminLoginController {
	@Autowired
	private AdminLoginService loginservice;
	
	@Autowired
	private JwtService jwtservice;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> checkLogin(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		//매개변수 불러오기
		String id = param.get("id").toString();
		String pw = param.get("pw").toString();
		//JSON 결과 변수
		Map<String, Object> resultMap = new HashMap<>();
		//response status 변수
		HttpStatus status = null;
		//login 정보 객체
		AdminModel loginadmin = null;
		try {// 르그인 정보 확인 및 토큰 생성 시도
			loginadmin = loginservice.Login(id, pw);
			String token = jwtservice.createJWT(loginadmin);
			resultMap.put("status", true);
			resultMap.put("token", token);
			resultMap.put("name", loginadmin.getName());
			status = HttpStatus.ACCEPTED;
		} catch (NoinfoException e) {
			resultMap.put("status", false);
			//Debug
			//resultMap.put("reason", e.getMessage());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/pwupdate")
	public ResponseEntity<Map<String, Object>> myPwUpdate(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/infoupdate")
	public ResponseEntity<Map<String, Object>> myInfoUpdate(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		//안주면...?
		Map<String, Object> resultMap = new HashMap<>();
		String id = param.get("id").toString();
		String name = param.get("name").toString();
		String phone = param.get("phone").toString();
		String email = param.get("email").toString();
		HttpStatus status = null;
		try {
			loginservice.editInfo(id, name, phone, email);
			status = HttpStatus.OK;
			resultMap.put("status", true);
		} catch (NoinfoException e) {
			resultMap.put("status", false);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
