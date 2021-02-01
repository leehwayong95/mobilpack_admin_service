package com.mobilpack.manager.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobilpack.manager.Exception.NoinfoException;
import com.mobilpack.manager.Model.QnaModel;
import com.mobilpack.manager.Service.AdminQnaService;
import com.mobilpack.manager.Service.JwtService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/su/qna")
public class AdminQnaController {
	@Autowired
	AdminQnaService adminQnaService;
	@Autowired
	JwtService jwtService;
	
	//Qna자세히 보기 API
	@GetMapping("/{index}")
	public ResponseEntity<Map<String, Object>> qnaInfo(
			@PathVariable String index,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			resultMap.put("status", true);
			resultMap.put("post", adminQnaService.getQna(index));
			status = HttpStatus.OK;
		} catch (NoinfoException e) {
			resultMap.put("status", false);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("status", false);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	//검색 API
	@PatchMapping("/search")
	public ResponseEntity<Map<String, Object>> qnaSearch(
			@RequestBody Map<String, String> param,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			//페이징 포함 개수 반환
			List<QnaModel> searchList = adminQnaService.getQnaList(param);
			resultMap.put("list", searchList);
			//전체 검색 결과 개수 반환
			//반환 형이 안맞아서 post index 에 전체 게시글 값을 넣음.
			QnaModel model = adminQnaService.getQnaList(null).get(0);
			resultMap.put("count",model.getQnaindex());
			status = HttpStatus.OK;
		}	catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
			if (e.getMessage() == "Bad Request")
				status = HttpStatus.BAD_REQUEST;
			else
				status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	//QNA 삭제 API
	@DeleteMapping("/{index}")
	public ResponseEntity<Map<String, Object>> qnaDelete(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	//QNA 답변 수정 및 삭제
	@PostMapping("/chat/{index}")
	public ResponseEntity<Map<String, Object>> qnaChatUpdate(
			@PathVariable String index,
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String admin_id = jwtService.getInfo(req.getHeader("authorization")).get("admin_id").toString();
			adminQnaService.setReply(index, param.get("content").toString(), admin_id );
			resultMap.put("status",true);
			status=HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("status", false);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	//QNA 답변 삭제
	@DeleteMapping("/chat/{index}")
	public ResponseEntity<Map<String, Object>> qnaChatDelete(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
}
