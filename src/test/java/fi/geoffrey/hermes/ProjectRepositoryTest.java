package fi.geoffrey.hermes;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.geoffrey.hermes.domain.Project;
import fi.geoffrey.hermes.domain.ProjectRepository;
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
public class ProjectRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ProjectRepository pRepository;
	
	@Test
	public void createProject() {
		User user = new User("Zippity", "Zippity", "Zippity", "Zoppity", "Zippity@gmail.com", "USER");
		Project project = new Project("Zippi's new workshop");
		
		project.setProjectOwner(user);
		user.addProject(project);
		
		repository.save(user);
		pRepository.save(project);
		
		assertThat(project.getId()).isNotNull();		
	}
	
	@Test
	public void deleteProject() {
		Project project = new Project("Zippi's new workshop");

		pRepository.save(project);
		
		pRepository.delete(project);
		
		assertThat(pRepository.findByName("Zippi's new workshop")).isNull();
	}
	
	@Test
	public void findByProjectNameShouldReturnProject() {
		Project project = new Project("Zippi's new workshop");
		
		pRepository.save(project);
		
		assertThat(pRepository.findByName("Zippi's new workshop")).isNotNull();
	}
	
	@Test
	public void findByProjectIdShouldReturnProject() {
		Project project = new Project("Zippi's new workshop");
		
		pRepository.save(project);
		
		Long id = project.getId();
		
		assertThat(pRepository.findById(id).get()).isEqualTo(project);
	}
	
	
}
