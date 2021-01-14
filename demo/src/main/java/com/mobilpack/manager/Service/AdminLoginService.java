package com.mobilpack.manager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Model.AdminModel;

@Service
public class AdminLoginService {
	@Autowired
	private Dao dao;
	
	public AdminModel Login(String id, String pw)
	{
		return dao.LoginQuery(id, pw);
	}
}
