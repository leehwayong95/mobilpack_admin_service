package com.mobilpack.manager.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	// (관리자 처음 페이지) 처음 페이지 입장 시 보여져야 하는 처음 관리자 항목들 (현재페이지,몇개 기준으로 보여줄지)
	@GetMapping("/list")
	public List<AdminModel> Getboard(@RequestParam int Currentpage, int Number) {// Currentpage: 현재 페이징 번호 , Number :게시글개수
		Currentpage=Number*(Currentpage-1); //서비스로 옮겨야함
		return adminservice.getadminlist(Currentpage, Number);
	}

	// (관리자 처음페이지) 페이지 최종 몇개 페이지 필요한지 반환하는 것 (위에꺼랑 같이 보낼수 있다?) 카운트
	@GetMapping("/listcount")
	public int Allpagecount(@RequestParam(value = "Number", required = false) int Number) {
		int count; // 전체 항목수
		int Total; // 필요한 페이지 수 표시
		try {
			count = adminservice.getadminlistcount(); // 전체 관리자 항목 수 구하는곳 가능하면 서비스로 옮기자

			Total = count / Number;
			if (count % Number > 0) {
				Total = Total + 1;
			} else {
			}
			return Total;
		} catch (Exception e) {
			System.out.println("관리자 페이징 오류");
			return -1;
		}
	}

	// (관리자 id 중복 체크 )
	@GetMapping("/idcheck")
	public String IdAgaincheck(@RequestParam String id) {
		try {
			String Check = adminservice.idcheck(id);
			System.out.println(Check);
			if (Check == null) {
				// 중복 id 없음
				Check = "0";
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
			adminservice.joinadmin(admin.getAdmin_id(), admin.getPassword(), admin.getName(), admin.getPhone(),admin.getEmail());
			String check = "0";
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


	//(관리자 검색)
	@GetMapping("/listsearch")
	public List<AdminModel> Search (@RequestParam int Currentpage,int Number,String id,String name, String createat ,String updateat){
		Currentpage=Number*(Currentpage-1); //서비스로 옮겨야함
		return adminservice.searchadminlist(Currentpage, Number,id,name,createat,updateat);
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

	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> adminSearch(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/info")
	public ResponseEntity<Map<String, Object>> adminInfo(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/adminupdate")
	public ResponseEntity<Map<String, Object>> adminUpdate(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@DeleteMapping("/admindelete")
	public ResponseEntity<Map<String, Object>> adminDelete(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/adminpwreset")
	public ResponseEntity<Map<String, Object>> adminPwreset(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
