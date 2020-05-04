package fi.geoffrey.hermes.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

	// All users to the userlist.html page

	@RequestMapping(value = "/admin/userlist")
	public String userList(Model model) {
		model.addAttribute("users", uRepository.findAll());
		return "userlist";
	}

	// redirect to registration page

	@RequestMapping(value = "/registration")
	public String addUser(Model model) {

		model.addAttribute("user", new User());

		return "registration";
	}

	// Create user and redirect home page upon successful validation

	@RequestMapping(value = "/registration/saveUser", method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		if (uRepository.findByEmail(user.getEmail()) != null) {
			bindingResult.rejectValue("email", "err", "An account with that email address already exists");
			return "registration";
		}
		if (uRepository.findByUsername(user.getUsername()) != null) {
			bindingResult.rejectValue("username", "err", "That username is already in use");
			return "registration";
		}

		uRepository.save(user);

		return "redirect:../index";
	}

	// Save function for administrator edited users

	@RequestMapping(value = "/admin/saveUser", method = RequestMethod.POST)
	public String saveUserAdmin(@Valid User user, BindingResult bindingResult) {

		uRepository.save(user);

		return "redirect:../admin/userlist";
	}

	// Delete function for administrator

	@RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long userId, Model model) {
		uRepository.deleteById(userId);
		return "redirect:../userlist";
	}

	// Administrator redirect to edit users page

	@RequestMapping(value = "/admin/editUser/{id}")
	public String editUser(@PathVariable("id") Long userId, Model model) {
		model.addAttribute("user", uRepository.findById(userId));
		return "editUser";
	}

	// redirect to the login page

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	// User profile page
	
	@RequestMapping(value="/profile/{username}", method = RequestMethod.GET)
	public String showProfile(@PathVariable("username") String username, Model model, Authentication auth) {
		User user = uRepository.findByUsername(username);
		
		if(auth.getName().equals(user.getUsername())) {
			model.addAttribute("user", user);
			return "profile";
		}else {
			return "error";
		}
	}
}
