package com.mobilpack.manager.Service;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Model.CommentModel;
import com.mobilpack.manager.Model.FileModel;
import com.mobilpack.manager.Model.PostModel;
import com.mobilpack.manager.Model.TranslateModel;

@Service
public class AdminRecommandService {
	@Autowired
	Dao dao;
	
	//게시글 등록 관련 서비스
	//게시글 관련 내용 DB에 insert 및 파일 경로 및 이름 insert
	public void RecommandCreate(PostModel post) {
		dao.RecommandCreate(post);
	}
	//파일 경로, 이름 등의 정보를 db에 insert
	public void FileCreate(FileModel file) {
		dao.FileCreate(file);
	}
	//게시글 리스트 내용을 조건에 따라 select문으로 가져옴
	public List<PostModel> RecommandList(String category, String language, String state, String titlename,Integer currentPage, Integer number){
		return dao.RecommandList(category, language, state, titlename,currentPage,number);
	}
	//게시글 상세 정보 내용 가져오기
	public PostModel RecommandDetail(String postindex) {
		return dao.RecommandDetail(postindex);
	}
	//파일 다운
	public Resource FileDownload(String fileindex) throws MalformedURLException {
		FileModel path =  dao.FileDownload(fileindex);
		String targetPath = path.getFilepath();
    	File target = new File(targetPath);
    	return new UrlResource(target.toURI());
	}
	//인덱스 불러오기
	public List<FileModel> IndexOutput(String postindex){
		return dao.IndexOutput(postindex);
	}
	//게시글 수정하기
	public String RecommandUpdate(PostModel post) {
		try { 
			dao.RecommandUpdate(post);
			return "SUCCESS";
			}
		catch(Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
	}
	//파일 수정하기
	public void FileUpdate(FileModel file) {
			dao.FileUpdate(file);
	}
	//게시글 리뷰 받기
	public List<CommentModel> RecommandComments(String postindex){
		return dao.RecommandComments(postindex);
	}
	//게시글 삭제
	public void RecommandDelete(String postindex) {
		dao.RecommandDelete(postindex);
	}
	//게시글 리뷰 삭제
	public void CommentDelete(String postindex) {
		dao.CommentDelete(postindex);
	}
	//파일 삭제
	public void FileDelete(String fileindex) {
		dao.FileDelete(fileindex);
	}
	//번역 등록
	public void TranslateCreate(TranslateModel translate) {
		dao.TranslateCreate(translate);
	}
	//번역 등록 갱신
	public void UpdateTranslateCreate(TranslateModel translate) {
		dao.UpdateTranslateCreate(translate);
	}
	//번역 상세 정보
	public List<TranslateModel> TranslateInfo(String postindex,String language){
		return dao.TranslateInfo(postindex,language);
	}
	//게시글 리뷰 하나 삭제
	public void CommentOneDelete(String commentindex) {
		dao.CommentOneDelete(commentindex);
	}
	//추천장소 서비스 활성화
	public void StateUpdate(String postindex) {
		dao.StateUpdate(postindex);
	}
	//번역 정보 수정
	public void TranslateUpdate(TranslateModel translate) {
		dao.TranslateUpdate(translate);
	}
	//번역 정보 삭제
	public void TranslateDelete(TranslateModel translate) {
		dao.TranslateDelete(translate);
	}
	//번역 정보 삭제 갱신
	public void UpdateTranslateDelete(TranslateModel translate) {
		dao.UpdateTranslateDelete(translate);
	}
	//객체 불러오기
    public void ObjectGet(Object obj) {
        // 가져오고자하는 객체를 선언합니다. ( 새로 생성할 경우 value는 존재하지 않습니다. 
        try {
            // 반복문을 이용하여 해당 클래스에 정의된 필드를 가져옵니다.
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
            Object value = field.get(obj); // 필드에 해당하는 값을 가져옵니다.
            System.out.println("field : "+field.getName()+" | value : "+ value);
            }
       }catch (Exception e) {
            e.printStackTrace();
      }
    }
    //파일 실제 생성
    public String FileWrite(List<MultipartFile> files, String postindex) {
    	//게시글 index를 토대로 폴더를 구분함
    	String folderName = postindex;
    	//기본 경로를 설정함
    	String basePath = ".\\\\upload";
    	//경로 생성
    	File folderPath = new File(basePath+"//"+folderName);
    	//해당 경로가 없을 경우 자동으로 구분 폴더 생성
    	if(!folderPath.exists()) {
    		folderPath.mkdir();
    	}
    	//다음 파ㄹ일 업로드를 위한 for문
    	for(MultipartFile file : files) {
    		//파일 모델 객체 생성
    		FileModel RepeatModel = new FileModel();
    		//파일명 변수에 저장
    		String originalName = file.getOriginalFilename();
    		//파일 확장자 검사를 위해 split으로 확장자 구분
    		//String안에 그냥 .을 쓰면 구분 못함 \\.을 써야 구분함 .이 정규식이기 때문
    		String[] nameCheck = originalName.split("\\.");
    		//고유식별자 생성
    		UUID newName = UUID.randomUUID();
    		//고유식별자를 이용한 파일 생성
    		String filePath = basePath+"\\\\"+folderName+"\\\\"+newName+"."+nameCheck[1];
    		//png와 jpg 확장자만 받기 위해 예외처리 진행
    		if(nameCheck[1].equals("png")||nameCheck[1].equals("jpg")) {
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
				RepeatModel.setPostindex(postindex);
				RepeatModel.setFilename(originalName);
				RepeatModel.setFilepath(filePath);
				RepeatModel.setFileuuid(newName.toString());
				try {
					FileCreate(RepeatModel);
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
}
