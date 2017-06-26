package net.dzale.treeseeder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TreeseederController {

	@RequestMapping("/")
	public String handleRequest(Model model) {
		model.addAttribute("test", "THIS IS THE TEST ATTRIBUTE VALUE");
		return "blank";
	}
	
}
