package com.fullstack.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {

	private String name;
	private String nickName;
	private Printer printer;
	
	public void print()
	{
		printer.print("Hello " + name + " : " + nickName);
	}
	
}
