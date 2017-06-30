package net.dzale.treeseeder.system;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.util.Date;

/**
 * @author dzale
 */
public class SystemMetrics {

    private SystemInfo systemInfo;
    private HardwareAbstractionLayer hardware;
    private OperatingSystem operatingSystem;

    public SystemMetrics(SystemInfo systemInfo, HardwareAbstractionLayer hardware, OperatingSystem operatingSystem) {
        super();
    }

    private void collect() {
        // TODO: Collect the latest metrics, transform to something smokable.
        this.systemInfo = systemInfo;
        this.hardware = hardware;
        this.operatingSystem = operatingSystem;
    }
}
