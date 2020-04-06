package fi.geoffrey.hermes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a Person with User like credentials 2 Parametric constructors to
 * create a normal user and a superUser
 * 
 * @author Geoffrey
 */

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
	 * @ManyToMany List<Project> projects;
	 */

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String email;

	private boolean superUser;

	public User() {

	}

	public User(String username, String password, String firstName, String lastName, String email) {
		this.setEmail(email);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setUsername(username);
		this.setPassword(password);
		this.setSuperUser(false);
	}

	public User(String username, String password, String firstName, String lastName, String email, boolean superUser) {
		this.setEmail(email);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setSuperUser(superUser);
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public Long getId() {
		return id;
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

	public boolean isSuperUser() {
		return superUser;
	}

	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}

	@Override
	public String toString() {
		return this.getUsername() + " " + this.getFirstName() + " " + this.getLastName() + " " + this.getEmail();
	}

}
