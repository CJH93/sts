package com.fullstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fullstack.dao.MyUserDao;

import lombok.Setter;

@Controller
public class MybatisController {

	//@Autowired
	@Setter(onMethod_ = {@Autowired})
	private MyUserDao dao;
	
	@RequestMapping("/")
	public @ResponseBody String root()
	{
		return "Hello";
	}
	
	@GetMapping("/users")
	public String userListPage(Model model)
	{
		model.addAttribute("users", dao.list());
		return "userlist";
	}
	
	
}
