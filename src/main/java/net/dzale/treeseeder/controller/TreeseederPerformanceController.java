package net.dzale.treeseeder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/srv")
public class TreeseederPerformanceController {

    @RequestMapping("/prf")
    public String handleRequest(Model model) {
        return "admin";
    }

}
