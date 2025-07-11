package com.fullstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fullstack.dao.MyUserDao;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MybatisController {

	@Autowired
	private MyUserDao dao;
	
	@RequestMapping("/")
	public String root()
	{
		return "redirect:list";
	}
	
	@RequestMapping("/list")
	public String userListPage(Model model)
	{
		model.addAttribute("list", dao.listDao());
		return "list";
	}
	
	@RequestMapping("/view")
	public String view(HttpServletRequest req, Model model)
	{
		String id = req.getParameter("id");
		model.addAttribute("dto", dao.viewDao(Integer.parseInt(id)));
		return "view";
	}
		
	@RequestMapping("/writeForm")
	public String writeForm()
	{
		return "writeForm";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest req, Model model)
	{
		dao.writeDao(req.getParameter("writer"), req.getParameter("title"), req.getParameter("content"));
		return "redirect:list";
	}
	
	
}

















