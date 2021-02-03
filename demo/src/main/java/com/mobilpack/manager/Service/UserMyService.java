package com.mobilpack.manager.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Exception.NoinfoException;
import com.mobilpack.manager.Model.UserModel;

@Service
public class UserMyService {
	@Autowired
	Dao dao;
	
	public UserModel getUserInfo(String id) {
		return dao.getUserInfo(id);
	}
	
	/**
	 * 회원 정보 수정 메서드
	 * dao에 전달되는 HashMap은 파라메터 타입이 여러개여서 HashMap으로 뭉침
	 * @param target 수정 대상 id
	 * @param editModel 수정 목표 정보
	 */
	public void updateUserInfo(String target, UserModel editModel) {
		Map<String, Object> editinfo = new HashMap<>();
		editinfo.put("editModel", editModel);
		editinfo.put("target", target);
		dao.updateInfo(editinfo);
	}
	
	//회원 pw 변경 수정 메서드
	public void updatepw(String id, String currentpw, String editpw) throws NoinfoException {
		String salt = dao.getUserSalt(id);
		String encryptPw = "";
		try {
			encryptPw = InfoEncryptService.getEncrypt(currentpw, salt);
		} catch (Exception e) {
			throw new NoinfoException("worng ID");
		}
		UserModel target = dao.getUserLogin(id, encryptPw);
		if(target != null) {
			String EncryptEditPw;
			try {
				EncryptEditPw = InfoEncryptService.getEncrypt(editpw, salt);
				dao.updatepw(id, EncryptEditPw);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new NoinfoException("Error on getEncrypt");
			}
		}
		else
			throw new NoinfoException("worng currentpw");
	}
}
