package fi.geoffrey.hermes.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	// Testing Controller
	// Add thymeleaf
	// add documentation alt shift j
	
	@RequestMapping("/index")
	public String home() {
		return "index";
	}

}
