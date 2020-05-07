package fi.geoffrey.hermes.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "project")
public class Project {

	@ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
	private Set<User> users;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
	private List<Task> tasks;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String name;

	public Project() {

	}

	public Project(String name) {
		this.setName(name);
	}

	// many to many getter
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		String projectUsers = "";
		Iterator<User> itr = users.iterator();

		while (itr.hasNext()) {
			projectUsers = projectUsers + itr.next().toString() + " ";
		}
		return "Project name: " + this.getName() + "members: " + projectUsers;
	}
}
