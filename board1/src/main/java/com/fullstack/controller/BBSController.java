package com.fullstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fullstack.dao.BBSDaoInter;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BBSController {

	@Autowired
	private BBSDaoInter bbsDaoInter;
	
	@RequestMapping("/")
	public String root()
	{
		return "redirect:list";
	}
	
	@GetMapping("/list")
	public String listPage(Model model)
	{
		model.addAttribute("list", bbsDaoInter.listDao());
		return "list";
	}
	
	// 상세보기
	@GetMapping("/view")
	public String view(@RequestParam("id") String id, Model model)
	{
		model.addAttribute("dto", bbsDaoInter.viewDao(id));
		return "view";
	}
	
	// 글쓰기 폼 요청
	@GetMapping("/writeForm")
	public String writeForm()
	{
		return "writeForm";
	}
	
	@PostMapping("/write")
	public String write(HttpServletRequest req, Model model)
	{
		bbsDaoInter.writeDao(req.getParameter("writer"), req.getParameter("title"), req.getParameter("content"));
		return "redirect:list";
	}
	
	
	
}














