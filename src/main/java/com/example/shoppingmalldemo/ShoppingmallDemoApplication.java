package com.example.shoppingmalldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.controller"}) // 컨트롤러를 인식 못해서 어노테이션 추가
public class ShoppingmallDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingmallDemoApplication.class, args);
	}
}
