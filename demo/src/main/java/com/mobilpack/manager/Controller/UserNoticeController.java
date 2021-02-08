package com.mobilpack.manager.Controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobilpack.manager.Model.NoticeModel;
import com.mobilpack.manager.Service.UserNoticeService;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserNoticeController {
	
	@Autowired
	private UserNoticeService usernoticeservice;

	// (유저 공지사항 검색)
	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>>  usersearchnotice(@RequestParam int Currentpage, int Number,
		String title) {
		Currentpage = Number * (Currentpage - 1);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<NoticeModel> noticesearchlist = new ArrayList<NoticeModel>();
		List<NoticeModel> noticesearchlistcount = new ArrayList<NoticeModel>();

		String testCurrentpage = Integer.toString(Currentpage);
		String testNumber = Integer.toString(Number);

		noticesearchlist = usernoticeservice. usersearchnotice(testCurrentpage, testNumber, title);

		testCurrentpage = "";
		testNumber = "";

		noticesearchlistcount = usernoticeservice. usersearchnotice(testCurrentpage, testNumber, title);
		int listsize = noticesearchlistcount.size();
		map.put("result", noticesearchlist);
		map.put("count", listsize);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	// (유저 공지사항 상세)
	@GetMapping("/detail")
	public NoticeModel userdetailnotice(@RequestParam String postindex) {
		return usernoticeservice.userdetailnotice(postindex);
	}

	// (유저 공지사항 조회수 증가)
	@GetMapping("/userplusviewcount")
	public String userplusviewcount(@RequestParam String postindex) {
		usernoticeservice.userplusviewcount(postindex);
		return "ok";
	}

}
