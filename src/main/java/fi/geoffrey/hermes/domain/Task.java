package fi.geoffrey.hermes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	
	private String description;
	
	private int state;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "projectid")
	private Project project;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "userid")
	private User user;
	
	public Task() {
		
	}
	
	public Task(String description, int state, Project project) {
		this.setDescription(description);
		this.setProject(project);
		this.setState(state);
	}
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", description=" + description + ", state=" + state + ", project=" + project
				+ ", user=" + user + "]";
	}
	
	
	
}
