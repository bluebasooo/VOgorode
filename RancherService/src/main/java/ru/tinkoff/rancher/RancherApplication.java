package ru.tinkoff.rancher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.tinkoff.rancher.repository.RancherRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = RancherRepository.class)
@EnableFeignClients
public class RancherApplication {

	public static void main(String[] args) {
		SpringApplication.run(RancherApplication.class, args);
	}
}

