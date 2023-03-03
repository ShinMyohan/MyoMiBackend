package com.myomi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MyomiJpaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MyomiJpaApplication.class, args);
	}

	// CORS 설정
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:5500/")
						.allowedHeaders("*")
						.allowedMethods("*")
						.allowCredentials(true);
			}
		};
	}

//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//				.allowedOrigins("http://localhost:5500")
//				.allowCredentials(true)
//				//CORS 에러 방지를 위해 더 추가 (방식들을 ) 허용하겠다.
//				.allowedMethods("GET","POST","PUT","DELETE");
////		WebMvcConfigurer.super.addCorsMappings(registry);
//	}
}