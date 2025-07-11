package com.fullstack.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MyController {

	@RequestMapping("/my")
	public @ResponseBody String root() throws Exception
	{
		return "스프링 부트에서 JSP 사용하기";
	}
	
	@RequestMapping("/test1")
	public String test1()
	{
		return "test1"; // 실제 호출될 test1 페이지의 이름과 같아야 함
	}
	
	@RequestMapping("/test2")
	public String test2()
	{
		return "sub/test2";
	}
	
	@RequestMapping("/mv")
	public ModelAndView test3()
	{
		ModelAndView mv = new ModelAndView();
		List<String> list = new ArrayList<>();
		
		list.add("하나");
		list.add("둘");
		list.add("셋");
		
		mv.addObject("list", list);
		mv.addObject("mtest", "모델테스트");
		mv.setViewName("view/myView");
		
		return mv;
		
	}
	
}







