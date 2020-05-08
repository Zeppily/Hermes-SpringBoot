package fi.geoffrey.hermes.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.geoffrey.hermes.domain.Project;
import fi.geoffrey.hermes.domain.ProjectRepository;
import fi.geoffrey.hermes.domain.Task;
import fi.geoffrey.hermes.domain.TaskRepository;
import fi.geoffrey.hermes.domain.User;
import fi.geoffrey.hermes.domain.UserRepository;
import net.minidev.json.JSONObject;

@RestController
public class APIcontroller {
	
	@Autowired
	UserRepository uRepository;
	
	@Autowired
	ProjectRepository pRepository;
	
	@Autowired
	TaskRepository tRepository;
	
	@RequestMapping(value="/api/allusers", method = RequestMethod.GET)
	public @ResponseBody List<User> usersRest(){
		return (List<User>) uRepository.findAll();
	}
	
	@RequestMapping(value="/api/allprojects", method = RequestMethod.GET)
	public @ResponseBody List<Project> projectsRest(){
		return (List<Project>) pRepository.findAll();
	}
	
	@RequestMapping(value="/api/alltasks", method = RequestMethod.GET)
	public @ResponseBody List<Task> tasksRest(){
		return (List<Task>) tRepository.findAll();
	}
	
	@RequestMapping(value="/api/adduser", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody User addUserRest(@RequestBody JSONObject json) {
		User user = new User();
		
		user.setEmail(json.getAsString("email"));
		user.setUsername(json.getAsString("username"));
		user.setLastName(json.getAsString("lastname"));
		user.setFirstName(json.getAsString("firstname"));
		user.setPassword(json.getAsString("password"));
		
		uRepository.save(user);
		
		return user;
	}
	
	@RequestMapping(value="/api/addproject", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody Project addProjectRest(@RequestBody JSONObject json) {
		Project project = new Project();
		
		String email = json.getAsString("email");
		String name = json.getAsString("name");
		User user = uRepository.findByEmail(email); 
		
		project.setName(name);
		project.setProjectOwner(user);
		
		
		pRepository.save(project);
		
		user.addProject(project);
		uRepository.save(user);
		
		return project;
	}
	
	@RequestMapping(value="/api/addmember", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody Project addMemberRest(@RequestBody JSONObject json) {
		
		String email = json.getAsString("email");
		String name = json.getAsString("name");
		User user = uRepository.findByEmail(email); 
		
		Project project = pRepository.findByName(name);
		
		user.addProject(project);
		uRepository.save(user);		
		
		return project;
	}
	
	

}
