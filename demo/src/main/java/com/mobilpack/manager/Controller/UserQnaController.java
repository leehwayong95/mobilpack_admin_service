package com.mobilpack.manager.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobilpack.manager.Model.QnaModel;
import com.mobilpack.manager.Service.JwtService;
import com.mobilpack.manager.Service.UserQnaService;

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
			List<QnaModel> searchList = qnaService.getQnaList(param);
			resultMap.put("list", searchList);
			//전체 검색 결과 개수 반환
			List<QnaModel> tmp = qnaService.getQnaList(null);
			QnaModel tmp2= tmp.get(0);
			resultMap.put("count",tmp2.getQnaindex());
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
	
	@GetMapping("/{index}")
	public ResponseEntity<Map<String, Object>> userQnaInfo(
			@PathVariable String index,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			
		}	catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
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
			//토큰해석
			//jwtService.getInfo(req.getHeader("authorization"))
			qnaService.UserQnaWrite(model);
		}	catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
