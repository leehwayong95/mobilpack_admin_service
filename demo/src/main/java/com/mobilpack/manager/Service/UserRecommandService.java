package com.mobilpack.manager.Service;

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
}
