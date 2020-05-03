package fi.geoffrey.hermes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.geoffrey.hermes.domain.ProjectRepository;

/**
 * Controller for Project actions
 * 
 * @author Geoffrey
 *
 */
@Controller
public class ProjectsController {


	@Autowired
	private ProjectRepository pRepository;

	
	// All projects to the projectlist.html page
	 
	@RequestMapping(value = "/admin/projects")
	public String projectList(Model model) {
		model.addAttribute("projects", pRepository.findAll());
		return "projectlist";
	}
	
	// Add project redirect
	
	// Create a project
	
	// Delete project function
}
