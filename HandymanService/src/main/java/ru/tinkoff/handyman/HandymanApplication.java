package ru.tinkoff.handyman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.tinkoff.handyman.repository.HandymanRepository;

@EnableFeignClients
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = HandymanRepository.class)
public class HandymanApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandymanApplication.class, args);
	}

}
