package com.mobilpack.manager.Dao;

import java.util.List;

import com.mobilpack.manager.Model.AdminModel;

public interface Dao {
	public AdminModel LoginQuery(String id, String pw); 
	
	//(페이징) 현재 페이지에 대한 관리자 항목 정보,정해진 컬럼수 가져오기 
    public List<AdminModel> getadminlist(int Currentpage,int Number);
    
    //(페이징) 전체 관리자 항목수 가져오기 
    public int getadminlistcount();
    
    //(관리자 검색) 관리자를 검색한 결과 리스트 가져오기 (페이징 포함)
    public List<AdminModel> searchadminlist( int Currentpage,int Number,String id,String name, String createat ,String updateat);

    //(관리자 id 증복 검사)
    public String idcheck(String id);
    
    //(관리자 등록)
    public String joinadmin(String id,String name,String phone,String email);
    
    //(관리자 상세 정보)
    public List<AdminModel> admininformation(String id);
	public AdminModel LoginQuery(String id, String pw);
	public void editInfo(String id, String name, String phone, String email);
}
	