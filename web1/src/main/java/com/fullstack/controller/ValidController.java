package com.fullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fullstack.command.CommandController;
import com.fullstack.command.ContentDTO;
import com.fullstack.command.ContentValidator;

@Controller
public class ValidController {

	@RequestMapping("/")
	public @ResponseBody String root()
	{
		return "Valid 1";
	}
	
	@RequestMapping("/insertForm")
	public String insert1()
	{
		return "createPage";
	}

	@GetMapping("/create")
	public String insert2(@ModelAttribute("dto") ContentDTO contentDTO, BindingResult result)
	{
		String page = "createDonepage";
		System.out.println(contentDTO);
		
		// validation 을 하기 위한 객체 생성
		ContentValidator validator = new ContentValidator();
		validator.validate(contentDTO, result);
		if(result.hasErrors())
		{
			page = "createPage";
		}
		return page;
				
		
	}
	
}















