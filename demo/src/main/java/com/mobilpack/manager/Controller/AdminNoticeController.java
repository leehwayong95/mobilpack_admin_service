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

@CrossOrigin(origins ="http://localhost:8080")
@RestController
@RequestMapping("/api/su/notice")
public class AdminNoticeController {
	@Autowired
	private NoticeManagerService noticeservice;
	
	//(공지사항 검색)
	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> noticeSearch(@RequestParam int Currentpage, int Number, String language, String titlename) {
		Currentpage = Number * (Currentpage - 1); 
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<NoticeModel> noticesearchlist = new ArrayList<NoticeModel>();
		noticesearchlist=noticeservice.searchnotice(Currentpage,Number, language,titlename);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK );
	}	
	
	//(공지사항 수정)
	@PostMapping("/edit")
	public String editnotice(@RequestBody NoticeModel notice) {
		try {
			noticeservice.editnotice(notice.getIndex(),notice.getId(), notice.getEnabled(),
					notice.getLanguage(), notice.getTitle(),notice.getContent(), notice.getTopsetting());
			String check = "ok";
			return check;
		} catch (Exception e) {
			String check = "1";
			return check;
		}
	}
	
	//(공지사항 작성)
	@PostMapping("/insert")
	public String noticeCreate(@RequestBody NoticeModel notice) {
		try {
			noticeservice.insertnotice(notice.getId(), notice.getEnabled(), notice.getLanguage(),
					notice.getTitle(),notice.getContent(),notice.getTopsetting());
			String check = "ok";
			return check;
		} catch (Exception e) {
			String check = "1";
			return check;
		}

	}
	
	 //(공지사항 상세)
	@GetMapping("/detail")
	public NoticeModel detailnotice(@RequestBody NoticeModel notice) {
		return noticeservice.detailnotice(notice.getIndex());
	}
	
	//(공지사항 삭제)
	@GetMapping("/delete")
	public String deletenotice(@RequestBody NoticeModel notice) {
		try {
			noticeservice.deletenotice(notice.getIndex());
			String check = "ok";
			return check;
		} catch (Exception e) {
			String check = "1";
			return check;
		}
	}
	
}
