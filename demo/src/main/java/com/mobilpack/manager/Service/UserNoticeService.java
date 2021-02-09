package com.mobilpack.manager.Service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Model.NoticeModel;

@Service
public class UserNoticeService {
	@Autowired
	private Dao dao;
	
	//(유저 공지사항 검색)
    public List<NoticeModel> usersearchnotice(String Currentpage,String Number,String title) {
    	return dao.usersearchnotice(Currentpage,Number,title);
    }
    //(유저 공지사항 상세)
    public NoticeModel userdetailnotice(String postindex) {
    	return dao.userdetailnotice(postindex);
    }
    //(유저 조회수 증가)
    public void userplusviewcount(String postindex) {
    dao.userplusviewcount(postindex);
    }
}
