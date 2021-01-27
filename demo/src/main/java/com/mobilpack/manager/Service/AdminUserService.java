package com.mobilpack.manager.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Exception.NoinfoException;
import com.mobilpack.manager.Model.UserModel;

@Service
public class AdminUserService {
	@Autowired
	Dao dao;
	
	private String search_where = "";
	//유저 검색 리스트 반환 메서드
	//파라메터 맵과 전달된 키만 확인
	public List<UserModel> getUserList (Map<String,Object>param, List<String> keyList){
		// limit 절
		String limit = "LIMIT ";
		int page = 1;
		int count = Integer.parseInt(param.get("count").toString());
		try {
			page = Integer.parseInt(param.get("page").toString());
		} catch (Exception e){
			page = 1;
		}
		limit += (page-1)*count + ", " +count;
		//조건절
		String where = "";
		for(String key : keyList) {
			if(!where.equals(""))
				where += " AND ";
			switch(key) {
			case("userid"):
				where += "user_id LIKE \'%" + param.get(key).toString() + "%\'";
				break;
			case("username"):
				where += "name LIKE \'%" + param.get(key).toString() +"%\'";
				break;
			case("min"):
				where += "DATE_FORMAT(createat, '%Y%m%d') >= STR_TO_DATE(\'" + param.get(key).toString() + "\','%Y%m%d')";
				break;
			case("max"):
				where += "DATE_FORMAT(createat, '%Y%m%d') <= STR_TO_DATE(\'" + param.get(key).toString() + "\','%Y%m%d')";
				break;
			case("region"):
				where += "country = \'" + param.get(key).toString() + "\'";
			}
		}
		if (!where.equals(""))
			where = "WHERE " + where;
		this.search_where = where;
		//결과물 인출 및 반환
		List<UserModel> resultList = dao.getUserList(where,limit);
		return resultList;
	}
	
	//최근 검색 결과 최종 개수 반환 메서드
	public int getUserListCount () {
		int count = dao.getUserListCount(search_where);
		this.search_where = ""; //개수를 반환하면 최근 검색 where절 초기화
		return count;
	}
	
	//유저 정보 반환 서비스
	public UserModel getUserInfo (String id) throws NoinfoException {
		UserModel target = dao.getUserInfo(id);
		if(target == null)
			throw new NoinfoException("Can't find user");
		else
			return target;
	}
	
	//유저 pw reset 서비스
	public void setUserPwReset (String id) throws NoinfoException{
		this.getUserInfo(id);
		dao.setUserPwReset(id);
	}
}
