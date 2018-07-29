package com.zhangrui.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MySpringBootMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootMongodbApplication.class, args);
	}
}
