package fi.geoffrey.hermes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value = "/admin/userlist")
	public String userList(Model model) {
		model.addAttribute("users", uRepository.findAll());
		return "userlist";
	}

	@RequestMapping(value = "/registration")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@RequestMapping(value = "/registration/saveUser", method = RequestMethod.POST)
	public String saveUser(User user) {
		uRepository.save(user);
		return "redirect:../index";
	}
	
	@RequestMapping(value="/admin/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long userId, Model model) {
		uRepository.deleteById(userId);
		return "redirect:../userlist";
	}
	
	@RequestMapping(value="/admin/editUser/{id}")
	public String editUser(@PathVariable("id") Long userId, Model model) {
		model.addAttribute("user", uRepository.findById(userId));
		return "editUser";
	}
	
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}

}
