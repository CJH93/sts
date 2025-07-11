package com.fullstack.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fullstack.dto.GuestBookDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.GuestBook;
import com.fullstack.entity.QGuestBook;
import com.fullstack.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor // 필드에 final, not null 로 선언된 객체를 초기에 주입시키는 어노테이션.
// 반드시 final 로 선언된 것이 있어야 함.
public class GuestBookServiceImpl implements GuestBookService {

	private final GuestBookRepository guestBookRepository;
	
	@Override
	public Long register(GuestBookDTO guestBookDTO) {

		GuestBook guestBook = dtoToEntity(guestBookDTO);
		
		// System.out.println(guestBook);
		guestBookRepository.save(guestBook);
		
		return guestBook.getGno();
		
	}

	@Override
	public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO pageRequestDTO) {

		Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());
		
		BooleanBuilder builder = getSearch(pageRequestDTO);
		
		Page<GuestBook> page = guestBookRepository.findAll(builder, pageable);
		
		Function<GuestBook, GuestBookDTO> fn = entity -> entityToDto(entity); 
		return new PageResultDTO<>(page, fn);
	}

	// 검색 기능 추가
	private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO)
	{
		String type = pageRequestDTO.getType();
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		QGuestBook qGuestBook = QGuestBook.guestBook;
		
		String keyword = pageRequestDTO.getKeyword();
		
		BooleanExpression booleanExpression = qGuestBook.gno.gt(0L);
		booleanBuilder.and(booleanExpression);
		
		// 검색 조건이 없다면, 신규 글 위주로 넘겨줌
		if(type == null || type.trim().length() == 0)
		{
			return booleanBuilder;
		}
		
		BooleanBuilder condition = new BooleanBuilder();
		
		if(type.contains("t"))
		{
			condition.or(qGuestBook.title.contains(keyword));
		}
		
		if(type.contains("c"))
		{
			condition.or(qGuestBook.content.contains(keyword));
		}
		
		if(type.contains("w"))
		{
			condition.or(qGuestBook.writer.contains(keyword));
		}
		
		// 모든 조건 합치기
		booleanBuilder.and(condition);
		
		return booleanBuilder;
		
	}
	
	
	
	@Override
	public GuestBookDTO read(Long gno) {
		Optional<GuestBook> result = guestBookRepository.findById(gno);
		return result.isPresent() ? entityToDto(result.get()) : null;
	}

	@Override
	public void remove(Long gno) {
		guestBookRepository.deleteById(gno);
	}

	@Override
	public void modify(GuestBookDTO guestBookDTO) {
		Optional<GuestBook> result = guestBookRepository.findById(guestBookDTO.getGno());
		
		if(result.isPresent())
		{
			GuestBook entity = result.get();
			
			// 엔티티 내용 변경
			entity.changeTitle(guestBookDTO.getTitle());
			entity.changeContent(guestBookDTO.getContent());
			
			guestBookRepository.save(entity);
			
		}
		
	}
	

}









