package com.fullstack.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/*
 * list 페이지에서 사용자가 요청한 페이지에 따라서
 * 해당 글목록을 담아 Viewer 에 전달하는 역할을 할 예정
 * 페이징 처리에 필요한 기본 정보를 저장하는 역할도 함
 */

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

	private int page; // 요청된 페이지
	private int size; // 페이지의 사이즈
	
	// 검색에 관련된 변수 선언
	private String type; // 검색 조건 필드
	private String keyword; // 검색 내용
	
	// 객체 생성시에 위 필드를 기본값으로 초기화
	public PageRequestDTO()
	{
		this.page = 1;
		this.size = 10;
	}
	
	// 요청된 페이지에 맞는 글 목록 정보를 가진 Pageable 객체 리턴 메서드 정의
	// 파라미터로는 글의 정렬 순서를 받음
	public Pageable getPageable(Sort sort)
	{
		return PageRequest.of(page-1, size, sort);
	}
	
	
	
	
}













