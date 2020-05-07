package fi.geoffrey.hermes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.geoffrey.hermes.domain.Project;
import fi.geoffrey.hermes.domain.ProjectRepository;
import fi.geoffrey.hermes.domain.User;
import fi.geoffrey.hermes.domain.UserRepository;

@SpringBootApplication
public class HermesApplication {
	private static final Logger log = LoggerFactory.getLogger(HermesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HermesApplication.class, args);
	}

	@Bean
	public CommandLineRunner hermesDemo(UserRepository uRepository, ProjectRepository pRepository) {
		return (args) -> {

			// Adding predetermined data if database is empty
			if (uRepository.count() == 0) {
				log.info("Adding Users");

				User user1 = new User("admin", "admin", "Geoffrey", "Test", "admin@gmail.com", "ADMIN");
				User user2 = new User("user", "user", "Not Geoffrey", "Tester", "user@gmail.com", "USER");
				User user3 = new User("mark", "mark", "Mark", "Flyer", "mark@gmail.com", "USER");
				User user4 = new User("steven", "steven", "Steven", "Johnson", "steven@gmail.com", "USER");
				User user5 = new User("diana", "diana", "Diana", "Smith", "diana@gmail.com", "USER");
				uRepository.save(user1);
				uRepository.save(user2);
				uRepository.save(user3);
				uRepository.save(user4);
				uRepository.save(user5);

				log.info("added users");
				for (User user : uRepository.findAll()) {
					log.info(user.toString());
				}
				
				log.info("adding projects");
				Project project1 = new Project("Bookstore");
				Project project2 = new Project("Terminator Reformed");
				
				project1.setProjectOwner(user3);
				project2.setProjectOwner(user5);

				pRepository.save(project1);
				pRepository.save(project2);
				
				user3.addProject(project1);
				user4.addProject(project1);
				user3.addProject(project2);
				user5.addProject(project2);
				
				uRepository.save(user3);
				uRepository.save(user4);
				uRepository.save(user5);

				
				/*
				Set<User> users1 = new HashSet<User>();
				users1.add(user3);
				users1.add(user4);
				
				Set<User> users2 = new HashSet<User>();
				users2.add(user5);
				
				project1.setUsers(users1);
				project2.setUsers(users2);
				
				pRepository.save(project1);
				pRepository.save(project2);
				
				*/
				
				log.info("=== DATABASE SUCCESFULLY POPULATED ===");
			} else {
				log.info("=== NO DATA ADDED, DB ALREADY POPULATED ===");
			}
			
			for (Project p : pRepository.findAll()) {
				log.info(p.toString());
			}
		};
	}

}
