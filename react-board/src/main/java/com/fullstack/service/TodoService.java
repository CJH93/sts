package com.fullstack.service;

import com.fullstack.DTO.PageRequestDTO;
import com.fullstack.DTO.PageResponseDTO;
import com.fullstack.DTO.TodoDTO;

public interface TodoService {

	Long register(TodoDTO todoDTO);
	
	TodoDTO get(Long tno);
	
	void modify(TodoDTO todoDTO);
	
	void remove(Long tno);
	
	// 목록 페이지(list) 에서 필요한 데이터를 수행하는 메서드
	PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
	
	
}
