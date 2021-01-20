package com.mobilpack.manager.Model;

public class FileModel {
	private int fileindex;		//파일의 index(파일 5개까지 받기 때문)
    private int postindex;		//게시글 index(어떤 게시글의 파일인지 구분하기 위해)
    private String fileName;   	//파일 이름
    private String fileSize;  	//파일 용량
    private String filePath;	//파일 경로
	public int getFileindex() {
		return fileindex;
	}
	public void setFileindex(int fileindex) {
		this.fileindex = fileindex;
	}
	public int getPostindex() {
		return postindex;
	}
	public void setPostindex(int postindex) {
		this.postindex = postindex;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
    
    
}
