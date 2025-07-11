package com.fullstack.DTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Getter;

// 리액트 페이지 관련 정보를 전달함
// 페이지 번호에 따른 글 목록, pageNation 정보, 이전, 다음 등의 정보, 전체 페이지 수 , 현재 페이지 번호 등
@Getter
public class PageResponseDTO<E> {

	private List<E> dtoList;
	private List<Integer> pageNumList;
	private PageRequestDTO pageRequestDTO;
	private boolean prev, next;
	private int totalCount, prevPage, nextPage, totalPage, current;
	
	@Builder(builderMethodName = "withAll")
	public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) 
	{
		 int end = (int)(Math.ceil(pageRequestDTO.getPage() / 10.0)) *  10;
		 int start = end - 9;
		 int last =  (int)(Math.ceil((totalCount/(double)pageRequestDTO.getSize())));
		 end =  end > last ? last : end;
		 this.prev = start > 1;
		 this.next = totalCount > end * pageRequestDTO.getSize();
		 
		 this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
		
		 if(prev) 
			 this.prevPage = start-1;
		 if(next)
		 	 this.nextPage = end+ 1;
		 	 this.totalPage = this.pageNumList.size();
		 	 this.current = pageRequestDTO.getPage();
		 
		 
	}
	
}









