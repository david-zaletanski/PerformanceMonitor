package net.dzale.diezel.system.metrics;

import net.dzale.diezel.exceptions.DiezelException;
import net.dzale.diezel.service.metrics.SystemMetricsService;
import net.dzale.diezel.system.quartz.AutowiringSpringBeanJobFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;

/**
 * Creates a Quartz Scheduler for the system metrics collection job.
 *
 * @author dzale
 */
@Configuration
public class SystemMetricsCollectionConfig {
    private static final Logger log = LoggerFactory.getLogger(SystemMetricsCollectionConfig.class);

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job) throws DiezelException {
        try {
            StdSchedulerFactory factory = new StdSchedulerFactory();
            factory.initialize(new ClassPathResource("quartz.properties").getInputStream());

            Scheduler scheduler = factory.getScheduler();
            scheduler.setJobFactory(springBeanJobFactory());
            scheduler.scheduleJob(job, trigger);

            scheduler.start();
            return scheduler;
        } catch (IOException ex) {
            throw new DiezelException("Unable to find quartz.properties file in classpath.", ex);
        } catch (SchedulerException ex) {
            throw new DiezelException("Error creating scheduler.", ex);
        }
    }

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SystemMetricsCollectionJob.class);
        jobDetailFactory.setDescription("Collect system metrics every X ms job.");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(JobDetail job) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);

        trigger.setRepeatInterval(SystemMetricsService.COLLECT_INTERVAL_IN_MILLISECONDS);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return trigger;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
}
