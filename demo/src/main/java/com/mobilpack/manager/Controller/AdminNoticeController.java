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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobilpack.manager.Model.AdminModel;
import com.mobilpack.manager.Model.NoticeModel;
import com.mobilpack.manager.Service.NoticeManagerService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/su/notice")
public class AdminNoticeController {
	@Autowired
	private NoticeManagerService noticeservice;

	// (공지사항 검색)
	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> noticeSearch(@RequestParam int Currentpage, int Number, String language,
			String titlename) {
		Currentpage = Number * (Currentpage - 1);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<NoticeModel> noticesearchlist = new ArrayList<NoticeModel>();
		List<NoticeModel> noticesearchlistcount = new ArrayList<NoticeModel>();

		String testCurrentpage = Integer.toString(Currentpage);
		String testNumber = Integer.toString(Number);

		noticesearchlist = noticeservice.searchnotice(testCurrentpage, testNumber, language, titlename);

		testCurrentpage = "";
		testNumber = "";

		noticesearchlistcount = noticeservice.searchnotice(testCurrentpage, testNumber, language, titlename);
		int listsize = noticesearchlistcount.size();
		map.put("result", noticesearchlist);
		map.put("count", listsize);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	// (공지사항 수정)
	@PostMapping("/edit")
	public String editnotice(@RequestBody NoticeModel notice) {
		try {
			noticeservice.editnotice(notice.getPostindex(), notice.getId(), notice.getLanguage(),
					notice.getTitle(), notice.getContent(), notice.getTopsetting());
			String check = "TRUE";
			return check;
		} catch (Exception e) {
			String check = "FALSE";
			return check;
		}
	}

	// (공지사항 작성)
	@PostMapping("/insert")
	public String noticeCreate(@RequestBody NoticeModel notice) {
		noticeservice.insertnotice(notice.getId(), notice.getTopsetting(), notice.getLanguage(), notice.getTitle(),
				notice.getContent());
		String check = "TRUE";
		return check;
	}

	// (공지사항 상세)
	@GetMapping("/detail")
	 public NoticeModel detailnotice(@RequestParam String postindex) {
	 return noticeservice.detailnotice( postindex); 
	 }
	
	// (공지사항 조회수 증가)
	@GetMapping("/plusviewcount")
	 public String plusviewcount(@RequestParam String postindex) {
	 noticeservice.plusviewcount(postindex);
	 return "ok";
	 }

	// (공지사항 게시중단)
	@PostMapping("/stopposting")
	 public String stopposting(@RequestBody NoticeModel notice) {
	 System.out.println( notice.getPostindex());
	 noticeservice.stopposting( notice.getPostindex()); 
	 return "ok";
	 }
	 
	// (공지사항 삭제)
	@PostMapping("/delete")
	public String deletenotice(@RequestBody NoticeModel notice) {
		try {
			noticeservice.deletenotice(notice.getPostindex());
			String check = "TRUE";
			return check;
		} catch (Exception e) {
			String check = "FALSE";
			return check;
		}
	}

}
