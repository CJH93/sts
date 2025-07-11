package com.fullstack.service;

import org.springframework.stereotype.Service;

import com.fullstack.dto.GuestBookDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.GuestBook;

/*
 * 인터페이스에서 구현한 것은 Service 객체가 해야할 일의 목록을 가지고 있는 역할을 함
 * 실제 수행할 객체는 컨트롤러에서 주입 받아 처리
 * 다형성 개념 필수
 */

public interface GuestBookService {
	
	// 서비스 구현 객체가 사용할 DTO -> Entity 변환 메서드 정의
	default GuestBook dtoToEntity(GuestBookDTO guestBookDTO)
	{
		GuestBook entity = GuestBook.builder()
				.gno(guestBookDTO.getGno())
				.title(guestBookDTO.getTitle())
				.content(guestBookDTO.getContent())
				.writer(guestBookDTO.getWriter())
				.build();
		return entity;
	}
	
	default GuestBookDTO entityToDto(GuestBook guestBook)
	{
		GuestBookDTO dto = GuestBookDTO.builder()
				.gno(guestBook.getGno())
				.title(guestBook.getTitle())
				.content(guestBook.getContent())
				.writer(guestBook.getWriter())
				.regDate(guestBook.getRegDate())
				.build();
		return dto;
	}

	// 신규 글 등록 목록
	Long register(GuestBookDTO guestBookDTO);
	
	// list 에 뿌려질 글 목록 및 페이징 정보를 담고 있는 PageResultDTO 를 리턴하도록 명세 등록
	PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO pageRequestDTO);
	
	GuestBookDTO read(Long gno);
	
	void remove(Long gno);
	
	void modify(GuestBookDTO guestBookDTO);
	
}








