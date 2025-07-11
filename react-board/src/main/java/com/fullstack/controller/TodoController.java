package com.fullstack.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.DTO.PageRequestDTO;
import com.fullstack.DTO.PageResponseDTO;
import com.fullstack.DTO.TodoDTO;
import com.fullstack.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {

	private final TodoService todoService;
	
	// todo 목록 처리 메서드 구현
	@GetMapping("/list")
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO)
	{
		return todoService.list(pageRequestDTO);
	}
	
	// 상세 내용을 리턴하는 메서드
	@GetMapping("/{tno}")
	public TodoDTO get(@PathVariable(name = "tno") Long tno)
	{
		return todoService.get(tno);
	}
	
	// 등록 처리
	@PostMapping("/")
	public Map<String, Long> register(@RequestBody TodoDTO todoDTO)
	{
		Long tno = todoService.register(todoDTO);
		return Map.of("TNO", tno);
	}
	
	// 수정 처리. PUT
	@PutMapping("/{tno}")
	public Map<String, String> modify(@PathVariable(name = "tno") Long tno, @RequestBody TodoDTO todoDTO)
	{
		todoDTO.setTno(tno);
		todoService.modify(todoDTO);
		return Map.of("Result", "SUCCESS");
	}
	
	// 삭제 처리. DELETE
	@DeleteMapping("/{tno}")
	public Map<String, String> remove(@PathVariable(name = "tno") Long tno)
	{
		todoService.remove(tno);
		return Map.of("Result", "SUCCESS");
	}
	
	
	
	
	
}








