package fi.geoffrey.hermes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * CrudInterface for Users for data source interaction.
 * @author Geoffrey
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByUsername(String username);
	List<User> findByFirstName(String firstName);
	List<User> findByLastName(String lastName);
	List<User> findByEmail(String email);
}
