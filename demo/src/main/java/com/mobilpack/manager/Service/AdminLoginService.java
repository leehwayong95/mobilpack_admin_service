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
	//로그인 쿼리 결과 전달 메서드
	public AdminModel Login(String id, String pw) throws NoinfoException
	{
		AdminModel loginmodel = dao.LoginQuery(id, pw);
		if (loginmodel != null)
			return loginmodel;
		else
			throw new NoinfoException("Wrong PW or ID");
	}
	//정보 수정 메서드 (비밀번호제외)
	public boolean editInfo(String id, String name, String phone, String email) throws NoinfoException
	{
		try {
			dao.editInfo(id, name, phone, email);
			return true;
		} catch (Exception e) {
			throw new NoinfoException("Can't find ID");
		}
	}
	
	public boolean editPw(String id, String currentpw, String editpw) throws NoinfoException
	{
		AdminModel targetModel = this.Login(id, currentpw);
		dao.editPw(targetModel.getAdmin_id(), editpw);
		return true;
	}
}
