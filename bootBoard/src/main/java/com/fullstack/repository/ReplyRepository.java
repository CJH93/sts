package com.fullstack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fullstack.entity.Board;
import com.fullstack.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	
	// 게시글 삭제. 하위에 있는 댓글부터 삭제 후 작업 진행
	@Modifying // 삭제 쿼리 2번 사용 시 어노테이션
	@Query("Delete from Reply r where r.board.bno =:bno")
	void deleteByBno(@Param("bno") Long bno);
	
	// 특정 게시글에 해당하는 댓글의 목록을 리턴하는 서비스 선언
	List<Reply> getRepliesByBoardOrderByRno(Board board);
	
}
