package net.dzale.treeseeder.system;

import net.dzale.treeseeder.controller.DiezelErrorController;
import org.quartz.*;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;

import static javax.swing.text.html.HTML.Tag.I;
import static org.quartz.JobBuilder.newJob;

/**
 * The SystemMetricsPolling job is designed to execute periodically, collecting and persisting system and performance metrics.
 * @author dzale
 * @see <a href="https://github.com/oshi/oshi/blob/master/oshi-core/src/test/java/oshi/SystemInfoTest.java">https://github.com/oshi/oshi/blob/master/oshi-core/src/test/java/oshi/SystemInfoTest.java</a>
 */
public class SystemMetricsCollectionJob implements Job {

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        // TODO: Collect the metrics and PERSIST them.

    }


}
