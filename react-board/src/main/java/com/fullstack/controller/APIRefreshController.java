package com.fullstack.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.utils.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

	// 토큰 관련 메서드
	@RequestMapping("api/member/refresh")
	public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, @RequestParam("refreshToken") String refreshToken)
	{
		if(refreshToken == null)
		{
			throw new RuntimeException("NULL_REFRESH");
		}
		if(authHeader == null || authHeader.length() < 7)
		{
			throw new RuntimeException("NULL_REFRESH");
		}
		String accessToken = authHeader.substring(7);
		
		// 각 조건에 따른 토큰 핸들
		if(checkExpiredValidToken(accessToken) == false)
		{
			return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
		}
		
		// refresh token 검증
		Map<String, Object> claims = JWTUtil.validateToKen(refreshToken);
		
		String newAccessToken = JWTUtil.generateToken(claims, 10);
		
		String newRefreshToken = checkTime((Integer)claims.get("exp")) == true ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;
		
		return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
		
	}
	
	// 토큰이 유효시간을 검증하는 메서드 정의
	// 30분 미만의 토큰 유효시간이 남았는지 검증
	private boolean checkTime(Integer exp)
	{
		Date extDate = new Date((long)exp * (1000));
		
		// 현재 시간과의 차이 계산
		long gap = extDate.getTime() - System.currentTimeMillis();
		
		// 분 단위 계산
		long leftMin = gap / (1000 * 60);
		
		return leftMin < 30;
		
	}
	
	// 토큰의 유효성 검증 메서드 정의(위에는 시간 기준)
	// 토큰 내용의 적합성 기준 판별
	// boolean 을 리턴하도록 Token 유틸의 메서드를 이용해서 정의
	private boolean checkExpiredValidToken(String token)
	{
		try 
		{
			JWTUtil.validateToKen(token);
		} 
		catch (Exception e) 
		{
			if(e.getMessage().equalsIgnoreCase("expired"))
				return true;
		}
		
		return false;
		
	}	
	
}









