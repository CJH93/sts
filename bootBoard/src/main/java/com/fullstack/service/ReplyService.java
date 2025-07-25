package com.fullstack.service;

import java.util.List;

import com.fullstack.dto.ReplyDTO;
import com.fullstack.entity.Board;
import com.fullstack.entity.Reply;

public interface ReplyService {

	// 댓글의 등록, 수정, 삭제, 댓글 목록을 리턴하도록 선언
	Long register(ReplyDTO replyDTO);
	
	List<ReplyDTO> getList(Long bno);
	
	void modify(ReplyDTO dto);
	
	void remove(Long bno);
	
	// 변환 메서드 정의
	default Reply dtoToEntity(ReplyDTO replyDTO)
	{
		// 연관된 객체에서 댓글의 정보 get
		Board board = Board.builder().bno(replyDTO.getBno()).build();
		
		Reply reply = Reply.builder()
				.rno(replyDTO.getRno())
				.text(replyDTO.getText())
				.replyer(replyDTO.getReplyer())
				.board(board)
				.build();
		
		return reply;
	}
	
	default ReplyDTO entityToDTO(Reply reply)
	{
		ReplyDTO dto = ReplyDTO.builder()
				.rno(reply.getRno())
				.text(reply.getText())
				.replyer(reply.getReplyer())
				.regDate(reply.getRegDate())
				.modDate(reply.getModDate())
				.build();
		
		return dto;
	}
	
}
