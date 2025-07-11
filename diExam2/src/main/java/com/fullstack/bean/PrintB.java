package com.fullstack.bean;

import org.springframework.stereotype.Component;

@Component("printB")
public class PrintB implements Printer {

	@Override
	public void print(String message) {

		System.out.println("Print B 수행 : " + message);
		
	}

}
