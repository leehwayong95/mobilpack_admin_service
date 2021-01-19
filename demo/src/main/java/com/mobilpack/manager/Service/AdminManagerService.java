package com.mobilpack.manager.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Model.AdminModel;

@Service
public class AdminManagerService {
	@Autowired
	private Dao dao;
	
	//(처음 페이지 관리자 리스트 페이징) 현재 페이지에 대한 관리자 항목 정보,정해진 컬럼수 가져오기 
    public List<AdminModel> getadminlist(int Currentpage, int Number)
    {
        return dao.getadminlist(Currentpage, Number);
    }
    
    //(처음 페이지 관리자 리스트 페이징) 전체 관리자 항목수 가져오기 
    public int getadminlistcount(){
        return dao.getadminlistcount();
    }
    
    //(관리자 검색) 관리자를 검색한 결과 리스트 가져오기 (페이징 포함)
    public List<AdminModel> searchadminlist(int Currentpage,int Number,String id,String name, String createat ,String updateat)
    {
        return dao.searchadminlist(Currentpage,Number,id,name,createat,updateat);
    }
    
    //(관리자 id 중복 검사)
    public String idcheck(String id) {
    	return dao.idcheck(id);
    }
    
    //(관리자 등록)
    public String joinadmin(String id,String name,String phone,String email) {
    	return dao.joinadmin(id,name,phone,email);
    }
    
    public List<AdminModel> admininformation(String id){
    	 return dao.admininformation(id);
    }
}
