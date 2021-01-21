package com.mobilpack.manager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Model.UserModel;

@Service
public class UserMyService {
	@Autowired
	Dao dao;
	public UserModel getUserInfo(String id) {
		return dao.getUserInfo(id);
	}
}
