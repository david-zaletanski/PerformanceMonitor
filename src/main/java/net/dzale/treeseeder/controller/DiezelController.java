package net.dzale.treeseeder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
public class DiezelController {

	@RequestMapping("/")
	public String handleRequest(Model model) {
		return "index";
	}
	
}
