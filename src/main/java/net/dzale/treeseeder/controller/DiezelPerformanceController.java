package net.dzale.treeseeder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller serves system metric and performance related requests.
 * @author dzale
 */
@Controller
@RequestMapping("/srv")
public class DiezelPerformanceController extends  DiezelController {

    @RequestMapping("/prf")
    public String handleRequest(Model model) {
        return "admin";
    }

}
