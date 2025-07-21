package com.fullstack.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/*
 * 인증에 실패하면, BadCredentialException 타입의 예외가 발생함
 * 
 * 이에 대한 메세지를 JSON 으로 생성해서 프론트에게 전송할 예정
 * 이 역시 핸들러를 통해서 JSON 생성 후 보내도록 함
 */
@Log4j2
public class APILoginFailHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.info("로그인 실패" + exception);
		
		Gson gson = new Gson();
		
		String jsonStr = gson.toJson(Map.of("error", "ERROR_LOGIN"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.close();
		
	}

}
