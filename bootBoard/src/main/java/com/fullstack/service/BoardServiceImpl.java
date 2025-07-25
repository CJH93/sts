package com.fullstack.service;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fullstack.dto.BoardDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.Board;
import com.fullstack.entity.Member;
import com.fullstack.repository.BoardRepository;
import com.fullstack.repository.ReplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final ReplyRepository replyRepository;
	
	private final BoardRepository boardRepository;

	@Override
	public Long register(BoardDTO boardDTO) {
		
		Board board = dtoToEntity(boardDTO);
		boardRepository.save(board);
		
		return board.getBno();
	}

	@Override
	public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) 
	{
		// mapTo(fn) 에 적용할 변환 함수 객체 생성
		Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member)en[1], (Long)en[2])); 
		
//		Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
		
		Page<Object[]> result = 
				boardRepository.searchpage(pageRequestDTO.getType(), 
						pageRequestDTO.getKeyword(), 
						pageRequestDTO.getPageable(Sort.by("bno").descending()));
	
	return new PageResultDTO<>(result, fn);
	}

	@Override
	public BoardDTO get(Long bno) 
	{
		Object result = boardRepository.getBoardByBno(bno);
		Object[] arr = (Object[]) result;
		
		return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
	}

	@Transactional // 쿼리 2개 사용, 서로 끊어지지 않게 연결
	@Override
	public void removeWithReply(Long bno) 
	{
		// 댓글 부터 삭제
		replyRepository.deleteByBno(bno);
		boardRepository.deleteById(bno);
		
	}

	@Transactional
	@Override
	public void modify(BoardDTO boardDTO) 
	{
		Board board = boardRepository.getReferenceById(boardDTO.getBno());
		
		board.changeTitle(boardDTO.getTitle());
		board.changeContent(boardDTO.getContent());
		
		boardRepository.save(board);
	}
	
	
	
	
}







