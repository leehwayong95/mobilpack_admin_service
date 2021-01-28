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
import com.mobilpack.manager.Service.AdminManagerService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/su/admin")
public class AdminManagerController {
	@Autowired
	private AdminManagerService adminservice;

	// (관리자 id 중복 체크 )
	@GetMapping("/idcheck")
	public String IdAgaincheck(@RequestParam String id) {
		try {
			String Check = adminservice.idcheck(id);
			System.out.println(Check);
			if (Check == null) {
				// 중복 id 없음
				Check = "ok";
				return Check;
			} else {
				// id 중복됨
				Check = "1";
				return Check;
			}
		} catch (Exception e) {
			String Check = "0";
			return Check;
		}
	}

	// (관리자 등록) ( 0:정상 작동 1: 오류 )
	@PostMapping("/join")
	public String join(@RequestBody AdminModel admin) {
		try {
			adminservice.joinadmin(admin.getAdmin_id(), admin.getName(), admin.getPhone(),admin.getEmail());
			String check = "ok";
			return check;
		} catch (Exception e) {
			String check = "1";
			return check;
		}
	}

	// (관리자 수정) ( 0k:정상 작동 1: 오류 )
	@PostMapping("/edit")
	public String edit(@RequestBody AdminModel admin) {
		try {
			adminservice.editadmin(admin.getAdmin_id(), admin.getName(), admin.getPhone(), admin.getEmail());
			String check = "ok";
			return check;
		} catch (Exception e) {
			String check = "1";
			return check;
		}
	}

	//(비밀번호 초기화)
	@PostMapping("/pwreset")
	public String pwreset(@RequestBody AdminModel admin) {
		try {
			adminservice.pwreset(admin.getAdmin_id());
			String check = "ok";
			return check;
		} catch (Exception e) {
			String check = "1";
			return check;
		}
	}

	
	// (관리자 검색)
	@GetMapping("/listsearch")
	public ResponseEntity<Map<String, Object>> Search1(@RequestParam int Currentpage, int Number, String id, String name, String createat,String updateat) {
		Currentpage = Number * (Currentpage - 1); // 서비스로 옮겨야함
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<AdminModel> adminsearchlist = new ArrayList<AdminModel>();
		List<AdminModel> adminsearchlistcount = new ArrayList<AdminModel>();
		
		String testCurrentpage=Integer.toString(Currentpage);
		String testNumber=Integer.toString(Number);
		// (검색한 결과 게시글 리스트 담는곳)
		System.out.println("여기가 Currentpage번호야"+Currentpage);
		System.out.println("여기가 Number번호야"+Number);
		adminsearchlist=adminservice.searchadminlist(testCurrentpage, testNumber, id, name, createat, updateat);
		// (검색한 결과 게시글 수 담는곳)
		testCurrentpage="";
		testNumber="";
		adminsearchlistcount=adminservice.searchadminlist(testCurrentpage, testNumber, id, name, createat, updateat);
		int listsize = adminsearchlistcount.size();
		
		map.put("result",adminsearchlist);
		map.put("count",listsize);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK );
		
	}

	// (관리자 상세페이지)
	@PostMapping("/information")
	public AdminModel Detailpage(@RequestBody AdminModel admin) {
		return adminservice.admininformation(admin.getAdmin_id());
	}

	// (관리자 삭제)  ( ok:정상 작동 1: 오류 )
	@PostMapping("/delete")
	public String delete(@RequestBody AdminModel admin) {
		try {
			adminservice.deleteadmin(admin.getAdmin_id());
			String check = "ok";
			return check;
		} catch (Exception e) {
			String check = "1";
			return check;
		}
	}

	/*
	 * @GetMapping("/search") public ResponseEntity<Map<String, Object>>
	 * adminSearch(
	 * 
	 * @RequestBody Map<String, Object> param, HttpServletRequest req) { // do
	 * something
	 * 
	 * return new ResponseEntity<Map<String, Object>>(resultMap, status); }
	 */
}
