package project.gradproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GradProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradProjectApplication.class, args);
	}

}
