package com.mobilpack.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MobilpackManager  implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(MobilpackManager.class, args);
	}
}
