package com.green.namu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 자동 감사 기능(Auditing) 활성화
public class NamuApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamuApplication.class, args);
	}

}
