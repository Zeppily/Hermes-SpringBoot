package fi.geoffrey.hermes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.geoffrey.hermes.domain.User;
import fi.geoffrey.hermes.domain.UserRepository;

@SpringBootApplication
public class HermesApplication {
	private static final Logger log = LoggerFactory.getLogger(HermesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HermesApplication.class, args);
	}

	@Bean
	public CommandLineRunner hermesDemo(UserRepository uRepository) {
		return (args) -> {

			// Adding predetermined Users if not already in database
			if(uRepository.count() == 0) {
				log.info("Adding Users");

				User user1 = new User("admin", "admin", "Geoffrey", "Test", "admin@gmail.com", "ADMIN");
				User user2 = new User("user", "user", "Not Geoffrey", "Tester", "user@gmail.com", "USER");
				uRepository.save(user1);
				uRepository.save(user2);

				log.info("added users");
				for (User user : uRepository.findAll()) {
					log.info(user.toString());
				}
			}else {
				log.info("=== NO USERS ADDED, ALREADY IN DB ===");
			}



		};
	}

}
