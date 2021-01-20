package com.mobilpack.manager.Model;

public class PostModel {
	private String default_Lang;	//기본 언어
	private String category;		//카테고리
	private String title;			//추천장소명
	private String content;			//관광정보
	private String tag;				//태그
	private String voice_Info;		//음성안내 문구
	private String location;		//위치(위도와 경도:**.***,**.****)
	private String address;			//주소
	private String phone;			//연락처
	private int day;				//운영요일(1은 운영, 0은 미운영: 1011100)
	private String operationTime;	//오픈시간
	private String closeTime;		//닫는시간
	private String endTime;			//마감시간
	
	public String getDefault_Lang() {
		return default_Lang;
	}
	public void setDefault_Lang(String default_Lang) {
		this.default_Lang = default_Lang;
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getVoice_Info() {
		return voice_Info;
	}
	public void setVoice_Info(String voice_Info) {
		this.voice_Info = voice_Info;
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
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
