package com.mobilpack.manager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Exception.NoinfoException;
import com.mobilpack.manager.Model.AdminModel;

@Service
public class AdminLoginService {
	@Autowired
	private Dao dao;
	
	public AdminModel Login(String id, String pw) throws NoinfoException
	{
		AdminModel loginmodel = dao.LoginQuery(id, pw);
		if (loginmodel != null)
			return loginmodel;
		else
			throw new NoinfoException("Wrong PW or ID");
	}
	
	public boolean editInfo(String id, String name, String phone, String email) throws NoinfoException
	{
		try {
			dao.editInfo(id, name, phone, email);
			return true;
		} catch (Exception e)
		{
			throw new NoinfoException("Can't find ID");
		}
	}
}
