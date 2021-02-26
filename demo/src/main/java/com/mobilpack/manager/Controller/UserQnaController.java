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
import com.mobilpack.manager.Service.JwtService;
import com.mobilpack.manager.Service.UserQnaService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/qna")
public class UserQnaController {
	@Autowired
	UserQnaService qnaService;
	@Autowired
	JwtService jwtService;
	
	@PatchMapping("/search")
	public ResponseEntity<Map<String, Object>> userQnaSearch(
			@RequestBody Map<String, String> param,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			//페이징 포함 개수 반환
			String id = jwtService.getUserID(req.getHeader("Authorization"));
			param.put("userID", id);
			List<QnaModel> searchList = qnaService.getQnaList(param);
			resultMap.put("list", searchList);
			//전체 검색 결과 개수 반환
			//반환 형이 안맞아서 post index 에 값을 넣음.
			QnaModel model = qnaService.getQnaList(null).get(0);
			resultMap.put("count",model.getQnaindex());
			status = HttpStatus.OK;
		}	catch (Exception e) {
			resultMap.put("result", false);
			if (e.getMessage() == "Bad Request")
				status = HttpStatus.BAD_REQUEST;
			else
				status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/{index}")
	public ResponseEntity<Map<String, Object>> userQnaInfo(
			@PathVariable String index,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			QnaModel target = qnaService.getQnaPost(index);
			boolean permission = jwtService.getInfo(req.getHeader("Authorization")).get("user_id").toString().equals(target.getUser_id());
			resultMap.put("permission", permission);
			resultMap.put("QnaPostModel", target);
			status = HttpStatus.OK;
		} catch (NoinfoException e) {
			resultMap.put("result", false);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("result", false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@DeleteMapping("/{index}")
	public ResponseEntity<Map<String, Object>> userQnadelete(
			@PathVariable String index,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String id = jwtService.getInfo(req.getHeader("Authorization")).get("user_id").toString();
			qnaService.deleteQnaPost(index, id);
			resultMap.put("result", true);
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("result", false);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PutMapping ("/{index}")
	public ResponseEntity<Map<String, Object>> userQnaEdit(
			@PathVariable String index,
			@RequestBody QnaModel model,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String id = jwtService.getInfo(req.getHeader("Authorization")).get("user_id").toString();
			qnaService.updateQnaPost(model,  id, index);
			resultMap.put("result", true);
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("result", false);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> userQnaWrite(
			@RequestBody QnaModel model,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			//토큰해석 : 글쓴이 파악 및 설정
			model.setUser_id(jwtService.getInfo(req.getHeader("Authorization")).get("user_id").toString());
			qnaService.UserQnaWrite(model);
			status = HttpStatus.OK;
		}	catch (Exception e) {
			resultMap.put("result", false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
