package com.mobilpack.manager.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobilpack.manager.Model.UserModel;
import com.mobilpack.manager.Service.AdminUserService;

@CrossOrigin(origins = "http://localhost/")
@RestController
@RequestMapping("/api/su/user")
public class AdminUserController {
	@Autowired
	AdminUserService userService;
	
	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> userSearch(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			//검색결과 조회
			String[] keyList = {"userid","username","min","max","region"};
			List<String> usedKey=new ArrayList<>();
			//cookie 형식의 key=value;형식으로 String 만듦.
			for(int i = 0 ; i < 5 ; i++) {
				try {
					param.get(keyList[i]).toString();
					usedKey.add(keyList[i]);
				} catch (Exception e) {
					if (keyList[i].equals("page")) {
						param.put("page", 1);
						usedKey.add(keyList[i]);
					}
					continue;
				}
			}//검색조건 추출 및 필터
			List<UserModel> userlist = userService.getUserList(param, usedKey);
			resultMap.put("userdata", userlist);
			//리스트 개수 반환
			resultMap.put("total", userlist.size());
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("userdata",null);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/info")
	public ResponseEntity<Map<String, Object>> userInfo(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/pwreset")
	public ResponseEntity<Map<String, Object>> userPwreset(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, Object>> userDELETE(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
