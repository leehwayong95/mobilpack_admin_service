package com.mobilpack.manager.Dao;

import java.util.List;
import java.util.Map;

import com.mobilpack.manager.Model.AdminModel;
import com.mobilpack.manager.Model.UserModel;

public interface Dao {
	//(페이징) 현재 페이지에 대한 관리자 항목 정보,정해진 컬럼수 가져오기 
    public List<AdminModel> getadminlist(int Currentpage,int Number);
    //(페이징) 전체 관리자 항목수 가져오기 
    public int getadminlistcount();
    //(관리자 검색) 관리자를 검색한 결과 리스트 가져오기 (페이징 포함)
    public List<AdminModel> searchadminlist( int Currentpage,int Number,String id,String name, String createat ,String updateat);
    //(관리자 상세 정보)
    public AdminModel admininformation(String id);
    
    //(관리자 id 증복 검사)
    public String idcheck(String id);
    //(관리자 등록)
    public String joinadmin(String id,String name,String phone,String email);
    
    //관리자 로그인쿼리
	public AdminModel LoginQuery(String id, String pw);
	//사용자 정보 변경
	public void editInfo(String id, String name, String phone, String email);
	//사용자 비밀번호 변경
	public void editPw(String id, String editpw);
	
	/**********************************************************/
	/****************아래는 사용자 쿼리관련 메서드 입니다.****************/
	/**********************************************************/
	
	//유저 로그인 
	public UserModel getUserLogin(String id, String pw);
	//유저 회원가입시 아이디 중복확인
	public UserModel getCheckingId(String id);
	//유저 회원가입
	public void signinUser (UserModel user);
	//유저 회원정보 확인
	public UserModel getUserInfo(String id);
	//유저 회원정보 수정
	public void updateInfo(Map<String, Object> editinfo);
}
	