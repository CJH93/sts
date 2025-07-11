package com.fullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fullstack.dto.GuestBookDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.GuestBook;
import com.fullstack.service.GuestBookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor // 자동 주입
public class GuestBookController {

	private final GuestBookService guestBookService;
	
	@GetMapping("/") // localhost/guestbook or /guestbook/list 요청 매핑
	public String index()
	{
		log.info("list 요청 받음");
		return "redirect:/guestbook/list";
	}
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model)
	{
	 	PageResultDTO<GuestBookDTO, GuestBook> res = guestBookService.getList(pageRequestDTO);
		
	 	// 요청 파라미터는 DTO 로 매핑
		model.addAttribute("result", res);
		
	}
	
	@GetMapping("/register")
	public void register()
	{
		
	}
	
	@PostMapping("/register")
	public String registerProc(GuestBookDTO guestBookDTO, RedirectAttributes redirectAttributes)
	{
		// DB 에 입력된 글 번호를 list 에 파라미터로 넘김
		// DB 에 글 입력하기
		Long gno = guestBookService.register(guestBookDTO);
		
		redirectAttributes.addFlashAttribute("msg", gno);
		return "redirect:/guestbook/list";
	}
	
	@GetMapping({"/read", "/modify"})
	public void read(@RequestParam("gno") Long gno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model)
	{
		GuestBookDTO dto = guestBookService.read(gno);
		model.addAttribute("dto", dto);
	}
	
	// 페이지 번호, 수정 내용 받아야함. 리다이렉트 해줘야함
	@PostMapping("/modify")
	public String modify(GuestBookDTO guestBookDTO, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes){
		guestBookService.modify(guestBookDTO);
		redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
		redirectAttributes.addFlashAttribute("msg", guestBookDTO.getGno());
		return "redirect:/guestbook/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("gno") Long gno, RedirectAttributes redirectAttributes)
	{
		guestBookService.remove(gno);
		redirectAttributes.addFlashAttribute("msg", gno);
		return "redirect:/guestbook/list";
	}
	
}









