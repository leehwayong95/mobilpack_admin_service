package com.mobilpack.manager.Controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my")
public class UserMyController {
	
	@PostMapping("/update")
	public ResponseEntity<Map<String, Object>> userInfo(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
