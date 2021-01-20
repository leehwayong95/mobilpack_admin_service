package com.mobilpack.manager.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobilpack.manager.Exception.NoinfoException;
import com.mobilpack.manager.Model.UserModel;
import com.mobilpack.manager.Service.JwtService;
import com.mobilpack.manager.Service.UserLoginService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/user")
public class UserLoginController {
	@Autowired
	UserLoginService loginservice;
	
	@Autowired
	JwtService jwtservice;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> userSearch(
			@RequestBody Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String id = param.get("id").toString();
		String pw = param.get("pw").toString();
		try {
			UserModel targetUser = loginservice.login(id, pw);
			String token = jwtservice.createJWT(targetUser);
			resultMap.put("status", true);
			resultMap.put("name",targetUser.getName());
			resultMap.put("jwt-token", token);
			status = HttpStatus.OK;
		} catch (NoinfoException e) {
			resultMap.put("status", false);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/check")
	public ResponseEntity<Map<String, Object>> userInfo(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/singin")
	public ResponseEntity<Map<String, Object>> userPwreset(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
}
