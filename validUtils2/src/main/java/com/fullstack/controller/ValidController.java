package com.fullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fullstack.command.CommandController;
import com.fullstack.command.ContentDTO;

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
		
		if(result.hasErrors())
		{
			if(result.getFieldError("writer") != null)
			{
				System.out.println("!!" + result.getFieldError("writer").getDefaultMessage());
			}
			page = "createPage";
		}
			
			
		return page;
				
		
	}
	
	
}















