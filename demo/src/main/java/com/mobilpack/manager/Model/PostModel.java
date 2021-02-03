package com.mobilpack.manager.Model;

import lombok.Data;

@Data
public class PostModel {
	private String postindex;
	private String default_lang;	//기본 언어
	private String category;		//카테고리
	private String title;			//추천장소명
	private String admin_id;		//관리자 아이디
	private String content;			//관광정보
	private String tag;				//태그
	private String voice_info;		//음성안내 문구
	private String location;		//위치(위도와 경도:**.***,**.****)
	private String address;			//주소
	private String phone;			//연락처
	private String state;			//서비스 상태
	private String date;			//등록날짜
	private String openday;			//운영요일(1은 운영, 0은 미운영: 1011100)
	private String opentime;		//오픈시간
	private String closetime;		//닫는시간
	private String endtime;			//마감시간
	public String getPostindex() {
		return postindex;
	}
	public void setPostindex(String postindex) {
		this.postindex = postindex;
	}
	public String getDefault_lang() {
		return default_lang;
	}
	public void setDefault_lang(String default_lang) {
		this.default_lang = default_lang;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getVoice_info() {
		return voice_info;
	}
	public void setVoice_info(String voice_info) {
		this.voice_info = voice_info;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOpenday() {
		return openday;
	}
	public void setOpenday(String openday) {
		this.openday = openday;
	}
	public String getOpentime() {
		return opentime;
	}
	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}
	public String getClosetime() {
		return closetime;
	}
	public void setClosetime(String closetime) {
		this.closetime = closetime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
}
