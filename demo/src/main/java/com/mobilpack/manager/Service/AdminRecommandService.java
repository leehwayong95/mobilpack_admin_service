package com.mobilpack.manager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Model.FileModel;
import com.mobilpack.manager.Model.PostModel;

@Service
public class AdminRecommandService {
	@Autowired
	Dao dao;
	
	//게시글 등록 관련 서비스
	//게시글 관련 내용 DB에 insert 및 파일 경로 및 이름 insert
	public void RecommandCreate(PostModel post) {
		dao.RecommandCreate(post);
	}
	
	public void FileCreate(FileModel file) {
		dao.FileCreate(file);
	}
}
