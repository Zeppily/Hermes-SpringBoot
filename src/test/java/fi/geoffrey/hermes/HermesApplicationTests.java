package fi.geoffrey.hermes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import fi.geoffrey.hermes.web.APIcontroller;
import fi.geoffrey.hermes.web.HelloController;
import fi.geoffrey.hermes.web.ProjectsController;
import fi.geoffrey.hermes.web.UsersController;


@SpringBootTest
class HermesApplicationTests {
	
	@Autowired
	private HelloController helloController;
	
	@Autowired
	private APIcontroller apiController;
	
	@Autowired
	private ProjectsController projectsController;
	
	@Autowired
	private UsersController usersController;

	@Test
	void contextLoads() {
		assertThat(helloController).isNotNull();
		assertThat(apiController).isNotNull();
		assertThat(projectsController).isNotNull();
		assertThat(usersController).isNotNull();
	}

}
