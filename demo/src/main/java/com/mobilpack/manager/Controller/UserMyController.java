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

import com.mobilpack.manager.Model.UserModel;
import com.mobilpack.manager.Service.JwtService;
import com.mobilpack.manager.Service.UserMyService;

@CrossOrigin
@RestController
@RequestMapping("/api/my")
public class UserMyController {
	
	@Autowired
	UserMyService myservice;
	@Autowired
	JwtService jwtservice;
	
	@PostMapping("/update")
	public ResponseEntity<Map<String, Object>> userInfo(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/info")
	public ResponseEntity<Map<String,Object>> getInfo(
			HttpServletRequest req) {
		Map<String,Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		UserModel targetModel = null;
		try {
			targetModel = myservice.getUserInfo(jwtservice.getInfo(req.getHeader("authorization")).get("user_id").toString());
			resultMap.put("status", true);
			resultMap.put("UserModel", targetModel);
			status = HttpStatus.OK;
		} catch (Exception e) { 
			e.printStackTrace();
			resultMap.put("status", false);
			resultMap.put("reason", e.getMessage());
			status=HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
}
