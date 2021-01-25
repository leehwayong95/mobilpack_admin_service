package com.mobilpack.manager.Service;

import java.util.HashMap;
import java.util.Map;

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
	
	//회원 정보 수정 메서드. dao에 전달되는 HashMap은 파라메터 타입이 여러개여서 HashMap으로 뭉침
	public void updateUserInfo(String target, UserModel editModel) {
		Map<String, Object> editinfo = new HashMap<>();
		editinfo.put("editModel", editModel);
		editinfo.put("target", target);
		dao.updateInfo(editinfo);
	}
}
