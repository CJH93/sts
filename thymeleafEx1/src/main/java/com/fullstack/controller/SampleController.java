package com.fullstack.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fullstack.dtos.SampleDTO;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample")
@Log4j2 // 모든 요청에 대한 처리 결과의 log 를 남기도록 설정. 콘솔에 로그가 찍힘
public class SampleController {

	@GetMapping("/ex1")
	public void ex1()
	{
		log.info("ex1 요청에 응답함.");
	}
	
	@GetMapping(value = {"/ex2", "/exLink"})
	public void ex2(Model model)
	{
		List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(value -> {
			SampleDTO dto = SampleDTO.builder().sno(value).first("first : " + value).last("last : " + value).regTime(LocalDateTime.now()).build();
			return dto;
		}).collect(Collectors.toList());
		
		model.addAttribute("list", list);
	}
	
	@GetMapping("/exInline")
	private String getMethodName(RedirectAttributes redirectAttributes) // 리다이렉트시 파라미터를 넘겨주도록 객체 추가 
	{ 
		SampleDTO dto = SampleDTO.builder().sno(100L).first("first : 100").last("last : 100").regTime(LocalDateTime.now()).build();
		redirectAttributes.addFlashAttribute("result", "success");
		redirectAttributes.addFlashAttribute("dto", dto);
		
		
		return "redirect:/sample/ex3";
	}
	
	
	@GetMapping("/ex3")
	public void ex3()
	{
		log.info("ex3 요청됨");
	}
	
	
}














