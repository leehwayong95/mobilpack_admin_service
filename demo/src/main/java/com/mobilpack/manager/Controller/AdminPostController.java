package com.mobilpack.manager.Controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mobilpack.manager.Model.FileModel;
import com.mobilpack.manager.Model.PostModel;
import com.mobilpack.manager.Service.AdminRecommandService;

@CrossOrigin(origins = "http://localhost/")
@RestController
@RequestMapping("/api/su/post")
public class AdminPostController {
	@Autowired
	AdminRecommandService service;
	//게시글 등록 부분 api
	@PostMapping("/create")
	public String postCreate(@ModelAttribute PostModel post,
			@RequestPart("files") List<MultipartFile> files) {
		//vue에서 오는 값들을 insert문으로 DB에 넣기
		//동시에 postindex값을 리턴 받는다.
        service.RecommandCreate(post);
		//postindex값을 토대로 폴더를 구분
		String folderName = post.getPostindex();
		System.out.println(post.getPostindex());
		//기본 경로를 설정함
		String basePath ="C:\\\\Users\\shjo2\\Desktop\\Hub"
				+ "\\GitHub\\MobilPack\\demo\\src"
				+ "\\main\\resources\\images";
		//기본 경로에 postindex값을 넣어 게시글 별로 경로 생성
		File folderPath = new File(basePath+"\\"+folderName);	
		// 해당 경로가 없을 겅우 자동으로 폴더 생성
		if(!folderPath.exists()) {
			folderPath.mkdir();
		}
		//파일을 받는다
    	for(MultipartFile file : files) {
    		FileModel RepeatModel = new FileModel();
    		String originalName = file.getOriginalFilename();
    		String[] nameCheck = 
    				originalName.split("\\.");
    		UUID newName = UUID.randomUUID();
    		System.out.println(newName+"."+nameCheck[1]);
    		String filePath = folderPath + "\\" + newName+"."+nameCheck[1];
    		System.out.println(filePath);
    		File dest = new File(filePath);
    		if(nameCheck[1].equals("png")
    				   ||nameCheck[1].equals("jpg")) {
    					try {
    			    		file.transferTo(dest);
    			    		dest.length();
    					} 
    					catch(Exception e) {
    						e.printStackTrace();
    						return "false";
    					}
    					RepeatModel.setPostindex(post.getPostindex());
    					RepeatModel.setFilename(originalName);
    					RepeatModel.setFilepath(filePath);
    					RepeatModel.setFileuuid(newName.toString());
    					try {
    					service.FileCreate(RepeatModel);
    					}
    					catch(Exception e) {
    						e.printStackTrace();
    						return "false";
    					}
    				}
    		else {
    			return "right extension";
    		}
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
