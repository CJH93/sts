package com.fullstack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fullstack.dto.ReplyDTO;
import com.fullstack.entity.Board;
import com.fullstack.entity.Reply;
import com.fullstack.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

	private final ReplyRepository replyRepository;
	
	@Override
	public Long register(ReplyDTO replyDTO) 
	{
		Reply reply = dtoToEntity(replyDTO);
		
		replyRepository.save(reply);
		
		return reply.getRno();
	}

	@Override
	public List<ReplyDTO> getList(Long bno) 
	{
		List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
		
		return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
	}

	@Override
	public void modify(ReplyDTO dto) 
	{
		Reply reply = dtoToEntity(dto);
		replyRepository.save(reply);
	}

	@Override
	public void remove(Long bno) 
	{
		replyRepository.deleteById(bno);
	}

}
