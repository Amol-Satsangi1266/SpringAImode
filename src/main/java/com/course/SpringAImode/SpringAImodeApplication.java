package com.course.SpringAImode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAImodeApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringAImodeApplication.class, args);

		System.out.println(
				"API Key present: " +
						(System.getenv("GEMINI_API_KEY") != null)
		);
	}

}
