package com.mobilpack.manager.Model;

public class FileModel {
	private String fileindex;		//파일의 index(파일 5개까지 받기 때문)
    private String postindex;		//게시글 index(어떤 게시글의 파일인지 구분하기 위해)
    private String filename;   	//파일 이름
    private String fileuuid;  	//파일 용량
    private String filepath;	//파일 경로
	
	public String getFileindex() {
		return fileindex;
	}
	public void setFileindex(String fileindex) {
		this.fileindex = fileindex;
	}
	public String getPostindex() {
		return postindex;
	}
	public void setPostindex(String postindex) {
		this.postindex = postindex;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileuuid() {
		return fileuuid;
	}
	public void setFileuuid(String fileuuid) {
		this.fileuuid = fileuuid;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}
