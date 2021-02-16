package com.mobilpack.manager.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.UserRecommandDao;
import com.mobilpack.manager.Model.PostModel;

@Service
public class UserRecommandService {
	@Autowired 
	UserRecommandDao dao;
	
	public PostModel getRecommandPost (String index) {
		return dao.getRecommandPost(index);
	}
	
	public List<PostModel> getRecommandList (String category, String language, String state, String titlename,Integer currentPage,Integer number){
		List<PostModel> recommandList = dao.getRecommandList(category, language, state, titlename, currentPage, number);
		//상대경로를 절대경로로 바꾸어줌
		for (PostModel i : recommandList) { 
			try {
				if(i.getThumbnail() != null) {
					File targetfile = new File(i.getThumbnail());
					if(targetfile.exists()) { //실제로 이 컴퓨터에 존재하는지 검사
						//이미지 파일을 byte화 코드
						//i.setImg(Files.readAllBytes(Path.of(targetfile.toURI())));
						i.setThumbnail("http://localhost/img/" + i.getPostindex() +"/"+targetfile.getName().toString());
					}
					else
						i.setThumbnail(null);
				}
			} catch (Exception e) {
				i.setThumbnail(null);
			}
		}
		return recommandList;
	}
}
