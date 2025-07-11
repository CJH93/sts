package com.fullstack.service;

import org.hibernate.internal.build.AllowSysOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fullstack.dto.GuestBookDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.GuestBook;

@SpringBootTest
public class GuestBookImplTest {

	@Autowired
	private GuestBookService guestBookService;
	
	// @Test
	public void regiTest()
	{
		GuestBookDTO guestBookDTO = GuestBookDTO.builder()
				.title("유닛 테스트용 제목")
				.content("유닛 테스트용 내용")
				.writer("유닛 테스트용 작성자")
				.build();
		
		System.out.println(guestBookService.register(guestBookDTO));
	}
	
	// list 테스트
	@Test
	public void listTest()
	{
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1)
				.size(10)
				.type("tc")
				.keyword("a")
				.build();
		
		PageResultDTO<GuestBookDTO, GuestBook> pageResultDTO = guestBookService.getList(pageRequestDTO);
		for(GuestBookDTO guestBookDTO : pageResultDTO.getDtoList())
		{
			System.out.println(guestBookDTO);
		}
		
		System.out.println("이전(PREV) : " + pageResultDTO.isPrev());
		System.out.println("NEXT : " + pageResultDTO.isNext());
		System.out.println("TOTAL : " + pageResultDTO.getTotalPage());
		
		System.out.println("---------------------------------------------------------------------------------------");
		
		for(GuestBookDTO guestBookDTO : pageResultDTO.getDtoList())
		{
			System.out.println(guestBookDTO);
		}
		
		System.out.println("---------------------------------------------------------------------------------------");
		pageResultDTO.getPageList().forEach(i -> System.out.println(i));
		
	}

	
}











