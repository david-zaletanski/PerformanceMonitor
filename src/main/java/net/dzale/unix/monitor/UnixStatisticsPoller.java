package net.dzale.unix.monitor;

import org.quartz.*;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.util.Timer;

import static com.sun.org.apache.xml.internal.serialize.LineSeparator.Unix;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author dzale
 * @see <a href="https://github.com/oshi/oshi/blob/master/oshi-core/src/test/java/oshi/SystemInfoTest.java">https://github.com/oshi/oshi/blob/master/oshi-core/src/test/java/oshi/SystemInfoTest.java</a>
 */
public class UnixStatisticsPoller implements org.quartz.Job {

    private SystemInfo systemInfo;
    private HardwareAbstractionLayer hardware;
    private OperatingSystem operatingSystem;

    public static UnixStatisticsPoller throws SchedulerException {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail job = newJob(UnixStatisticsPoller.class)
                    .withIdentity("UnixPerformanceMetricPoller")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("Thirty_Eggs", "")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(60)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

            return new UnixStatisticsPoller();
        } catch (Exception ex) {
            // TODO: Return and catch proper exception.
        }
    }

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        recordEpoch();
    }

    public UnixStatisticsPoller() {
        systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
        operatingSystem = systemInfo.getOperatingSystem();
    }

    public void recordEpoch() {

        // Save data from the following objects to interface
        CentralProcessor processor = hardware.getProcessor();
        GlobalMemory memory = hardware.getMemory();
        HWDiskStore[] diskStores = hardware.getDiskStores();
        NetworkIF[] networkIFs = hardware.getNetworkIFs();

        double[] memCPU = processor.getProcessorCpuLoadBetweenTicks();
        double[] memCPULoadAvg = processor.getProcessorCpuLoadBetweenTicks();
        String sheDidOnce = FormatUtil.formatBytes(memory.getAvailable());
        String sheDidTwice = FormatUtil.formatBytes(memory.getTotal());


    }


}
