package com.fullstack.command;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/command")
public class CommandController {

	@RequestMapping("/test")
	public @ResponseBody String root() throws Exception
	{
		return "스프링 부트에서 JSP 사용하기";
	}
	
	@GetMapping("/test1")
	public String test1(HttpServletRequest request, Model model)
	{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		
		// model 객체에 addAtt...() 를 이용해서 데이터를 다른 곳으로 전송 가능
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		return "test1";
		
	}
	
	// 어노테이션을 이용한 파람매핑
	@GetMapping("/test2")
	public String test2(@RequestParam("id") String id, @RequestParam("name") String name, Model model)
	{
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		return "test2";
	}
	
	// 모든 키가 같다면, DTO 객체를 통째로 파라미터에 넣음
	@GetMapping("/test3")
	public String test3(Member member, Model model)
	{
		// 이 방식을 사용하면 따로 모델객체를 이용하지 않아도 Viewer 로 command 객체가 자동 전달됨
		return "test3";
	}
	
	// PathVariable : 요청 오는 URL 을 Path 에 값을 매핑하는 어노테이션
	// 보통 비동기 통신에 적용할때 많이 사용
	// 특정 페이지의 특정 데이터만 요청할때 서버에서는 이 요청을 path 정보로 구분해서 
	//  그 값에 대한 정보만 되돌려 주는 기능을 할 때 많이 사용됨
	@GetMapping("/test4/{stuId}/{idx}")
	public String getStuInfo(@PathVariable ("stuId") String stuId, @PathVariable ("idx") Long idx, Model model)
	{
		model.addAttribute("id", stuId);
		model.addAttribute("num", idx);
		return "test4";
	}
	
	
	
}












