package fi.geoffrey.hermes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.geoffrey.hermes.domain.User;
import fi.geoffrey.hermes.domain.UserRepository;

/*
 * All tests pass after starting project with default Hermes application credentials in database
 * !!! Will not pass if information is deleted out of database
 * 
 * */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Test
	public void createNewUser() {
		User user = new User("Zippity", "Zippity", "Zippity", "Zoppity", "Zippity@gmail.com", "USER");
		repository.save(user);

		assertThat(user.getId()).isNotNull();
	}

	@Test
	public void findByUsernameShouldReturnUser() {
		User user = repository.findByUsername("admin");

		assertThat(user.getFirstName().equals("Geoffrey"));

	}

	@Test
	public void findByEmailShouldReturnUser() {
		User user = repository.findByEmail("admin@gmail.com");

		assertThat(user.getFirstName().equals("Geoffrey"));

	}

	@Test
	public void findUsersByProjectIdShouldReturnUser() {

		List<User> users = repository.findUsersByProjectsId((long) 1);

		assertThat(users.get(0).getEmail()).isEqualTo("mark@gmail.com");

	}

	@Test
	public void findByFirstnameShouldReturnUser() {
		List<User> user = repository.findByFirstName("Geoffrey");

		assertThat(user.get(0).getFirstName().equals("Geoffrey"));
	}

	@Test
	public void findByLastnameShouldReturnUser() {
		List<User> user = repository.findByLastName("Test");

		assertThat(user.get(0).getFirstName().equals("Geoffrey"));
	}

	@Test
	public void deleteUser() {
		User user = new User("hercules", "hercules", "hercules", "strong", "hercules@gmail.com", "USER");

		repository.save(user);

		Long id = user.getId();

		repository.deleteById(id);

		User compare = repository.findByUsername("hercules");

		assertThat(compare).isNull();
	}

}
