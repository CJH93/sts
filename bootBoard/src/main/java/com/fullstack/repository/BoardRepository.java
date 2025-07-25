package com.fullstack.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fullstack.entity.Board;
import com.fullstack.repository.search.SearchBoardRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

	// 하나의 Row 내에 Object[] 로 리턴 됨
	/*
	 * 내부 엔티티를 이용해서 join 작성시 On 이라는 키워드가 없어도 됨
	 */
	@Query("Select b, w From Board b left join b.writer w where b.bno =:bno")
	Object getBoardWithWriter(@Param("bno") Long bno);
	
	/*
	 * 조인을 걸려고 하는 엔티티가 내부 엔티티가 아닌 경우엔 반드시 JOIN ON 을 사용해서 조인을 수행해야함
	 * 내부 엔티티의 연관관계가 없는 join 의 리턴 타입은 List<Object[]>
	 */
	@Query("Select b, r From Board b left join Reply r on r.board = b where b.bno =:bno")
	List<Object[]> getBoardWithReply(@Param("bno") Long bno);
	
	/*
	 * Native Query : 표준 ANSI 쿼리를 수행하도록 하는 것
	 * @query(value = "ANSI query") 형태로 설정 
	 * 부가 속성으로 그룹 쿼리 등을 속성 명을 이용해서 사용할 수 있음
	 * 리스트 페이지에서 사용한 게시글 전체 목록과 작성자 정보, 댓글 정보를 조회하는 쿼리
	 */
	
	@Query(value = "Select b, w, count(r) From Board b left Join b.writer w "
			+ "left join Reply r on r.board = b Group By b ", 
			countQuery = "Select count(b) From Board b")
	Page<Object[]> getBoardWithReplyCount(Pageable pageable);
	
	
	// 특정 글 번호의 상세 페이지에서 사용할 댓글수 표기 쿼리 작성
	@Query(value = "Select b, w, count(r) From Board b left join b.writer w left outer join Reply r on r.board = b where b.bno =:bno")
	Object getBoardByBno(@Param("bno") Long bno);
	
	
}





