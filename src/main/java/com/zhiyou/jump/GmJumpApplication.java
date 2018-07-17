package com.zhiyou.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class GmJumpApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmJumpApplication.class, args);
	}
}
