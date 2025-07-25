package com.fullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fullstack.dto.BoardDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model)
	{
		// getList 를 통해서 목록 get
		model.addAttribute("result", boardService.getList(pageRequestDTO)); 
	}
	
	@GetMapping("/register")
	public void register()
	{
		
	}
	
	@PostMapping("/register")
	public String registerProc(BoardDTO boardDTO, RedirectAttributes redirectAttributes)
	{
		Long bno = boardService.register(boardDTO);
		// DB 에 입력된 글 번호를 list 에 파라미터로 넘김
		// DB 에 글 입력하기
		redirectAttributes.addFlashAttribute("msg", bno);
		return "redirect:/board/list";
	}
	
	@GetMapping({"/read", "/modify"})
	public void read(@RequestParam("bno") Long bno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model)
	{
		BoardDTO dto = boardService.get(bno);
		model.addAttribute("dto", dto);
	}
	
	// 페이지 번호, 수정 내용 받아야함. 리다이렉트 해줘야함
	@PostMapping("/modify")
	public String modify(BoardDTO boardDTO, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes){
		boardService.modify(boardDTO);
		redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
		redirectAttributes.addFlashAttribute("msg", boardDTO.getBno());
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes redirectAttributes)
	{
		boardService.removeWithReply(bno);
		redirectAttributes.addFlashAttribute("msg", bno);
		return "redirect:/board/list";
	}
	
}












