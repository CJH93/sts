package com.fullstack.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fullstack.entity.Board;


public interface SearchBoardRepository{
	Board search1();
	
	
	Page<Object[]> searchpage(String type, String keyword, Pageable pageable);
}



