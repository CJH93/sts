package com.fullstack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fullstack.dto.BBSDto;

// Mapper 로 사용하기 위해 등록
@Mapper
public interface MyUserDao {

	public List<BBSDto> listDao(); // 게시판 리스트 get
	public BBSDto viewDao(int id); // 글 상세보기
	public int writeDao(String writer, String title, String content); // 글 작성
	public int deleteDao(String id); // 글 삭제
	
	
}
