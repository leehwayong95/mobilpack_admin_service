package com.mobilpack.manager.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		String basePath =".\\\\upload";
		//기본 경로에 postindex값을 넣어 게시글 별로 경로 생성
		File folderPath = new File(basePath+"//"+folderName);	
		// 해당 경로가 없을 겅우 자동으로 폴더 생성
		if(!folderPath.exists()) {
			folderPath.mkdir();
		}
		//다중 파일 업로드를 위한 for문
    	for(MultipartFile file : files) {
    		//파일 모델 객체 생성
    		FileModel RepeatModel = new FileModel();
    		//파일명 변수에 저장
    		String originalName = file.getOriginalFilename();
    		//파일 확장자 검사를 위해 split으로 확장자 구분
    		String[] nameCheck = 
    				originalName.split("\\.");
    		//고유식별자 생성
    		UUID newName = UUID.randomUUID();
    		//고유식별자를 이용한 파일 생성
    		String filePath = basePath+"\\\\"+folderName+"\\\\" + newName+"."+nameCheck[1];
    		//png와 jpg 확장자만 받기 위해 예외처리 진행
    		if(nameCheck[1].equals("png")
    				   ||nameCheck[1].equals("jpg")) {
    					try {
    						//확장자가 맞을 경우 파일 업로드
    						Path path = Paths.get(filePath);
    					    Files.write(path, file.getBytes());
    					} 
    					catch(Exception e) {
    						//확장자가 맞지 않을 경우 false를 반환하고 코드 중지
    						e.printStackTrace();
    						return "false";
    					}
    					//파일모델에 이름,경로,고유식별명,게시글index를 넣고 insert함
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
    			return "wrong extension";
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
