package fi.geoffrey.hermes.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.geoffrey.hermes.domain.Project;
import fi.geoffrey.hermes.domain.ProjectRepository;
import fi.geoffrey.hermes.domain.Task;
import fi.geoffrey.hermes.domain.TaskRepository;
import fi.geoffrey.hermes.domain.User;
import fi.geoffrey.hermes.domain.UserRepository;

/**
 * Controller for Project actions
 * 
 * @author Geoffrey
 *
 */
@Controller
public class ProjectsController {

	@Autowired
	private TaskRepository tRepository;

	@Autowired
	private UserRepository uRepository;

	@Autowired
	private ProjectRepository pRepository;

	// All projects to the projectlist.html page

	@RequestMapping(value = "/admin/projectlist")
	public String projectList(Model model) {
		model.addAttribute("projects", pRepository.findAll());
		return "projectlist";
	}

	// Add project redirect

	@RequestMapping(value = "/addproject")
	public String addProject(Model model, Authentication authentication) {
		model.addAttribute("project", new Project());
		return "addproject";
	}

	// Create a project

	@RequestMapping(value = "/saveProject", method = RequestMethod.POST)
	public String saveProject(Project project, Authentication auth, BindingResult bindingResult) {
		if (pRepository.findByName(project.getName()) != null) {
			bindingResult.rejectValue("name", "err", "A project with this name already exists!");
			return "addproject";
		}
		User user = uRepository.findByUsername(auth.getName());

		pRepository.save(project);

		user.addProject(project);
		uRepository.save(user);

		return "redirect:/index";
	}

	// Project Page

	@RequestMapping(value = "/project/{name}", method = RequestMethod.GET)
	public String projectPage(Authentication auth, Model model, @PathVariable("name") String projectName) {
		Project project = pRepository.findByName(projectName);
		User user = uRepository.findByUsername(auth.getName());
		List<Task> tasks = project.getTasks();
		List<Task> todo = new ArrayList<Task>();
		List<Task> progress = new ArrayList<Task>();
		List<Task> completed = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getState() == 1) {
				todo.add(task);
			}
			if (task.getState() == 2) {
				progress.add(task);
			}
			if (task.getState() == 3) {
				completed.add(task);
			}
		}

		if (project.getUsers().contains(user)) {
			model.addAttribute("project", project);
			model.addAttribute("todos", todo);
			model.addAttribute("progress", progress);
			model.addAttribute("completed", completed);
			return "project";
		} else {
			return "errorAccess";
		}
	}

	// Adding users to a project

	@RequestMapping(value = "/project/{name}/addmember", method = RequestMethod.GET)
	public String addMemberProject(@PathVariable("name") String projectName, @RequestParam String email, Model model,
			Authentication auth, HttpServletRequest request) {

		Project project = pRepository.findByName(projectName);
		User user = uRepository.findByUsername(auth.getName());
		User userAdd = uRepository.findByEmail(email);

		if (project.getUsers().contains(user) && userAdd != null) {
			userAdd.addProject(project);
			uRepository.save(userAdd);
			model.addAttribute("project", project);
			String referer = request.getHeader("Referer");
			return "redirect:" + referer;
		} else {
			return "errorAccess";
		}
	}

	// Add task to a project

	@RequestMapping(value = "/project/{name}/addtask", method = RequestMethod.GET)
	public String addTask(@PathVariable("name") String projectName, @RequestParam String description, Model model,
			Authentication auth, HttpServletRequest request) {
		Project project = pRepository.findByName(projectName);
		if (description != "") {
			Task task = new Task(description, 1, project);
			tRepository.save(task);
		}

		// model.addAttribute("project", project);
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	// Set a task to in progress or completed
	
	@RequestMapping(value = "/movetask/{id}", method = RequestMethod.GET)
	public String progressTask(@PathVariable("id") Long taskId, Model model, Authentication authentication,
			HttpServletRequest request) {
		
		Task task = tRepository.findById(taskId).get();
		
		
		if(task.getState() == 2) {
			task.setState(3);
			tRepository.save(task);			
		}
		
		if(task.getState() == 1) {
			task.setState(2);
			task.setUser(uRepository.findByUsername(authentication.getName()));
			tRepository.save(task);			
		}
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	// Delete project function
	// This erases the project from all the users before deleting the project

	@RequestMapping(value = "/projectdelete/{id}", method = RequestMethod.GET)
	public String deleteProject(@PathVariable("id") Long projectId, Model model, Authentication authentication) {
		String authName = authentication.getName();
		Project project = pRepository.findById(projectId).get();

		if (uRepository.findByUsername(authName).getRole().equals("ADMIN")) {
			List<User> users = uRepository.findUsersByProjectsId(projectId);

			for (User user : users) {
				user.removeProject(project);
			}

			pRepository.deleteById(projectId);
		}
		return "redirect:../admin/projectlist";
	}

	// Delete Task
	@RequestMapping(value = "/deletetask/{id}", method = RequestMethod.GET)
	public String deleteTask(@PathVariable("id") Long taskId, Model model, Authentication authentication,
			HttpServletRequest request) {
		Task task = tRepository.findById(taskId).get();

		if (task != null) {
			tRepository.deleteById(taskId);
		}

		String referer = request.getHeader("Referer");
		return "redirect:" + referer;

	}

}
