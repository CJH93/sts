package com.fullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fullstack.dao")
public class MybatisExApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisExApplication.class, args);
	}

}
