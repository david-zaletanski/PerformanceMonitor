package net.dzale.performancedashboard.controller;

import net.dzale.performancedashboard.exceptions.PerformanceDashboardException;
import net.dzale.performancedashboard.service.metrics.SystemMetricsService;
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
public class PerformanceDashboardController {
    private static final Logger log = LoggerFactory.getLogger(PerformanceDashboardController.class);

    @Autowired
    SystemMetricsService systemMetricsService;

    @RequestMapping("/")
    public String handleRequest(Model model) throws PerformanceDashboardException {
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

    /**
     * Test comment.
     *
     * @return
     * @throws PerformanceDashboardException
     */
    @RequestMapping("/admintest")
    public String handleTestRequest() throws PerformanceDashboardException {
        log.debug("TEST ENDPOINT CALLED");
        return "TEST SUCCESS";
    }

    @RequestMapping("/admin")
    public String handleAdminRequest(Model model) throws PerformanceDashboardException {
        model.addAttribute("recentSystemCpuLoad", systemMetricsService.getRecentMetricsSystemCPULoadDataPoints());
        model.addAttribute("recentCpuLoad", systemMetricsService.getRecentMetricsCPULoadDataPoints());
        return "admin";
    }

    @RequestMapping("/performance")
    public String handlePerformanceRequest(Model model) throws PerformanceDashboardException {
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

}
