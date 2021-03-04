package com.mobilpack.manager.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilpack.manager.Dao.Dao;
import com.mobilpack.manager.Model.NoticeModel;

@Service
public class NoticeManagerService {
	@Autowired
	private Dao dao;
	
	//(공지사항 검색)
    public List<NoticeModel> searchnotice(String Currentpage,String Number,String language,String titlename) {
    	return dao.searchnotice(Currentpage,Number,language,titlename);
    }
    //(공지사항 상세)
    public NoticeModel detailnotice(String postindex) {
    	return dao.detailnotice(postindex);
    }
    //(공지사항 조회수 증가)
    public void plusviewcount(String postindex) {
    	dao.plusviewcount(postindex);
    }
    //(공지사항 게시 토글)
    public void stopposting(String postindex) {
    	dao.stopposting(postindex);
    }
    //(공지사항 작성)
    public void insertnotice(NoticeModel notice) {
    	dao.insertnotice(notice);
    }
    //(공지사항 수정)
    public void editnotice(String postindex,String id,String language,String title,String content,String topsetting) {
    	dao.editnotice(postindex,id,language,title,content,topsetting);
    }
    //(공지사항 삭제)
    public void deletenotice(String postindex) {
        dao.deletenotice(postindex);
    }
}
