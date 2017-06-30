package net.dzale.treeseeder.system;

import org.quartz.Trigger;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import oshi.util.FormatUtil;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author dzale
 * What the SystemMetricsMMonitor stores its results in to, and manages the biodiversity.
 */
public class SystemMetricsMonitor {

    double[] cpuLoad;
    double cpuLoadAvg;
    long memAvail;
    long memUsed;
    long memTotal;
    long memSwapAvail;
    long memSwapUsed;



    public SystemMetricsMonitor(CentralProcessor processor,
                                GlobalMemory memory,
                                HWDiskStore disks,
                                NetworkIF[] networkIFs) {
        // Define members.
        cpuLoadAvg = processor.getSystemLoadAverage();
        cpuLoad = processor.getProcessorCpuLoadBetweenTicks();
        memAvail = memory.getAvailable();
        memUsed = memory.getTotal()-memory.getAvailable();
        memTotal = memory.getTotal();
        memSwapAvail = memory.getAvailable();
        memSwapUsed = memory.getSwapUsed();


    }

}
