package com.fullstack.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.dto.ReplyDTO;
import com.fullstack.service.ReplyService;

import lombok.RequiredArgsConstructor;

// RestFul 방식의 컨트롤러 선언

@RestController
@RequestMapping("/replies/")
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;
	
	// 댓글 요청시 서버 URI 는 게시글 번호를 파라미터로 받는 pathvariable 을 사용
	@GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReplyDTO>> getListByBno(@PathVariable("bno") Long bno)
	{
		return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
	}
	
	// 신규 댓글 등록
	@PostMapping("")
	public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO)
	{
		Long rno = replyService.register(replyDTO);
		
		return new ResponseEntity<Long>(rno, HttpStatus.OK);
	}
	
	// 댓글 삭제
	@DeleteMapping("/{rno}")
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno)
	{
		replyService.remove(rno);
		
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
}












