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
	//로그인서비스
	public UserModel login(String id, String pw) throws NoinfoException {
		UserModel targetUser = dao.getUserLogin(id, pw);
		if (targetUser != null) {
			return targetUser;
		} else {
			throw new NoinfoException("Wrong PW or ID");
		}
	}
	//회원가입의 아이디 중복확인 서비스
	public boolean checkID(String id) {
		UserModel user = dao.getCheckingId(id);	
		if (user != null) {//검색된 사용자 객체가 있으면
			return false;// 불가능
		} else {
			return true;// null일땐 가능
		}
	}
	//회원가입 서비스
	public boolean signin(UserModel user) {
		dao.signinUser(user);
		return true;
	}
}
