package com.fullstack.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.fullstack.entity.Memo;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MemoRepoTests {

	@Autowired // 빈으로 등록된 Repo 를 get.
	MemoRepository memoRepository;
	
	// 메서드 단위 별로 테스트를 진행 할 수 있음.
	// 테스트를 수행할 메서드엔 @Test 라고 선언을 해줘야함.
	// @Test
	public void testClass()
	{
//		System.out.println(memoRepository.getClass().getName()); // 등록된 Repo 의 구현된 빈객체의 이름 출력
		
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Memo memo = Memo.builder().memoText("이건 샘플데이터" + i).build();
			memoRepository.save(memo);
		});
	}
	
	// select 테스트
	// @Test
	public void testSelec()
	{
		Long mno = 100L; // DB에 존재하는 mno
		
		Optional<Memo> optional = memoRepository.findById(mno); 
		
		System.out.println("===========================");
		
		// 데이터가 존재 하는지 체크
		if(optional.isPresent())
		{
			Memo memo = optional.get();
			System.out.println(memo);
		}
		
	}
	
	// update. save(entity)
	// @Test
	public void testUpdate()
	{
		Memo memo = Memo.builder().mno(100L).memoText("update 내용").build();
		
		System.out.println(memoRepository.save(memo));
	}
	
	// delete. deleteById() 이용
	// @Test
	public void delMemo()
	{
		memoRepository.deleteById(100L);
	}
	
	
	// 페이징 처리를 위한 테스트
	// @Test
	public void testPageDefault()
	{
		Sort sort = Sort.by("mno").descending();
		Sort sort2 = Sort.by("memoText").ascending();
		Sort sortAll = sort.and(sort2);
		
		
		Pageable pageable = PageRequest.of(0,  10, sortAll);
		Page<Memo> page = memoRepository.findAll(pageable); 
		System.out.println(page);
		
		System.out.println("============================================");
		
		System.out.println(page.getTotalPages());
		System.out.println("전체 글의 갯수 : " + page.getTotalElements());
		System.out.println("현재 페이지 번호 : " + page.getNumber());
		System.out.println("페이지당 데이터 갯수 : " + page.getSize());
		System.out.println("다음 페이지의 존재 여부 : " + page.hasNext());
		System.out.println("현재 페이지가 첫 페이지인지 여부 : " + page.isFirst());

		System.out.println("이 페이지에 속한 데이터 ============================================");
		for(Memo memo : page.getContent())
		{
			System.out.println(memo);
		}
		
	}
	
	// 쿼리 메서드를 사용하는 테스트
	// @Test
	public void testQueryMethod()
	{
		List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
		for(Memo m : list)
		{
			System.out.println(m);
		}
		
		
		System.out.println("============================================");
		Pageable pageable = PageRequest.of(0, 100, Sort.by("mno").descending());
		Page<Memo> res = memoRepository.findByMnoBetween(10L, 60L, pageable);
		
		res.get().forEach(memo -> System.out.println(memo));
	}
	
	//@Test
	//@Transactional // 한 번 이상의 쿼리가 수행될때 기존의 connection 을 유지해서 수행하라는 어노테이션
	//@Commit // 기본적으로 JPA 의 DDL 은 자동 롤백 상태
	// 반드시 commit 처리를 해줘야함.
    public void testQureyDel()
    {
       memoRepository.deleteMemoByMnoLessThan(10L);
    }
	
	// @Test
	public void testQueryList()
	{
		List<Memo> list = memoRepository.getListDesc();
		for(Memo m : list)
		{
			System.out.println(m);
		}
	}
	
	@Test
	public void testUpdate2()
	{
		int res = memoRepository.updateMemoText(95L, "95였지롱");
	}
	
	
}
















