package com.mobilpack.manager.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeModel {
	private String postindex;
	private String id;
	private String enabled;
	private String language;
	private String title;
	private String content;
	private String topsetting;
	private String createat;
	private String updateat;
	private String viewcount;
	public String getPostindex() {
		return postindex;
	}
	public void setPostindex(String postindex) {
		this.postindex = postindex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTopsetting() {
		return topsetting;
	}
	public void setTopsetting(String topsetting) {
		this.topsetting = topsetting;
	}
	public String getCreateat() {
		return createat;
	}
	public void setCreateat(String createat) {
		this.createat = createat;
	}
	public String getUpdateat() {
		return updateat;
	}
	public void setUpdateat(String updateat) {
		this.updateat = updateat;
	}
	public String getViewcount() {
		return viewcount;
	}
	public void setViewcount(String viewcount) {
		this.viewcount = viewcount;
	}
	
}
