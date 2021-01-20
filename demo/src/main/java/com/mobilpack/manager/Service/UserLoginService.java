package com.mobilpack.manager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Exception.NoinfoException;
import com.mobilpack.manager.Model.UserModel;

@Service
public class UserLoginService {
	@Autowired
	Dao dao;
	
	public UserModel login(String id, String pw) throws NoinfoException {
		UserModel targetUser = dao.getUserLogin(id, pw);
		if (targetUser != null) {
			return targetUser;
		} else {
			throw new NoinfoException("Wrong PW or ID");
		}
	}
}
