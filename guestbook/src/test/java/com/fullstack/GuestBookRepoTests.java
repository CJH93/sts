package com.fullstack;

import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fullstack.entity.GuestBook;
import com.fullstack.entity.QGuestBook;
import com.fullstack.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest
public class GuestBookRepoTests {

	@Autowired
	private GuestBookRepository guestBookRepository;
	
	@Test
	public void insertDumies()
	{
		IntStream.rangeClosed(1, 100).forEach(i -> {
			GuestBook guestBook = GuestBook.builder().title("방명록 제목" + i).content("방명록 내용" + i).writer("user" + i).build();
			System.out.println(guestBookRepository.save(guestBook));
		});
	}

	// @Test
	public void update()
	{
		// DB 상에 존재하는 번호를 select 후 작성자/내용 업데이트
		Optional<GuestBook> res = guestBookRepository.findById(100L);
		if(res.isPresent())
		{
			GuestBook guestBook = res.get();
			
			guestBook.changeTitle("수정된 제목");
			guestBook.changeContent("수정된 내용");
			
			guestBookRepository.save(guestBook);
			
		}
	}
	
	// QueryDSL 을 이용한 데이터 조회 테스트
	// 다중의 조건이 필요할때, 이전에 배웠던 쿼리 보다 쉽게 객체지향형으로 사용할 수 있음
	/*
	 * 사용법 : BooleanBuilder 라는 객체 생성
	 * 조건에 맞는 구문은 Querydsl 에 있는 Predicate 타입의 함수(람다)를 사용해서 검출
	 * 작성된 BooleanBuider 에 Predicate 를 추가하고 수행시킴 
	 */
	
	// @Test
	public void testQueryDsl()
	{
		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
		
		QGuestBook qGuestBook = QGuestBook.guestBook;
		
		String keyWord = "1";
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		// 제목에서 찾기
		BooleanExpression booleanExpression1 = qGuestBook.title.contains(keyWord);
		// 내용에서 찾기
		BooleanExpression booleanExpression2 = qGuestBook.content.contains(keyWord);
		
		BooleanExpression exAll = booleanExpression1.or(booleanExpression2);
		
		booleanBuilder.and(exAll);
		booleanBuilder.and(qGuestBook.gno.gt(20));
		
		// builder 내에 있는 검색 기능을 수행하고, 이를 Page 처리한 결과를 Page 객체에 담도록
		// repository 에서 메서드를 호출해서 수행시킴
		Page<GuestBook> pageResult = guestBookRepository.findAll(booleanBuilder, pageable);
		
		pageResult.stream().forEach(guestbook -> {
			System.out.println(guestbook);
		});
		
	}
	
	
	
	
	
}











