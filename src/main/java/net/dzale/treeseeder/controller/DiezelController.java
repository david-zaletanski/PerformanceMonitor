package net.dzale.treeseeder.controller;

import net.dzale.treeseeder.exceptions.DiezelException;
import net.dzale.treeseeder.service.SystemMetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
public class DiezelController {
	private static final Logger log = LoggerFactory.getLogger(DiezelController.class);

	@Autowired
	SystemMetricsService systemMetricsService;

	@RequestMapping("/")
	public String handleRequest(Model model) throws DiezelException {
		// Create CPU Load Graph Data
		model.addAttribute("recentSystemCpuLoad", systemMetricsService.getRecentMetricsSystemCPULoadDataPoints());
		model.addAttribute("numVirtualCpu", systemMetricsService.getNumVirtualCPU());
		model.addAttribute("recentCpuLoad", systemMetricsService.getRecentMetricsCPULoadDataPoints());
		model.addAttribute("recentFSTotal", systemMetricsService.getFSStorageTotal());
		model.addAttribute("recentFSUsage", systemMetricsService.getRecentFSDataPoints());
		return "index";
	}

	@RequestMapping("/admin")
	public String handleAdminRequest(Model model) throws DiezelException {
		model.addAttribute("recentSystemCpuLoad", systemMetricsService.getRecentMetricsSystemCPULoadDataPoints());
		model.addAttribute("recentCpuLoad", systemMetricsService.getRecentMetricsCPULoadDataPoints());
		return "admin";
	}
	
}
