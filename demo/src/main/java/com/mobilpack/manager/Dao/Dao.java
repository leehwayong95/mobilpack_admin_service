package com.mobilpack.manager.Dao;

import java.util.List;
import java.util.Map;

import com.mobilpack.manager.Model.AdminModel;
import com.mobilpack.manager.Model.QnaModel;
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
    //(관리자 삭제)
    public void deleteadmin(String id);
    
    
    //(관리자 id 증복 검사)
    public String idcheck(String id);
    //(관리자 등록)
    public void joinadmin(String id,String password,String name,String phone,String email);
    //(관리자 수정)
    public void editadmin(String id,String name,String phone,String email);
    //(관리자 비밀번호 초기화)
    public void pwreset(String id);
    
    //관리자 로그인쿼리
	public AdminModel LoginQuery(String id, String pw);
	//관리자 정보 변경 : 자기자신
	public void editInfo(String id, String name, String phone, String email);
	//관리자 비밀번호 변경 : 자기자신
	public void editPw(String id, String editpw);
	
	//유저 리스트 조회
	public List<UserModel> getUserList (String where, String limit);
	//유저 리스트 조회 개수 반환
	public int getUserListCount (String where);
	//유저 정보 조회는 아래 유저 쿼리 재활용
	//유저 패스워드 초기화
	public void setUserPwReset (String id);
	//유저 삭제
	public void setUserDelete (String id);
	
	/**********************************************************/
	/****************아래는 사용자 쿼리관련 메서드 입니다.****************/
	/**********************************************************/
	
	//유저 로그인 
	public UserModel getUserLogin (String id, String pw);
	//유저 회원가입시 아이디 중복확인
	public UserModel getCheckingId (String id);
	//유저 회원가입
	public void signinUser (UserModel user);
	//유저 회원정보 확인
	public UserModel getUserInfo (String id);
	//유저 회원정보 수정
	public void updateInfo (Map<String, Object> editinfo);
	//유저 패스워드 수정
	public void updatepw (String id, String editpw);
	
	//유저 QnA 작성
	public void UserQnaWrite (QnaModel qna);
	//유저 QnA List 검색
	public List<QnaModel> getQnaList(String category, String title, String answer, String min, String max, String page, String count);
	//유저 QnA 자세히 보기
	public QnaModel getQnaPost(String index);
	//유저 QnA 삭제
	public void deleteQnaPost (String index, String id);
	//유저 QnA 수정
	public void updateQnaPost (QnaModel model);
}
	