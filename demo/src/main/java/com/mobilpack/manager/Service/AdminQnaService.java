package com.mobilpack.manager.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Exception.NoinfoException;
import com.mobilpack.manager.Model.QnaModel;

@Service
public class AdminQnaService {
	@Autowired
	Dao dao;
	
	//검색 결과 인출을 위한 임시 변수
	private boolean flag = false;
	private Map<String,String> recent_Search = new HashMap<>();
	
	//검색 메서드
	public List<QnaModel> getQnaList(Map<String, String> param) throws Exception {
		//Limit절 추출
		String page;
		String count;
		if (!flag) {
			page = param.get("page");
			count = param.get("count");
			//페이징 처리
			page = Integer.toString((Integer.parseInt(page)-1) * Integer.parseInt(count));
		} else {
			//전체 카운트 들어갈때
			param = recent_Search;
			page = null;
			count = null;
		}
		//페이징을 위한 전체 게시물 메서드 추출을 위해 파라메터 보관 및 페이징 삭제
		//다시 이 조건에 왔을때 flag에 따라 전체 게시물 카운트 쿼리로 바뀜
		if (flag) {
			recent_Search = null;
			flag = false;
		} else {
			recent_Search = param;
			flag = true;
		}
		//Where절 추출
		String category = param.get("category");
		String title = param.get("title");
		String answer = param.get("answer");
		String min = param.get("min");
		String max = param.get("max");
		//쿼리
		return dao.getAdminQnaList(category, title, answer, min, max, page, count);
	}
	
	/**
	 * Qna Detail 인출 서비스
	 * @param index
	 * @return
	 * @throws NoinfoException
	 */
	public QnaModel getQna (String index) throws NoinfoException {
		QnaModel target = dao.getAdminQnaPost(index);
		if (target == null ) {
			throw new NoinfoException("Can't find post");
		} else {
			return target;
		}
	}
	
	/**
	 * 답변 기능 (수정, 새로 삽입 모두)
	 * @param index
	 * @param content
	 */
	public void setReply(String index, String content, String admin_id) {
		dao.setReply(index, content, admin_id);
	}
}
