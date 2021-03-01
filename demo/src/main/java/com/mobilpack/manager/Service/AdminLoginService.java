package com.mobilpack.manager.Service;

import java.security.NoSuchAlgorithmException;

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
		String salt = dao.getAdminSalt(id);
		String encryptPw = null;
		try {
			encryptPw = InfoEncryptService.getEncrypt(pw, salt);
		} catch (NullPointerException e) {
			if (dao.getUserSalt(id) == null) {
				throw new NoinfoException("Wrong ID");
			} else { 
				throw new NoinfoException("UserID");
			}
		} catch (NoSuchAlgorithmException e) {
			throw new NoinfoException("WTF");
		}
		AdminModel loginmodel = dao.LoginQuery(id, encryptPw);
		if (loginmodel != null)
			return loginmodel;
		else {
			throw new NoinfoException("Wrong PW");
		}
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
		String salt = null;
		String EncryptPw = null;
		if (targetModel == null) {
			throw new NoinfoException("Wrong ID or PW");
		}
		try {
			salt = dao.getAdminSalt(id);
			EncryptPw = InfoEncryptService.getEncrypt(editpw, salt);
		} catch (NoSuchAlgorithmException e) {
			throw new NoinfoException("WTF");
		}
		dao.editPw(targetModel.getAdmin_id(), EncryptPw);
		return true;
	}
}
