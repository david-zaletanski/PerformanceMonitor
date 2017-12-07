package net.dzale.performancedashboard.repository.metrics;

import net.dzale.performancedashboard.system.metrics.SystemMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dzale on 7/7/17.
 */
public interface SystemMetricsRepository extends JpaRepository<SystemMetrics, Long> {

    // Current Metrics
    SystemMetrics findFirstByOrderByCollectionTimestampDesc();

    // Historical Metrics (Last 5 Hours)
    List<SystemMetrics> findFirst5ByOrderByCollectionTimestampDesc();

}
