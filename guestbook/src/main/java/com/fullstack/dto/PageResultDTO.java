package com.fullstack.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

/*
 * 페이지 결과 처리 담당
 * Page<Entity> 로 리턴된 결과를 DTO 로 변환 해야하며, 화면에 필요한 페이지 index 번호 정보를 구성하는 역할을 함
 */

@Data
public class PageResultDTO<DTO, EN> {

	// 리스트 페이지에서는 전체 페이지 1 ~ 10 까지 나와야 함
	// 하나의 페이지에는 10개의 글 목록이 있어야 함
	
	// 글 목록 list 선언
	private List<DTO> dtoList;
	
	// 페이징 처리를 위한 변수 선언
	
	// 총 페이지 번호
	private int totalPage;
	
	// 현재 페이지 번호
	private int page;
	private int size; // 페이지의 글 목록 갯수
	
	// 시작 페이지 번호, 끝 페이지 번호
	private int start, end;
	
	// 이전, 다음
	private boolean prev, next;
	
	// 페이지 번호 목록
	private List<Integer> pageList;
	
	// 생성자를 통해 Page<Entity> 객체를 파람으로 받고, 이를 변환하는 함수(function) 를 받아서
	// Entity 를 DTO 로 변환하도록 함
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn)
	{
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		
		// 전체 페이지수 초기화
		totalPage = result.getTotalPages();
		
		// 페이지 처리
		makePageList(result.getPageable());
	}
	private void makePageList(Pageable pageable)
	{
		this.page = pageable.getPageNumber() + 1; // 0페이지부터 시작. + 1 해줌
		this.size = pageable.getPageSize();
		
		// 유령 페이지 생성을 막기 위한 실제 끝 페이지 설정
		int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
		
		start = tempEnd - 9;
		prev = start > 1;
		end = totalPage > tempEnd ? tempEnd : totalPage;
		
		next = totalPage > tempEnd;
		
		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
	
}













