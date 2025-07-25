package com.fullstack.service;

import com.fullstack.dto.BoardDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.Board;
import com.fullstack.entity.Member;

public interface BoardService {

	// 게시글 수정
	void modify(BoardDTO boardDTO);
	
	// 신규 글 등록
	Long register(BoardDTO boardDTO);
	
	// 글 목록 get
	PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
	
	// 글 상세 get
	BoardDTO get(Long bno);
	
	// 글 삭제(댓글도 같이)
	void removeWithReply(Long bno);
	
	// 서비스 구현 객체가 사용할 DTO -> Entity 변환 메서드 정의
		default Board dtoToEntity(BoardDTO boardDTO)
		{
			Member member = Member.builder()
					.email(boardDTO.getWriterEmail())
					.build();
			
			Board board = Board.builder()
					.bno(boardDTO.getBno())
					.title(boardDTO.getTitle())
					.content(boardDTO.getContent())
					.writer(member)
					.build();
					
			return board;
		}
	
		default BoardDTO entityToDTO(Board board, Member member, Long replyCount)
		{
			BoardDTO boardDTO = BoardDTO.builder()
					.bno(board.getBno())
					.title(board.getTitle())
					.content(board.getContent())
					.regDate(board.getRegDate())
					.modDate(board.getModDate())
					.writerEmail(member.getEmail())
					.writerName(member.getName())
					.replyCount(replyCount.intValue())
					.build();
			
			return boardDTO;
		}
}
