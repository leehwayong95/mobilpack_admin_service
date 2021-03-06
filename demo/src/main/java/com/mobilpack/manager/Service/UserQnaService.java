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
public class UserQnaService {
	@Autowired
	Dao dao;
	
	private Map<String,String> recent_Search = new HashMap<>();
	private boolean flag = false;
	
	//QnA글쓰기 메서드
	public void UserQnaWrite (QnaModel qna) {
		dao.UserQnaWrite(qna);
	}
	
	//Qna 검색 메서드
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
		String userid = param.get("userID");
		
		//쿼리
		return dao.getQnaList(category, title, answer, min, max, page, count, userid);
	}
	
	//Qna 자세히 보기 메서드
	public QnaModel getQnaPost(String index) throws NoinfoException {
		QnaModel target = dao.getQnaPost(index);
		if(target == null) {
			throw new NoinfoException("Can't find qna post");
		} else {
			return target;
		}
	}
	//Qna 삭제 메서드
	public void deleteQnaPost (String index, String id) {
		dao.deleteQnaPost(index, id);
	}
	//Qna 수정 메서드
	public void updateQnaPost (QnaModel model, String id, String index) {
		model.setUser_id(id);
		model.setQnaindex(index);
		dao.updateQnaPost(model);
	}
}