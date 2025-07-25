package com.fullstack.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fullstack.DTO.PageRequestDTO;
import com.fullstack.DTO.PageResponseDTO;
import com.fullstack.DTO.TodoDTO;
import com.fullstack.entity.Todo;
import com.fullstack.repository.TodoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

	private final TodoRepository todoRepository;
	
	private final ModelMapper modelMapper;
	
	@Override
	public Long register(TodoDTO todoDTO)
	{
		log.info("Todo 등록 수행");
		
		Todo todo = modelMapper.map(todoDTO, Todo.class);
		
		return todoRepository.save(todo).getTno();
		
	}

	@Override
	public TodoDTO get(Long tno) {
		
		Optional<Todo> result = todoRepository.findById(tno);
		Todo todo = result.orElseThrow(); // 없으면 예외 발생시키는 메서드
		// 맵퍼를 이용한 Entity -> DTO 변환
		TodoDTO todoDTO = modelMapper.map(todo, TodoDTO.class);		
		
		return todoDTO;
		
	}

	@Override
	public void modify(TodoDTO todoDTO) {

		Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
		
		Todo todo = result.orElseThrow();
		
		todo.changeTitle(todoDTO.getTitle());
		todo.changeWriter(todoDTO.getWriter());
		todo.changeDueDate(todoDTO.getDueDate());
		todo.changeComplete(todoDTO.isComplete());
		
		todoRepository.save(todo);
		
	}

	@Override
	public void remove(Long tno) {

		todoRepository.deleteById(tno);
		
	}

	@Override
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
		
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("tno").descending());
		
		Page<Todo> result = todoRepository.findAll(pageable);
		
		List<TodoDTO> dtoList = result.getContent().stream().map(todo -> modelMapper.map(todo, TodoDTO.class)).collect(Collectors.toList());
		
		long totalcount = result.getTotalElements();
		
		return PageResponseDTO.<TodoDTO>withAll()
		.dtoList(dtoList)
		.pageRequestDTO(pageRequestDTO)
		.totalCount(totalcount)
		.build();
		 
	}
	
}








