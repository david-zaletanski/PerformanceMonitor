package net.dzale.unix.monitor;

import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import oshi.util.FormatUtil;

/**
 * Created by dzale on 6/29/17.
 */
public class UnixStatistics {

    double[] cpuLoad;
    double cpuLoadAvg;
    long memAvail;
    long memUsed;
    long memTotal;
    long memSwapAvail;
    long memSwapUsed;



    public UnixStatistics(CentralProcessor processor,
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
