package com.mobilpack.manager.Dao;

import java.util.List;

import com.mobilpack.manager.Model.CommentModel;
import com.mobilpack.manager.Model.FileModel;
import com.mobilpack.manager.Model.PostModel;

public interface UserRecommandDao {
	/**
	 * 추천장소 게시글 불러오기
	 * @param index 인덱스 번호
	 * @return PostModel 게시글 모델로 반환
	 */
	public PostModel getRecommandPost (String index);
	/**
	 * 유저용 추천장소 게시글 리스트 불러오기
	 * @param category 카테고리
	 * @param language 언어
	 * @param state 활성화, 비활성화 상태
	 * @param titlename 제목
	 * @param currentPage 불러올 현재 페이지(선계산 해서 넣어야함)
	 * @param number 불러올 갯수
	 * @return List<PostModel> 리스트로 반환
	 */
	public List<PostModel> getRecommandList (String category, String language, String state, String titlename,Integer currentPage,Integer number);
	
	/**
	 * 유저용 추천장소 리뷰 불러오기
	 * @param postindex 게시물 인덱스번호
	 * @return List<CommentModel> 리스트로 반환
	 */
	public List<CommentModel> getCommentsList (String postindex);
	
	/**
	 * 유저용 추천장소 첨부파일 불러오기 
	 * @param postindex 게시물 인덱스 번호
	 * @return List<FileModel> 리스트로 반환
	 */
	public List<FileModel> getFileList (String postindex);
}
