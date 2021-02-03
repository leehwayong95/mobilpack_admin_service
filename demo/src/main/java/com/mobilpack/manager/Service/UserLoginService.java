package com.mobilpack.manager.Service;

import java.security.NoSuchAlgorithmException;

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
	public UserModel login(String id, String pw) throws NoinfoException{
		String salt = dao.getUserSalt(id);
		String encryptPW = null;
		try { // 비밀번호 암호화
			encryptPW = InfoEncryptService.getEncrypt(pw, salt);
			System.out.println("Login > Salt : " + salt);
			System.out.println("Login > PW : " +encryptPW);
		} catch (NullPointerException e) {
			throw new NoinfoException("Wrong PW or ID");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		UserModel targetUser = dao.getUserLogin(id, encryptPW);
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
		String originPw = user.getPassword();
		String Salt = InfoEncryptService.getSalt();
		String encryptPw = "";
		try {
			encryptPw = InfoEncryptService.getEncrypt(originPw, Salt);
			
			user.setPassword(encryptPw);
			user.setSalt(Salt);
			
			System.out.println("Signin > Salt : " + Salt);
			System.out.println("Sigin > PW : " +encryptPw);
			
			dao.signinUser(user);
			return true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
