package net.dzale.treeseeder.system;

import net.dzale.treeseeder.service.SystemMetricsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This job will collect performance metrics and cache/persist them as necessary when executed.
 * @author dzale
 * @see <a href="https://github.com/oshi/oshi/blob/master/oshi-core/src/test/java/oshi/SystemInfoTest.java">https://github.com/oshi/oshi/blob/master/oshi-core/src/test/java/oshi/SystemInfoTest.java</a>
 */
@Component
public class SystemMetricsCollectionJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(SystemMetricsCollectionJob.class);

    @Autowired
    SystemMetricsService service;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        service.collectAndPersistCurrentSystemMetrics();
    }

}
