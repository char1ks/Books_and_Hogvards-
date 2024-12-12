package com.example.SpringRedisJWTDocker_demo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringRedisJwtDockerDemo3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisJwtDockerDemo3Application.class, args);
	}

}
