package fi.geoffrey.hermes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.geoffrey.hermes.domain.Project;
import fi.geoffrey.hermes.domain.ProjectRepository;
import fi.geoffrey.hermes.domain.Task;
import fi.geoffrey.hermes.domain.TaskRepository;

/*
 * All tests pass after starting project with default Hermes application credentials in database
 * !!! Will not pass if information is deleted out of database
 * 
 * */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {

	@Autowired
	private TaskRepository repository;

	@Autowired
	private ProjectRepository pRepository;

	@Test
	public void createTask() {
		Project project = new Project("Zippi's new workshop");
		pRepository.save(project);

		Task task = new Task("Do work", 1, project);
		repository.save(task);

		assertThat(task.getId()).isNotNull();
	}

	@Test
	public void deleteTask() {
		Project project = new Project("Zippi's new workshop");
		pRepository.save(project);

		Task task = new Task("Do work", 1, project);
		repository.save(task);

		repository.delete(task);

		assertThat(repository.findByDescription("Do work")).isNull();
	}

	@Test
	public void findTaskByDescriptionShouldReturnTask() {
		Project project = new Project("Zippi's new workshop");
		pRepository.save(project);

		Task task = new Task("Do work", 1, project);
		repository.save(task);

		assertThat(repository.findByDescription("Do work")).isNotNull();
	}

	@Test
	public void findTaskByTaskIdShouldReturnTask() {
		Project project = new Project("Zippi's new workshop");
		pRepository.save(project);

		Task task = new Task("Do work", 1, project);
		repository.save(task);

		long id = task.getId();

		assertThat(repository.findById(id).get().getDescription()).isEqualTo("Do work");
	}

}
