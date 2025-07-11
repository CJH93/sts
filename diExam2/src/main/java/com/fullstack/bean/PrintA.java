package com.fullstack.bean;

import org.springframework.stereotype.Component;

@Component("printA")
public class PrintA implements Printer {

	@Override
	public void print(String message) {

		System.out.println("Print A 수행 : " + message);
		
	}

}
