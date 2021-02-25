package com.mobilpack.manager.Service;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Model.AdminModel;

@Service
public class AdminManagerService {
	@Autowired
	private Dao dao;
	

	// (관리자 검색) 관리자를 검색한 결과 리스트 가져오기 (페이징 포함)
	public List<AdminModel> searchadminlist(String Currentpage, String Number, String id, String name, String createat,
			String updateat) {
		System.out.println( "여기가 최종 날짜 입니다."+updateat);
		return dao.searchadminlist(Currentpage, Number, id, name, createat, updateat);
//		try {
//			System.out.println( createat);
//			System.out.println( updateat);
//			//SimpleDateFormat origin = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			//SimpleDateFormat newdate = new SimpleDateFormat("yyyy-MM-dd");
//			//Date Createat = origin.parse(createat);
//			//Date Updateat = origin.parse(updateat);
//
//			//String ChangeCreateat = newdate.format(Createat);
//			//String ChangeUpdateat = newdate.format(Updateat);
//
//			return dao.searchadminlist(Currentpage, Number, id, name, createat, updateat);
//			//return dao.searchadminlist(Currentpage, Number, id, name, ChangeCreateat, ChangeUpdateat);
//		//} catch (ParseException e) {
//		  } catch (exception e) {
//			System.out.println("포맷 오류");
//			String ChangeCreateat = null;
//			String ChangeUpdateat = null;
//			return dao.searchadminlist(Currentpage, Number, id, name, createat, updateat);
//			//return dao.searchadminlist(Currentpage, Number, id, name, ChangeCreateat, ChangeUpdateat);
//		}
	}
	
    //(관리자 id 중복 검사)
    public String idcheck(String id) {
    	return dao.idcheck(id);
    }
    
    //(관리자 등록)
    public void joinadmin(String id,String name,String phone,String email) throws NoSuchAlgorithmException {
    	String password = "admin1234!!";
    	String salt = InfoEncryptService.getSalt();
    	password = InfoEncryptService.getEncrypt(password, salt);
    	dao.joinadmin(id,password,name,phone,email,salt);
    }
    
    //(관리자 수정)
    public void editadmin(String id,String name,String phone,String email) {
    	dao.editadmin(id,name,phone,email);
    }
    
  //(관리자 비밀번호 초기화)
    public void pwreset(String id) throws NoSuchAlgorithmException {
    	String salt = InfoEncryptService.getSalt();
    	String EncryptPw = InfoEncryptService.getEncrypt("admin1234!!", salt);
    	dao.pwreset(id, EncryptPw, salt);
    }
    
    //(관리자 상세정보)
    public AdminModel admininformation(String id){
    	 return dao.admininformation(id);
    }
    
    //(관리자 삭제)
    public void deleteadmin(String id){
    	 dao.deleteadmin(id);
    }
}
