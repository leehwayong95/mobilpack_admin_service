package com.mobilpack.manager.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobilpack.manager.Model.PostModel;
import com.mobilpack.manager.Model.UserModel;
import com.mobilpack.manager.Service.AdminRecommandService;
import com.mobilpack.manager.Service.JwtService;
import com.mobilpack.manager.Service.UserMyService;
import com.mobilpack.manager.Service.UserRecommandService;

@RestController
@RequestMapping("/api/post")
public class UserPostController {
	@Autowired
	AdminRecommandService adminService;
	@Autowired
	UserRecommandService userService;
	@Autowired
	JwtService jwtService;
	@Autowired
	UserMyService userInfoService;
	
	/**
	 * User 추천 장소 요청 API
	 * @param param 검색에 따른 파라메터
	 * @param req header에서 token을 가져오기 위한 부분
	 * @return 검색결과 개수와 검색 결과 리스트(페이징한 후)표출
	 */
	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> userSearch(
			@RequestParam Map<String, String> param,
			HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			/*
			 * Request User Contry 인출 부분
			 */
			String requestToken = req.getHeader("authorization");
			String requestUserID = jwtService.getUserID(requestToken);
			UserModel requestUser = userInfoService.getUserInfo(requestUserID);
			String requestUserContry = requestUser.getCountry();
			String requestLanguage = null;
			switch(requestUserContry) {
				case"KR":
					requestLanguage = "1";
					break;
				case"EN":
					requestLanguage = "2";
					break;
				case"JP":
					requestLanguage = "4";
					break;
				case"CN":
					requestLanguage = "8";
					break;
			}
			/*
			 * 다른 매개변수 세팅
			 */
			String category = param.get("category");
			String name = param.get("name");
			Integer page = Integer.parseInt(param.get("page"));
			Integer count = Integer.parseInt(param.get("count"));
			page=count*(page-1);
			
			/*
			 * 검색 결과 인출
			 */
			List<PostModel> searchList = adminService.RecommandList(category, requestLanguage, "1", name, page, count);
			resultMap.put("result", searchList);
			/*
			 * 페이징을 위한 검색 조건에 따른 개수 구하기
			 */
			page = null;
			count = null;
			int ListCount = adminService.RecommandList(category, requestLanguage, "1", name, page, count).size();
			resultMap.put("count", ListCount);
			
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("status", false);
			resultMap.put("reason", e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/{index}")
	public PostModel userInfo(
			@PathVariable String index) {
		return userService.getRecommandPost(index);
	}
}
