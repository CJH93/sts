package com.fullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fullstack.bean.Config;
import com.fullstack.bean.Member;
import com.fullstack.bean.PrintB;
import com.fullstack.bean.Printer;

//@SpringBootApplication
public class DiExamApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DiExamApplication.class, args);
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
		
		Member member1 = (Member)ctx.getBean("member1");
		member1.print();
		
		// 오버로딩된 getBean 이용, hello 멤버 get (캐스팅 불필요)
		Member member2 = ctx.getBean("hello", Member.class);
		member2.print();
		
		Printer printb = ctx.getBean("PrintB", PrintB.class);
		member1.setPrinter(printb);
		member1.print();
		
		
		
		
		
		
		
		
		
		
	}

}
