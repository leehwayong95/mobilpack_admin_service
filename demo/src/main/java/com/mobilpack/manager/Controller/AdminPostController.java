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
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	@ModelAttribute("FileModel") 
	public FileModel getFileModel() {
		return new FileModel(); }
	
	//게시글 등록 부분 api
	@PostMapping("/create")
	public String postCreate(@ModelAttribute PostModel post,
			MultipartHttpServletRequest mtfRequest,
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
		//mtfRequest를 이용해 파일 생성
		List<MultipartFile> files = mtfRequest.getFiles("files");
		//쿼리문을 통해 DB에 insert함
		try {
        service.RecommandCreate(post);
		} catch(Exception e) {
			e.printStackTrace();
			return "FALSE";
		}
      //받은 파일을 가지고 파일 생성함
		String result = service.FileWrite(files, post.getPostindex());
		if(result.equals("TRUE")) {
			return "TRUE";
		} else {
			return "FALSE";
		}
	}

	@GetMapping("/search")
	public HashMap<String,Object> postSearch(
			String category, String language, String state, 
			String titlename,@RequestParam int currentPage,@RequestParam int number,HttpServletRequest req) {
		int totalPage;
		//페이징을 위한 페이지 번호 관련 계산
		currentPage=number*(currentPage-1);
		//전체 게시글을 받아서 listCount에 값 저장
		int listCount = service.RecommandList(category, language, state, titlename, null, null).size();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("List", service.RecommandList(category, language, state, titlename,currentPage, number));
		map.put("pageCount", listCount );
		return map;
	}
	
	@GetMapping("/info")
	public Map<String, Object> postInfo(
		@RequestParam String postindex,
		HttpServletRequest req) throws Exception{
		//map 생성
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		//PostModel 생성 하고 추천장소 게시글 정보 담음
		PostModel detail = service.RecommandDetail(postindex);
		//FileModel List 생성하고 fileModel 리스트를 담음
		List<FileModel> file = service.IndexOutput(postindex);
		//CommentModel List에 추천장소 리뷰 List를 담음
		List<CommentModel> comment = service.RecommandComments(postindex);
		//해당 부분들을 맵에 담아서 리턴
		map.put("postModel", detail);
		map.put("fileList",file);
		map.put("comment", comment);
		return map;
	}
	
	@PostMapping("/download")
	public ResponseEntity<Resource> fileDownload(
			@RequestParam String fileindex,
			HttpServletRequest req) throws Exception{
		// 파일 경로를 통해 resource를 받음
		Resource resource = service.FileDownload(fileindex);
		// Files를 이용해 해당 resource를 mediaType으로 받아서 리턴
		String mediaType = Files.probeContentType(Paths.get(resource.getFile().getAbsolutePath()));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
				.contentType(MediaType.parseMediaType(mediaType))
				.body(resource);
	}
	
	@PostMapping("/update")
	public String postUpdate(
			@ModelAttribute PostModel post, 
			MultipartHttpServletRequest mtfRequest,
			@RequestParam List<String> deletelist,
			HttpServletRequest req) {
		//받은 post 값을 DB에 update함
		try {
			service.RecommandUpdate(post);
		}
		catch(Exception e) {
			return "FALSE";
		}
		//mtfRequest통해 파일 생성
		List<MultipartFile> files = mtfRequest.getFiles("files");
		//지울 파일 리스트에서 파일 인덱스를 받아서 해당 파일을 db에서 지운다
		if(!deletelist.get(0).equals("0")) {
			for(int repeat = 0; repeat<deletelist.size();repeat++) {
				service.FileDelete(deletelist.get(repeat));
			}
		}
		//받은 파일을 가지고 실제 파일 생성
		String result = service.FileWrite(files, post.getPostindex());
		if(result.equals("TRUE")) {
			return "TRUE";
		} else {
			return "FALSE";
		}
	}
	
	@PostMapping("/delete")
	public String postDelete(
			@RequestParam String postindex,
			HttpServletRequest req) {
		//추천장소 게시글이랑 리뷰 DB 정보 지울것
		try {
			// 해당 게시글의 리뷰 dB 삭제
			service.CommentDelete(postindex);
			//해당 파일들의 정보를 지울 것(만약 파일이 있을 경우에)
			// 해당 게시글에 해당하는 file들을 불러와서 실제 위치에 있는 파일을 삭제
			List<FileModel> fileList = service.IndexOutput(postindex);
			if(!fileList.isEmpty()) {
				for(int repeat=0; repeat<fileList.size(); repeat++) {
					File deleteFile = new File(fileList.get(repeat).getFilepath());
					service.FileDelete(fileList.get(repeat).getFileindex());
					deleteFile.delete();
				}
			}
			// 마지막으로 해당 postindex의 DB에 있는 정보들을 삭제
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
			// 번역 정보 등록 및 DB의 language 부분 수정
			service.TranslateCreate(translate);
			service.UpdateTranslateCreate(translate);
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
				// 번역 내용 삭제 및 language 부분 삭제
				service.TranslateDelete(translate);
				service.UpdateTranslateDelete(translate);
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
				//commetindex를 받아서 해당 리뷰 삭제
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
