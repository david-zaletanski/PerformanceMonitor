package net.dzale.diezel.controller;

import net.dzale.diezel.exceptions.DiezelException;
import net.dzale.diezel.model.grams.Gram;
import net.dzale.diezel.service.GramService;
import net.dzale.diezel.service.metrics.SystemMetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
public class DiezelController {
    private static final Logger log = LoggerFactory.getLogger(DiezelController.class);

    @Autowired
    SystemMetricsService systemMetricsService;
    @Autowired
    GramService gramService;

    @RequestMapping("/")
    public String handleRequest(Model model) throws DiezelException {
        // Create CPU Load Graph Data
        model.addAttribute("recentSystemCpuLoad", systemMetricsService.getRecentMetricsSystemCPULoadDataPoints());
        model.addAttribute("numVirtualCpu", systemMetricsService.getNumVirtualCPU());
        model.addAttribute("recentCpuLoad", systemMetricsService.getRecentMetricsCPULoadDataPoints());
        model.addAttribute("recentFSTotal", systemMetricsService.getFSStorageTotal());
        model.addAttribute("recentFSUsage", systemMetricsService.getRecentFSDataPoints());
        model.addAttribute("recentMemUsage", systemMetricsService.getRecentMemoryDataPoints());
        model.addAttribute("recentMemTotal", systemMetricsService.getMemoryStorageTotal());
        return "index";
    }

    @RequestMapping("/admintest")
    public String handleTestRequest() throws DiezelException {
        log.debug("TEST ENDPOINT CALLED");
        return "TEST SUCCESS";
    }

    @RequestMapping("/admin")
    public String handleAdminRequest(Model model) throws DiezelException {
        model.addAttribute("recentSystemCpuLoad", systemMetricsService.getRecentMetricsSystemCPULoadDataPoints());
        model.addAttribute("recentCpuLoad", systemMetricsService.getRecentMetricsCPULoadDataPoints());
        return "admin";
    }

    @RequestMapping("/performance")
    public String handlePerformanceRequest(Model model) throws DiezelException {
        // Create CPU Load Graph Data
        model.addAttribute("recentSystemCpuLoad", systemMetricsService.getRecentMetricsSystemCPULoadDataPoints());
        model.addAttribute("numVirtualCpu", systemMetricsService.getNumVirtualCPU());
        model.addAttribute("recentCpuLoad", systemMetricsService.getRecentMetricsCPULoadDataPoints());
        model.addAttribute("recentFSTotal", systemMetricsService.getFSStorageTotal());
        model.addAttribute("recentFSUsage", systemMetricsService.getRecentFSDataPoints());
        model.addAttribute("recentMemUsage", systemMetricsService.getRecentMemoryDataPoints());
        model.addAttribute("recentMemTotal", systemMetricsService.getMemoryStorageTotal());
        return "performance";
    }


    //@RequestMapping(path = "/gramslist", method = RequestMethod.GET)
    @GetMapping("/gramslist")
    public String handleGramsRequest(Model model) throws DiezelException {

        model.addAttribute("gram", new Gram());
        model.addAttribute("grams", gramService.getGrams());

        return "gramslist";
    }

    //@RequestMapping(path = "/gramslist", method = RequestMethod.POST)
    @PostMapping("/gramslist")
    public String handleGramsPost(@ModelAttribute Gram gram, Model model) throws DiezelException {

        gramService.addGram(gram.getBody());

        return handleGramsRequest(model);
    }
}
