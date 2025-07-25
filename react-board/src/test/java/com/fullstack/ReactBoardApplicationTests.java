package com.fullstack;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fullstack.DTO.PageRequestDTO;
import com.fullstack.entity.Todo;
import com.fullstack.repository.TodoRepository;
import com.fullstack.service.TodoService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class ReactBoardApplicationTests {

	@Autowired
	private TodoRepository repository;
	
	@Autowired
	private TodoService todoService;
	
	@Test
	public void testList()
	{
		 log.info(todoService.list(PageRequestDTO.builder().page(2).size(10).build()));
	}
	
	// @Test
	public void testPaging()
	{
		Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
		Page<Todo> result = repository.findAll(pageable);
		log.info(result.getTotalElements());
		result.getContent().stream().forEach(todo -> log.info(todo));
		
	}
	
	// @Test
	void contextLoads() {
		for (int i = 1; i <= 100; i++)
		{
			Todo todo = Todo.builder()
					.title("Title..." + i)
					.dueDate(LocalDate.now())
					.writer("user00")
					.build();
			repository.save(todo);
			
		}
		
	}

}
