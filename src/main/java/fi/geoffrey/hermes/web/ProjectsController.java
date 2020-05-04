package fi.geoffrey.hermes.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.geoffrey.hermes.domain.Project;
import fi.geoffrey.hermes.domain.ProjectRepository;
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
	
	// Adding users to a project
	
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
}
