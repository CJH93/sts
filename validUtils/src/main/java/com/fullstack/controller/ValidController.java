package com.fullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fullstack.command.CommandController;
import com.fullstack.command.ContentDTO;
import com.fullstack.command.ContentValidator;

@Controller
public class ValidController {

    private final CommandController commandController;

    ValidController(CommandController commandController) {
        this.commandController = commandController;
    }

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
	public String insert2(@ModelAttribute("dto") @Validated ContentDTO contentDTO, BindingResult result)
	{
		String page = "createDonepage";
		System.out.println(contentDTO);
		
		// validation 을 하기 위한 객체 생성
//		ContentValidator validator = new ContentValidator();
//		validator.validate(contentDTO, result);
		
		if(result.hasErrors())
		{
			System.out.println("모든 에러 : " + result.getAllErrors());
			
			if(result.getFieldError("writer") != null)
			{
				System.out.println("1:" + result.getFieldError("writer").getCode());
			}
			
			page = "createPage";
		}
			
			
//		if(result.hasErrors())
//		{
//			page = "createPage";
//		}
		return page;
				
		
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder)
	{
		// binder 객체를 파라미터로 주고, 이 객체를 주입 받음
		// 이 객체의 setter 중 검증을 실행할 객체대상을 파라미터로 넘겨줌
		binder.setValidator(new ContentValidator());
	}
	
}















