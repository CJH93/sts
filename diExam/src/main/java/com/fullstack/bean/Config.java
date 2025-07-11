package com.fullstack.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	// Bean : Spring 이 IoC 방식으로 관리하는 객체
	// Bean Factory : IoC 를 담당하는 컨테이너
	// application context : 빈 팩토리를 확장한 IoC 컨테이너
	
	@Bean
	public Member member1() 
	{
		// setter 를 이용한 의존성 주입
		Member member1 = new Member();
		member1.setName("데몬헌터");
		member1.setNickName("killer");
		member1.setPrinter(new PrintA());
		return member1;
	}
	
	@Bean(name = "hello")
	public Member member2()
	{
		// 생성자를 이용한 객체 생성
		return new Member("지용", "GD", new PrintB());
	}
	
	@Bean
	public PrintA PrintA()
	{
		return new PrintA();
	}
	
	@Bean
	public PrintB PrintB()
	{
		return new PrintB();
	}
	
	
	
	
	
	
	
}
