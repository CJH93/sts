package com.fullstack.dao;

import java.util.List;

import com.fullstack.dto.BBSDto;

public interface BBSDaoInter {

	// list 를 get 하는 메서드 선언
	public List<BBSDto> listDao();
	
	// 상세내용 보기 메서드 선언
	public BBSDto viewDao(String id);
	
	// 글쓰기 메서드 선언
	public int writeDao(String writer, String title, String content);
		
}
