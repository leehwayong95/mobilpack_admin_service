package com.mobilpack.manager.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mobilpack.manager.Model.CommentModel;
import com.mobilpack.manager.Model.FileModel;
import com.mobilpack.manager.Model.PostModel;
import com.mobilpack.manager.Model.TranslateModel;
import com.mobilpack.manager.Service.AdminRecommandService;
import com.mobilpack.manager.Service.JwtService;

@CrossOrigin(origins = "http://localhost/")
@RestController
@RequestMapping("/api/su/post")
public class AdminPostController {
	@Autowired
	AdminRecommandService service;
	@Autowired
	JwtService jwtService;
	//게시글 등록 부분 api
	@PostMapping("/create")
	public String postCreate(@ModelAttribute PostModel post,
			@RequestPart("files") List<MultipartFile> files,
			HttpServletRequest req) {
		//vue에서 오는 값들을 insert문으로 DB에 넣기
		//jwtservice를 이용해 토큰에 있는 관리자 id를 받기
		try {
		String admin_id = jwtService.getInfo(req.getHeader("authorization")).get("admin_id").toString();
		post.setAdmin_id(admin_id);
		}
		catch(Exception e) {
			e.printStackTrace();
			return "FALSE";
		}
		//쿼리문을 통해 DB에 insert함
        service.RecommandCreate(post);
		//postindex값을 토대로 폴더를 구분함
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
    						return "FALSE";
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
    						return "FALSE";
    					}
    				}
    		else {
    			return "FALSE";
    		}
    	}	
	return "TRUE";
	}

	@GetMapping("/search")
	public HashMap<String,Object> postSearch(
			String category, String language, String state, 
			String titlename,@RequestParam int currentPage,@RequestParam int number,HttpServletRequest req) {
		int totalPage;
		//페이징을 위한 페이지 번호 관련 계산
		currentPage=number*(currentPage-1);
		//전체 게시글을 구하기 위한 코드
		int listCount = service.RecommandList(category, language, state, titlename, null, null).size();
		if (listCount % number != 0) {
	          totalPage = listCount / number + 1;
	        } else {
	          totalPage = listCount / number;
	        }
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("List", service.RecommandList(category, language, state, titlename,currentPage, number));
		map.put("pageCount", listCount );
		return map;
	}
	
	@GetMapping("/info")
	public Map<String, Object> postInfo(
		@RequestParam String postindex,
		HttpServletRequest req) throws Exception{
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		PostModel detail = new PostModel();
		List<FileModel> file = new ArrayList<FileModel>();
		List<CommentModel> comment = service.RecommandComments(postindex);
		detail = service.RecommandDetail(postindex);
		file = service.IndexOutput(postindex);
		map.put("postModel", detail);
		map.put("fileList",file);
		map.put("comment", comment);
		return map;
	}
	
	@PostMapping("/download")
	public ResponseEntity<Resource> fileDownload(
			@RequestParam String fileindex,
			HttpServletRequest req) throws Exception{
		// 파일 다운로드 api
		Resource resource = service.FileDownload(fileindex);
		String mediaType = Files.probeContentType(Paths.get(resource.getFile().getAbsolutePath()));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
				.contentType(MediaType.parseMediaType(mediaType))
				.body(resource);
	}
	
	@PostMapping("/update")
	public String postUpdate(
			@ModelAttribute PostModel post, 
			@RequestPart("files") List<MultipartFile> files,
			HttpServletRequest req) {
		try {
			service.RecommandUpdate(post);
		}
		catch(Exception e) {
			return "FALSE";
		}
			//postindex값을 토대로 폴더를 구분
			String folderName = post.getPostindex();
			// fileindex값 출력을 위해 postindex값을 넣어 리스트에 담음
			List<FileModel> fileList = service.IndexOutput(post.getPostindex());
			//파일 리스트의 경로에 있는 파일을 지우고 시작
			//기본 경로를 설정함
			String basePath =".\\\\upload";
			//기본 경로에 postindex값을 넣어 게시글 별로 경로 생성
			File folderPath = new File(basePath+"//"+folderName);	
			//파일 인덱스 추출을 위한 반복변수
			int num = 0;
			//다중 파일 업로드를 위한 for문
	    	for(MultipartFile file : files) {
	    		//파일 모델 객체 생성
	    		FileModel RepeatModel = new FileModel();
	    		//파일명 변수에 저장
	    		String originalName = file.getOriginalFilename();
	    		//만약 파일 이름이 같으면 수정안하고 넘어간다
	    		//다를 경우에는 해당 경로에 있는 기존 파일을 지움
	    		if(originalName.equals(fileList.get(num).getFilename())) {
	    			continue;
	    		}else {
	    			File deleteFile = new File(fileList.get(num).getFilepath());
	    			deleteFile.delete();
	    		}
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
						return "FALSE";
					}
					//파일모델에 이름,경로,고유식별명,게시글index를 넣고 insert함
					RepeatModel.setFileindex(fileList.get(num).getFileindex());
					RepeatModel.setFilename(originalName);
					RepeatModel.setFilepath(filePath);
					RepeatModel.setFileuuid(newName.toString());
					num++;	//리스트 반복을 위한 num 변수 값 증가
					try {
						service.FileUpdate(RepeatModel);
					}
					catch(Exception e) {
						e.printStackTrace();
						return "FALSE";
					}
				}
	    		else {
	    			return "FALSE";
	    		}
	    	}
			return "TRUE";
	}
	
	@PostMapping("/delete")
	public String postDelete(
			@RequestParam String postindex,
			HttpServletRequest req) {
		//추천장소 게시글이랑 리뷰 DB 정보 지울것
		try {
			service.CommentDelete(postindex);
			//해당 파일들의 정보를 지울 것(만약 파일이 있을 경우에)
			List<FileModel> fileList = service.IndexOutput(postindex);
			if(!fileList.isEmpty()) {
				for(int repeat=0; repeat<fileList.size(); repeat++) {
					File deleteFile = new File(fileList.get(repeat).getFilepath());
					service.FileDelete(fileList.get(repeat).getFileindex());
					deleteFile.delete();
				}
			}
			service.RecommandDelete(postindex);
		}
		catch(Exception e) {
			e.printStackTrace();
			return "FALSE";
		}
		// 파일 지울 것
		
		return "TRUE";
	}
	
	@GetMapping("/translate/info")
	public List<TranslateModel> TranslateInfo(
			@RequestParam String postindex,String language,
			HttpServletRequest req) {
		//번역 정보 불러오기
			return service.TranslateInfo(postindex,language);
	}
	
	@PostMapping("/translate/create")
	public String postTranslateCreate(
			@RequestBody TranslateModel translate,
			HttpServletRequest req) {
		// 번역 정보 등록하기
		try {
			service.TranslateCreate(translate);
			return "TRUE";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "FALSE";
		}
	}
	
	@PostMapping("/translate/update")
	public String postTranslateUpdate(
			@RequestBody TranslateModel translate,
			HttpServletRequest req) {
		//번역 내용 수정하기
		try{
			service.TranslateUpdate(translate);
			return "TRUE";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "FALSE";
		}
		
	}
	
	
	@PostMapping("/translate/delete")
	public String postTranslateDelete(
			@RequestBody TranslateModel translate,
			HttpServletRequest req) {
			// 번역 내용 삭제
			try{
				service.TranslateDelete(translate);
				return "TRUE";
			}
			catch(Exception e) {
				e.printStackTrace();
				return "FALSE";
			}
	}
	
	
	
	
	@PostMapping("/comment/delete")
	public String postCommentDelete(
			@RequestParam String commentindex,
			HttpServletRequest req) {
			// 해당 게시글 번호에 있는 댓글 삭제
			try{
				service.CommentOneDelete(commentindex);
				return "TRUE";
			}
			catch(Exception e) {
				e.printStackTrace();
				return "FALSE";
			}
	}
	
	@GetMapping("/place/enable")
	public String postPlaceEnable(
			@RequestParam String postindex,
			HttpServletRequest req) {
		// 받은 postindex에 따라 추천장소 서비스 활성화 시키기
			try {
				service.StateUpdate(postindex);
				return "TRUE";
			}
			catch(Exception e) {
				e.printStackTrace();
				return "FALSE";
			}
	}

	
}
