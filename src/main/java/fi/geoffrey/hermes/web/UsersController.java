package fi.geoffrey.hermes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.geoffrey.hermes.domain.User;
import fi.geoffrey.hermes.domain.UserRepository;

/**
 * Controller for user actions
 * 
 * @author Geoffrey
 *
 */
@Controller
public class UsersController {

	@Autowired
	private UserRepository uRepository;

	@RequestMapping(value = "/userlist")
	public String userList(Model model) {
		model.addAttribute("users", uRepository.findAll());
		return "userlist";
	}

	@RequestMapping(value = "/registration")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(User user) {
		uRepository.save(user);
		return "redirect:userlist";
	}

}
