package fi.geoffrey.hermes.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Represents a Person with User like credentials 2 Parametric constructors to
 * create a normal user and a superUser
 * 
 * @author Geoffrey
 */

@Entity
@Table(name = "users")
public class User {
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
			name = "user_project",
			joinColumns = { @JoinColumn(name = "userid") },
			inverseJoinColumns = { @JoinColumn(name = "projectid") })
	private Set<Project> projects = new HashSet<Project>(0);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;

	@NotNull(message = "username already exists")
	@Size(min = 2, max = 30, message = "Enter a valid username (2-30 chars long)")
	@Column(unique = true)
	private String username;

	@NotNull
	private String password;

	@NotNull
	@Size(min = 2, max = 30, message = "Enter a valid first name")
	private String firstName;

	@NotNull
	@Size(min = 2, max = 30, message = "Enter a valid last name")
	private String lastName;

	@NotNull
	@Email(message = "please enter a valid email-adress")
	@Size(min = 5, max = 50, message = "Please enter a valid email-address")
	@Column(unique = true)
	private String email;

	@NotNull
	private String role;

	public User() {
		this.setRole("USER");
	}

	public User(String username, String password, String firstName, String lastName, String email) {
		this.setEmail(email);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setUsername(username);
		this.setPassword(password);
		this.setRole("USER");
	}

	public User(String username, String password, String firstName, String lastName, String email, String role) {
		this.setEmail(email);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setRole(role);
		this.setUsername(username);
		this.setPassword(password);
	}

	//many to many getter

	public Set<Project> getCourses(){
		return this.projects;
	}
	
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		// BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		// this.password = bc.encode(password);
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public void addProject(Project project) {
		this.projects.add(project);
	}

	@Override
	public String toString() {
		return this.getUsername() + " " + this.getFirstName() + " " + this.getLastName() + " " + this.getEmail();
	}

	@PrePersist
	public void prePersist() {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		this.password = bc.encode(password);
	}

	@PreUpdate
	public void preUpdate() {
		if (!this.password.contains("$")) {
			BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			this.password = bc.encode(password);
		}

	}

}
