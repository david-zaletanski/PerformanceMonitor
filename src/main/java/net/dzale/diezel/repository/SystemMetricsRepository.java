package net.dzale.diezel.repository;

import net.dzale.diezel.system.SystemMetrics;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dzale on 7/7/17.
 */
public interface SystemMetricsRepository extends CrudRepository<SystemMetrics, Long> {

    // Current Metrics
    SystemMetrics findFirstByOrderByCollectionTimestampDesc();

    // Historical Metrics (Last 5 Hours)
    List<SystemMetrics> findFirst5ByOrderByCollectionTimestampDesc();

}
