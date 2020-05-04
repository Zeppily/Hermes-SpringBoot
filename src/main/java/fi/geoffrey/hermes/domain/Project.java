package fi.geoffrey.hermes.domain;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "project")
public class Project {

	@ManyToMany(mappedBy = "projects", fetch=FetchType.EAGER)
	private Set<User> users;
	
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
	
	//many to many getter
	public Set<User> getUsers(){
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

	@Override
	public String toString() {
		String projectUsers = "";
		Iterator<User> itr = users.iterator();
		
		while(itr.hasNext()){
			projectUsers = projectUsers + itr.next().toString() + " ";
		}
		return "Project name: " + this.getName() + "members: " + projectUsers;
	}
}
