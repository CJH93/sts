package com.fullstack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SNSController {

	private final MemberService memberService;
	
	@GetMapping("/api/member/kakao")
	public String[] getMemberFromKakao(@RequestParam("accessToken") String accessToKen)
	{
		log.info("---------------------accessToken--------------------- :::: " + accessToKen);
		
		memberService.getKakaoMember(accessToKen);
		
		return new String[] {"AAA", "BBB", "CCC"};
		
	}
	
}







