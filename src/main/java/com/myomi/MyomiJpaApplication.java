package com.myomi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@EnableScheduling
@SpringBootApplication
public class MyomiJpaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MyomiJpaApplication.class, args);
	}

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://{본인IP}:5500")
			.allowCredentials(true)
			//CORS 에러 방지를 위해 더 추가 (방식들을 ) 허용하겠다. 
			.allowedMethods("GET","POST","PUT","DELETE");
	}
}