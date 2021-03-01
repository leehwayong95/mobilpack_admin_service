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
import com.mobilpack.manager.Service.AdminManagerService;
import com.mobilpack.manager.Service.JwtService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/su/my")
public class AdminLoginController {
	//어드민 정보 반환을 위한 서비스 객체 생성
	@Autowired
	private AdminManagerService managerService;
	//loginService 객체 생성
	@Autowired
	private AdminLoginService loginservice;
	//jwtService 생성
	@Autowired
	private JwtService jwtservice;
	
	@PostMapping("/login")//로그인 RestAPI. Response : 토큰 발행
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
			resultMap.put("jwt-token", token);
			resultMap.put("name", loginadmin.getName());
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			//e.printStackTrace();
			resultMap.put("status", false);
			//Debug
			resultMap.put("reason", e.getMessage());
			if(e.getMessage().equals("UserID"))
				status = HttpStatus.GONE;
			else
				status = HttpStatus.OK;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/pwupdate")//비밀번호 초기화
	public ResponseEntity<Map<String, Object>> myPwUpdate(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String currentpw = param.get("currentpw").toString();
		String editpw = param.get("editpw").toString();
		try {
			String id = jwtservice.getAdminID(req.getHeader("authorization"));
			loginservice.editPw(id, currentpw, editpw);
			resultMap.put("status", true);
			status = HttpStatus.OK;
		} catch (Exception e){
			e.printStackTrace();
			resultMap.put("status", false);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/info")//로그인 정보 조회
	public ResponseEntity<Map<String, Object>> getMyInfo(
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String token = req.getHeader("authorization");
			Map<String, Object> info = jwtservice.getInfo(token);
			String id = (String)info.get("admin_id");
			resultMap.put("admininfo", managerService.admininformation(id));
			status = HttpStatus.OK;
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/infoupdate")
	public ResponseEntity<Map<String, Object>> myInfoUpdate(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		//Request Parameter
		String name = param.get("name").toString();
		String phone = param.get("phone").toString();
		String email = param.get("email").toString();
		HttpStatus status = null;
		try {
			String id = (String)jwtservice.getInfo(req.getHeader("authorization")).get("admin_id");
			loginservice.editInfo(id, name, phone, email);
			status = HttpStatus.OK;
			resultMap.put("status", true);
		} catch (Exception e) {
			resultMap.put("status", false);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
