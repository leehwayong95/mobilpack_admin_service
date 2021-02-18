package com.mobilpack.manager.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Model.CommentModel;
import com.mobilpack.manager.Model.FileModel;
import com.mobilpack.manager.Model.PostModel;
import com.mobilpack.manager.Model.TranslateModel;

@Service
public class AdminRecommandService {
	@Autowired
	Dao dao;
	
	//게시글 등록 관련 서비스
	//게시글 관련 내용 DB에 insert 및 파일 경로 및 이름 insert
	public void RecommandCreate(PostModel post) {
		dao.RecommandCreate(post);
	}
	//파일 경로, 이름 등의 정보를 db에 insert
	public void FileCreate(FileModel file) {
		dao.FileCreate(file);
	}
	//게시글 리스트 내용을 조건에 따라 select문으로 가져옴
	public List<PostModel> RecommandList(String category, String language, String state, String titlename,Integer currentPage, Integer number){
		return dao.RecommandList(category, language, state, titlename,currentPage,number);
	}
	//게시글 상세 정보 내용 가져오기
	public PostModel RecommandDetail(String postindex) {
		return dao.RecommandDetail(postindex);
	}
	//파일 다운
	public Resource FileDownload(String fileindex) throws MalformedURLException {
		FileModel path =  dao.FileDownload(fileindex);
		String targetPath = path.getFilepath();
    	File target = new File(targetPath);
    	return new UrlResource(target.toURI());
	}
	//인덱스 불러오기
	public List<FileModel> IndexOutput(String postindex){
		return dao.IndexOutput(postindex);
	}
	//게시글 수정하기
	public String RecommandUpdate(PostModel post) {
		try { 
			dao.RecommandUpdate(post);
			return "SUCCESS";
			}
		catch(Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
	}
	//파일 수정하기
	public void FileUpdate(FileModel file) {
			dao.FileUpdate(file);
	}
	//게시글 리뷰 받기
	public List<CommentModel> RecommandComments(String postindex){
		return dao.RecommandComments(postindex);
	}
	//게시글 삭제
	public void RecommandDelete(String postindex) {
		dao.RecommandDelete(postindex);
	}
	//게시글 리뷰 삭제
	public void CommentDelete(String postindex) {
		dao.CommentDelete(postindex);
	}
	//파일 삭제
	public void FileDelete(String fileindex) {
		dao.FileDelete(fileindex);
	}
	//번역 등록
	public void TranslateCreate(TranslateModel translate) {
		dao.TranslateCreate(translate);
	}
	//번역 상세 정보
	public List<TranslateModel> TranslateInfo(String postindex,String language){
		return dao.TranslateInfo(postindex,language);
	}
	//게시글 리뷰 하나 삭제
	public void CommentOneDelete(String commentindex) {
		dao.CommentOneDelete(commentindex);
	}
	//추천장소 서비스 활성화
	public void StateUpdate(String postindex) {
		dao.StateUpdate(postindex);
	}
}
