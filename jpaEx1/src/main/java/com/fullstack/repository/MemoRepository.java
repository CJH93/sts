package com.fullstack.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fullstack.entity.Memo;

import jakarta.transaction.Transactional;

/*
 * JPA repository : Entity 를 대상으로 CRUD 를 수행하는 API
 * 일반적으로 이 클래스를 상속 받은 인터페이스는 위 기능을 그대로 상속받게 됨
 * 상속 받을때는 반드시 관리할 entity 와 pk 타입을 지정해야 한다.
 */

public interface MemoRepository extends JpaRepository<Memo, Long>{

	// 스프링은 이 객체를 내장 빈으로 자동 등록함.
	// SIDU 작업은 다음과 같은 메서드로 수행 가능
	// insert : save(엔티티 객체)
	// select : findById(엔티티 key) or get()
	// update : save(엔티티 객체) : 이 메서드를 호출시 기본 엔티티와 변경된 내용이 있다면 update 진행
	// delete : deleteById(key) or delete(엔티티) : 
	
	// 쿼리메서드 : 메서드 자체를 쿼리 형태로 정의해서 사용하는 것
	// 대부분은 select 위주의 쿼리를 사용, 조건을 줄때 유용함
	// fine 으로 시작, Repository 에 정의하고 이를 서비스 등에서 사용
	// 중요! 이 쿼리의 장점은 리턴 타입이 배열 또는 List 로 직접 선언 할 수가 있음
	// 만약 paging 을 하기 위해 Pageable 을 추가하는 경우엔 무조건 Page<T> 로 리턴됨.
	
	// mno 를 기준으로 특정 범위에 속한 글들만 추출해서 desc 로 정렬
	List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
	
	// 특정 mno 를 기준으로 특정 범위의 글들만 가져오는 쿼리 작성
	Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
	
	void deleteMemoByMnoLessThan(Long mno);
	
	// Query 어노테이션. 일반 쿼리 구문을 직접 적용하는 어노테이션
	// 주의! 반드시 테이블 alias 를 사용해야함
	@Query("SELECT m FROM Memo m ORDER BY m.mno DESC")
    List<Memo> getListDesc();
	
	// 파라미터 처리 : ?1, ?2 등으로 파라미터를 동적으로 매핑
	// :파라미터를 매핑하는 방식도 있음
	// #을 사용해서 객체를 매핑하는 방식도 있음
	// 특정 mno 에 해당하는 memoText 를 update 하도록 정의
	// 일반적인 업데이트를 쿼리로 만들어 보세요.
	@Transactional
	@Modifying
	@Query("update Memo m set m.memoText = :memoText where m.mno = :mno") 
	int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);
	
	
}























