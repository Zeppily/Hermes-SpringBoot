package fi.geoffrey.hermes.domain;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	Project findByName(String name);

}
