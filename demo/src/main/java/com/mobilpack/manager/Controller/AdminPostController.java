package com.mobilpack.manager.Controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mobilpack.manager.Model.PostModel;

@CrossOrigin(origins = "http://localhost/")
@RestController
@RequestMapping("/api/su/post")
public class AdminPostController {
	//게시글 등록 부분 api
	@PostMapping("/create")
	public String postCreate(@RequestBody PostModel post,
			@RequestPart("files") MultipartFile file) {
		// 사진 등록 부분 test 중
		String test = "123";
		String basePath ="C:\\Users\\shjo2\\Desktop\\Hub\\GitHub\\MobilPack\\demo\\src\\main\\resources\\images";
		File test1 = new File(basePath+"/"+test);	// 경로에 폴더가 없을 경우 자동으로 생성
		if(!test1.exists()) {
			test1.mkdir();
		}
    	
    		String originalName = file.getOriginalFilename();
    		String filePath = test1 + "/" + originalName;
    		File dest = new File(filePath);
    		String[] nameCheck = dest.getName().split("\\.");
    		if(nameCheck[1].equals("png")||nameCheck[1].equals("jpg")) {
	    		try {
		    		file.transferTo(dest);
		    		dest.length();
	    		} 
	    		catch(Exception e) {
	    			e.printStackTrace();
	    			return "false";
	    		}
    		}
    		else {
    			return "right extension";
    		}
    	return "uploaded";
	}

	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> postSearch(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/info")
	public ResponseEntity<Map<String, Object>> postInfo(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/update")
	public ResponseEntity<Map<String, Object>> postUpdate(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, Object>> postDelete(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/translate/info")
	public ResponseEntity<Map<String, Object>> postTranslateInfo(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/translate/create")
	public ResponseEntity<Map<String, Object>> postTranslateCreate(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@DeleteMapping("/comment/delete")
	public ResponseEntity<Map<String, Object>> postCommentDelete(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/place/enable")
	public ResponseEntity<Map<String, Object>> postPlaceEnable(
			@RequestBody Map<String, Object> param,
			HttpServletRequest req) {
		// do something
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	
}
