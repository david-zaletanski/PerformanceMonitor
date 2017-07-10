package net.dzale.treeseeder.service;

import net.dzale.treeseeder.exceptions.DiezelException;
import net.dzale.treeseeder.repository.SystemMetricsRepository;
import net.dzale.treeseeder.system.SystemMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;

import java.util.*;

/**
 * @author dzale
 */
@Service
public class SystemMetricsService {
    /**
     * How often to collect system metrics (how often collectAndPersistSystemMetrics is called) in ms.
     */
    public static final int COLLECT_INTERVAL_IN_MILLISECONDS = 1000;
    /**
     * Max number of cached SystemMetric data points.
     */
    static final int MAX_RECENT_QUEUE_SIZE = 60;
    static final long HOUR_IN_MILLISECONDS = 60 * 60 * 1000;
    /**
     * Duration in milliseconds between persisting collected SystemMetric data points.
     */
    static final long MIN_PERSISTENCE_INTERVAL = HOUR_IN_MILLISECONDS;
    private static final Logger log = LoggerFactory.getLogger(SystemMetricsService.class);
    @Autowired
    private SystemMetricsRepository systemMetricsRepository;

    /**
     * Stores the last MAX_RECENT_QUEUE_SIZE system metrics from oldest (index 0) to newest (index MAX_RECENT_QUEUE_SIZE-1).
     */
    private Queue<SystemMetrics> recentMetrics;

    public SystemMetricsService() {
        recentMetrics = new LinkedList<>();
    }

    /**
     * Called every minute by scheduled SystemMetricsCollectionJob.
     */
    public void collectAndPersistCurrentSystemMetrics() {
        // Get metrics and persist if needed.
        SystemMetrics newMetrics = getCurrentSystemMetrics();
        persistWhenLastPersistedGreaterThanInterval(newMetrics);

        // Add metrics to cache and clear old entities.
        if (!new Double(newMetrics.getCpuLoad()).equals(0.0)) // Prevents double-adding during startup
            recentMetrics.add(newMetrics);
        if (recentMetrics.size() > MAX_RECENT_QUEUE_SIZE) {
            recentMetrics.remove();
        }

    }

    private void persistWhenLastPersistedGreaterThanInterval(SystemMetrics newMetrics) {
        if (isLastPersistedCollectionTimestampGreaterThanInterval()) {
            log.info("Persisting current system metrics as time since last persisted is greater than " + HOUR_IN_MILLISECONDS + " ms.");
            log.info(newMetrics.toString());
            systemMetricsRepository.save(newMetrics);
        }
    }

    private boolean isLastPersistedCollectionTimestampGreaterThanInterval() {
        return (getTimeSinceLastPersistedCollectionTimestamp() >= MIN_PERSISTENCE_INTERVAL);
    }

    private long getTimeSinceLastPersistedCollectionTimestamp() {
        SystemMetrics lastMetrics = systemMetricsRepository.findFirstByOrderByCollectionTimestampDesc();
        return (lastMetrics == null ? MIN_PERSISTENCE_INTERVAL : new Date().getTime() - lastMetrics.getCollectionTimestamp().getTime());
    }

    private SystemMetrics getCurrentSystemMetrics() {
        SystemMetrics metrics = new SystemMetrics();
        metrics.setFromSystemInfo(new SystemInfo());
        return metrics;
    }

    public Queue<SystemMetrics> getRecentMetrics() {
        return recentMetrics;
    }

    public void setRecentMetrics(Queue<SystemMetrics> recentMetrics) {
        this.recentMetrics = recentMetrics;
    }

    public List<DataPoint2D> getRecentMetricsSystemCPULoadDataPoints() throws DiezelException {
        List<DataPoint2D> dataPoints = new ArrayList<>();
        for (SystemMetrics metric : recentMetrics) {
            dataPoints.add(new DataPoint2D(metric.getCollectionTimestamp().getTime(), metric.getCpuLoad() * 100));
        }
        return dataPoints;
    }

    public List<DataPoint4D> getRecentMetricsCPULoadDataPoints() throws DiezelException {
        List<DataPoint4D> dataPoints = new ArrayList<>();
        /*for (SystemMetrics metric : recentMetrics) {
            dataPoints.add(new DataPoint4D(metric.getCollectionTimestamp().getTime(),
                    metric.getCpuLoadAvg()[0],
                    metric.getCpuLoadAvg()[1],
                    metric.getCpuLoadAvg()[2]
            ));
        }*/
        SystemMetrics metric = recentMetrics.peek();
        dataPoints.add(new DataPoint4D(metric.getCollectionTimestamp().getTime(),
                metric.getCpuLoadAvg()[0],
                metric.getCpuLoadAvg()[1],
                metric.getCpuLoadAvg()[2]
        ));
        return dataPoints;
    }

    public int getNumVirtualCPU() {
        return recentMetrics.peek().getCpuLogicalCount();
    }

    public List<MorrisDonutDataPoint> getRecentFSDataPoints() throws DiezelException {
        List<MorrisDonutDataPoint> dataPoints = new ArrayList<>();
        SystemMetrics metric = recentMetrics.peek();
        dataPoints.add(new MorrisDonutDataPoint("Used", metric.getHdUsable()));
        dataPoints.add(new MorrisDonutDataPoint("Unused", metric.getHdTotal() - metric.getHdUsable()));
        return dataPoints;
    }

    public long getFSStorageTotal() {
        return recentMetrics.peek().getHdTotal();
    }

    public class DataPoint2D {
        public final long x;
        public final double y;

        public DataPoint2D(long x, double y) {
            this.x = x;
            this.y = y;
        }

        public long getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    public class DataPoint4D {
        public final long x;
        public final double a;
        public final double b;
        public final double c;

        public DataPoint4D(long x, double a, double b, double c) {
            this.x = x;
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public long getX() {
            return x;
        }

        public double getA() {
            return a;
        }

        public double getB() {
            return b;
        }

        public double getC() {
            return c;
        }
    }

    public class MorrisDonutDataPoint {
        public final String label;
        public final double value;

        public MorrisDonutDataPoint(String label, double value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public double getValue() {
            return value;
        }
    }
}
