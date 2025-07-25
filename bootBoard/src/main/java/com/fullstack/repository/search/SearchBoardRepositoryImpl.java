package com.fullstack.repository.search;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.fullstack.entity.Board;
import com.fullstack.entity.QBoard;
import com.fullstack.entity.QMember;
import com.fullstack.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;

import jakarta.transaction.Transactional;




public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

	public SearchBoardRepositoryImpl() {
		super(Board.class);
	}

	//검색어와 페이지별 요청에 따른 페이징처리까지 처리하는 메서드 정의
	
	
	
	@Override
	public Board search1() {
		
//		System.out.println("Search1 메서드 실행됨..");
//		return null;
		
		QBoard board = QBoard.board;
		QReply reply = QReply.reply;
	
		QMember member = QMember.member;
	
		
		JPQLQuery<Board> jpqlQuery =  from(board);
		jpqlQuery.leftJoin(member).on(board.writer.eq(member));
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
		
		//그룹쿼리를 이용하는 예시..
		
		JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
		tuple.groupBy(board);
		
		List<Tuple> result =  tuple.fetch();
		
		
		
		// System.out.println(result);
		
		//결과셋을 Tuple 이라는 객체를 이용해서 처리합니다.
		
		
		
		return null;
		
//		JPQLQuery<Board> jpqlQuery =  from(board);
//		
//		jpqlQuery.select(board).where(board.bno.gt((1L)));
//		
//		System.out.println(jpqlQuery);
//		
//		List<Board> result = jpqlQuery.fetch();
//		
		
	}

	@Transactional
	@Override
	public Page<Object[]> searchpage(String type, String keyword, Pageable pageable) {
		
		//System.out.println("searchPage...... 수행...");
		QBoard board = QBoard.board;
		QReply reply = QReply.reply;
		QMember member = QMember.member;
		

		JPQLQuery<Board> jpqlQuery =  from(board);
		jpqlQuery.leftJoin(member).on(board.writer.eq(member));
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
		
		JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression booleanExpression = board.bno.gt(0L);
		
		booleanBuilder.and(booleanExpression);
		
		//검색어 필드 null 여부 검색
		if(type != null) {
			String typeArr[] = type.split("");
			BooleanBuilder condition = new BooleanBuilder();
			
			for(String t : typeArr) {
				switch (t) {
				case "t":
					condition.or(board.title.contains(keyword));
					break;
				case "w":
					condition.or(member.email.contains(keyword));
					break;
				case "c":
					condition.or(board.content.contains(keyword));
					break;
				}
			}
			booleanBuilder.and(condition);
		}	
		
		tuple.where(booleanBuilder);
		
		
//		Sort sort = pageable.getSort();
//		
//		tuple.orderBy(board.bno.desc());
//		
//		sort.stream().forEach(order -> {
//			Order diretion = order.isAscending()?Order.ASC:Order.DESC;
//			String prop = order.getProperty();
//			
//			PathBuilder orderByExp = new PathBuilder<>(Board.class, "board");
//			tuple.orderBy(new OrderSpecifier(diretion, orderByExp.get(prop)));
//		});
//		
//		//페이지 처리작업
//		tuple.offset(pageable.getOffset());
//		tuple.limit(pageable.getPageSize());
		
		tuple.groupBy(board);
		
		Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, tuple);
		
		List<Tuple> result = tuple.fetch();
		
		long count = tuple.fetchCount();
		
		// System.out.println(count);
		
		
		return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), 
				pageable, 
				count);
	}

}
