package com.fullstack.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class Member {

	@Value("홍길동")
	private String name;
	@Value("의적")
	private String nickName;
	@Autowired
	@Qualifier("printA")
	private Printer printer;
	
	public void print()
	{
		printer.print("Hello " + name + " : " + nickName);
	}
	
}
