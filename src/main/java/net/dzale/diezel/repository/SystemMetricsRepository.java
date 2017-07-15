package net.dzale.diezel.repository;

import net.dzale.diezel.system.SystemMetrics;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dzale on 7/7/17.
 */
public interface SystemMetricsRepository extends CrudRepository<SystemMetrics, Long> {

    SystemMetrics findFirstByOrderByCollectionTimestampDesc();

    List<SystemMetrics> findFirst5ByOrderByCollectionTimestampDesc();

}
