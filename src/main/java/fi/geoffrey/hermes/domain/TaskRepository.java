package fi.geoffrey.hermes.domain;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

	Task findByDescription(String description);
}
