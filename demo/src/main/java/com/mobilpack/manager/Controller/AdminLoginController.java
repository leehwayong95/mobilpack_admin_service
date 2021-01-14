package com.mobilpack.manager.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobilpack.manager.Service.AdminLoginService;

@CrossOrigin(origins = "http://localhost/")
@RestController
@RequestMapping("/api/my")
public class AdminLoginController {
	@Autowired
	private AdminLoginService loginservice;
	
	@PostMapping("/login")
	public String checkLogin(
			@RequestParam(value="id",required=false) String id,
			HttpServletRequest req) {
//		AdminModel queryadmin = loginservice.Login(id, pw);
		
		return "Hello";
	}
}
