package net.dzale.diezel.system.metrics;

import com.sun.management.OperatingSystemMXBean;
import net.dzale.diezel.ApplicationContextProvider;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import javax.persistence.*;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Date;

/**
 * A data structure for holding and persisting a variety of current system metrics.
 *
 * @author dzale
 */
@Entity
public class SystemMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date collectionTimestamp;
    @Column
    private int cpuPhyiscalCount = 0;
    @Column
    private int cpuLogicalCount = 0;
    @Column
    private double cpuLoad = 0.0;
    @Column
    private double cpuLoadBetweenTicks = 0.0;
    @Column
    private double[] cpuLoadAvg = {0.0, 0.0, 0.0};
    @Column
    private double[] procLoadBetweenTicks = {0.0, 0.0, 0.0};
    @Column
    private double[] procLoad = {0.0, 0.0, 0.0};
    @Column
    private long memAvail = 0;
    @Column
    private long memUsed = 0;
    @Column
    private long memTotal = 0;
    @Column
    private long memSwapAvail = 0;
    @Column
    private long memSwapUsed = 0;
    @Column
    private long hdUsable = 0;
    @Column
    private long hdTotal = 0;
    @Column
    private long netBytesSent = 0;
    @Column
    private long netBytesReceived = 0;

    private SystemMetricsOperatingSystem operatingSystem;
    private String operatingSystemName = "";

    public SystemMetrics() {
        // Set configuration properties.
        //defaultFilestoreName =
    }

    // Configuration Properties
    private String defaultFilestoreName() {
        String val = ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("diezel.metrics.defaultFilestoreName");
        return (val == null ? "" : val);
    }

    public void setFromSystemInfo(SystemInfo systemInfo) {
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        setOperatingSystemData(osBean);
        setCollectionTimestamp(new Date());
        setProcessorData(hardware.getProcessor(), osBean);
        setMemoryData(hardware.getMemory());
        setFileSystemData(operatingSystem.getFileSystem());
        setNetworkIFData(hardware.getNetworkIFs());
    }

    private void setOperatingSystemData(OperatingSystemMXBean osBean) {
        this.operatingSystemName = osBean.getName();
        if (operatingSystemName.toUpperCase().contains("WINDOWS")) {
            operatingSystem = SystemMetricsOperatingSystem.Windows;
        } else if (operatingSystemName.toUpperCase().contains("MAC")) {
            operatingSystem = SystemMetricsOperatingSystem.MacOS;
        } else {
            operatingSystem = SystemMetricsOperatingSystem.Linux;
        }
    }

    private void setProcessorData(CentralProcessor processor, OperatingSystemMXBean osBean) {
        cpuPhyiscalCount = processor.getPhysicalProcessorCount();
        cpuLogicalCount = processor.getLogicalProcessorCount();
        cpuLoad = processor.getSystemCpuLoad();
        cpuLoadBetweenTicks = processor.getSystemCpuLoadBetweenTicks();
        cpuLoadAvg = processor.getSystemLoadAverage(3);
        // CPU Load Avg in OSHI doesn't work for Windows, so we get it with a different approach.
        if (this.operatingSystem.equals(SystemMetricsOperatingSystem.Windows))
            cpuLoadAvg = new double[]{0.0, 0.0, 0.0};
        procLoadBetweenTicks = processor.getProcessorCpuLoadBetweenTicks();
        procLoad = processor.getProcessorCpuLoadBetweenTicks();
    }

    private void setMemoryData(GlobalMemory memory) {
        memAvail = memory.getAvailable();
        memUsed = memory.getTotal() - memory.getAvailable();
        memTotal = memory.getTotal();
        memSwapAvail = memory.getAvailable();
        memSwapUsed = memory.getSwapUsed();
    }

    private void setFileSystemData(FileSystem fs) {
        if (fs.getFileStores().length > 0) {
            // Default to filestore 0
            OSFileStore filestore = fs.getFileStores()[0];
            // But look for main / user defined filestore
            for (OSFileStore aFilestore : fs.getFileStores()) {
                // Windows 'Local Fixed Disk (C:)'
                String name = aFilestore.getName();
                String defaultFilestoreName = defaultFilestoreName();
                if (!defaultFilestoreName.isEmpty()
                        && name.contains(defaultFilestoreName))
                    filestore = aFilestore;
            }
            hdUsable = filestore.getUsableSpace();
            hdTotal = filestore.getTotalSpace();
        }
    }

    private void setNetworkIFData(NetworkIF[] networkIFs) {
        for (NetworkIF net : networkIFs) {
            if (!Arrays.toString(net.getIPv4addr()).isEmpty()) {
                netBytesReceived = net.getBytesRecv();
                netBytesSent = net.getBytesSent();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CPU Physical Count: %d%nCPU Virtual Count: %d%n", cpuPhyiscalCount, cpuLogicalCount));
        sb.append(String.format("CPU Load: %.1f%% (counting ticks)%n", cpuLoad * 100));
        sb.append(String.format("CPU Load: %.1f%% (between ticks)%n", cpuLoadBetweenTicks * 100));

        sb.append(String.format("CPU Load Averages: " + (cpuLoadAvg[0] < 0 ? " N/A" : String.format(" %.2f", cpuLoadAvg[0]))
                + (cpuLoadAvg[1] < 0 ? " N/A" : String.format(" %.2f", cpuLoadAvg[1]))
                + (cpuLoadAvg[2] < 0 ? " N/A" : String.format(" %.2f", cpuLoadAvg[2]))
        ) + "\n");
        sb.append("CPU Load per Processor: ");
        for (double load : procLoadBetweenTicks) {
            sb.append(String.format(" %.1f%%", load * 100));
        }
        sb.append("\n");

        sb.append("Memory Usage: " + FormatUtil.formatBytes(memTotal - memAvail) + "/"
                + FormatUtil.formatBytes(memTotal) + String.format(" (%.2f%% Used)%n", (double) (memTotal - memAvail) / memTotal * 100.0));
        sb.append("Memory Swap: " + FormatUtil.formatBytes(memSwapUsed) + "/"
                + FormatUtil.formatBytes(memSwapAvail) + String.format(" (%.2f%% Used)%n", (double) (memSwapAvail - memSwapUsed) / memSwapAvail * 100.0));

        sb.append("HD Usage: " + FormatUtil.formatBytes(hdTotal - hdUsable) + "/"
                + FormatUtil.formatBytes(hdTotal) + " "
                + String.format("(%.2f%% Used)%n", (double) (hdTotal - hdUsable) / hdTotal * 100.0));

        sb.append("Network Usage: " + FormatUtil.formatBytes(netBytesSent) + " Sent / "
                + FormatUtil.formatBytes(netBytesReceived) + " Received");

        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCollectionTimestamp() {
        return collectionTimestamp;
    }

    public void setCollectionTimestamp(Date collectionTimestamp) {
        this.collectionTimestamp = collectionTimestamp;
    }

    public int getCpuPhyiscalCount() {
        return cpuPhyiscalCount;
    }

    public void setCpuPhyiscalCount(int cpuPhyiscalCount) {
        this.cpuPhyiscalCount = cpuPhyiscalCount;
    }

    public int getCpuLogicalCount() {
        return cpuLogicalCount;
    }

    public void setCpuLogicalCount(int cpuLogicalCount) {
        this.cpuLogicalCount = cpuLogicalCount;
    }

    public double getCpuLoadBetweenTicks() {
        return cpuLoadBetweenTicks;
    }

    public void setCpuLoadBetweenTicks(double cpuLoadBetweenTicks) {
        this.cpuLoadBetweenTicks = cpuLoadBetweenTicks;
    }

    public double[] getProcLoadBetweenTicks() {
        return procLoadBetweenTicks;
    }

    public void setProcLoadBetweenTicks(double[] procLoadBetweenTicks) {
        this.procLoadBetweenTicks = procLoadBetweenTicks;
    }

    public double getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public double[] getCpuLoadAvg() {
        return cpuLoadAvg;
    }

    public void setCpuLoadAvg(double[] cpuLoadAvg) {
        this.cpuLoadAvg = cpuLoadAvg;
    }

    public double[] getProcLoad() {
        return procLoad;
    }

    public void setProcLoad(double[] procLoad) {
        this.procLoad = procLoad;
    }

    public long getMemAvail() {
        return memAvail;
    }

    public void setMemAvail(long memAvail) {
        this.memAvail = memAvail;
    }

    public long getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(long memUsed) {
        this.memUsed = memUsed;
    }

    public long getMemTotal() {
        return memTotal;
    }

    public void setMemTotal(long memTotal) {
        this.memTotal = memTotal;
    }

    public long getMemSwapAvail() {
        return memSwapAvail;
    }

    public void setMemSwapAvail(long memSwapAvail) {
        this.memSwapAvail = memSwapAvail;
    }

    public long getMemSwapUsed() {
        return memSwapUsed;
    }

    public void setMemSwapUsed(long memSwapUsed) {
        this.memSwapUsed = memSwapUsed;
    }

    public long getHdUsable() {
        return hdUsable;
    }

    public void setHdUsable(long hdUsable) {
        this.hdUsable = hdUsable;
    }

    public long getHdTotal() {
        return hdTotal;
    }

    public void setHdTotal(long hdTotal) {
        this.hdTotal = hdTotal;
    }

    public long getNetBytesSent() {
        return netBytesSent;
    }

    public void setNetBytesSent(long netBytesSent) {
        this.netBytesSent = netBytesSent;
    }

    public long getNetBytesReceived() {
        return netBytesReceived;
    }

    public void setNetBytesReceived(long netBytesReceived) {
        this.netBytesReceived = netBytesReceived;
    }
}
