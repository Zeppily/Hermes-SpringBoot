package fi.geoffrey.hermes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.geoffrey.hermes.domain.UserRepository;

@Controller
public class UsersController {

	@Autowired
	private UserRepository uRepository;
	
	@RequestMapping(value="/userlist")
	public String userList(Model model) {
		model.addAttribute("users", uRepository.findAll());
		return "userlist";
	}
}
