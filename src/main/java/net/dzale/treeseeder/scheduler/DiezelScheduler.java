package net.dzale.treeseeder.scheduler;

import com.sun.istack.internal.Nullable;
import net.dzale.treeseeder.exceptions.ComponentType;
import net.dzale.treeseeder.exceptions.DiezelComponentException;
import net.dzale.treeseeder.exceptions.DiezelException;
import net.dzale.treeseeder.system.SystemMetricsCollectionJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.Type;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author dzale
 */
public class DiezelScheduler {
    private static final Logger log = LoggerFactory.getLogger(DiezelScheduler.class);

    private Scheduler scheduler;

    public DiezelScheduler() {
    }

    private void createScheduler() throws DiezelException {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            addDiezelJobs();
        } catch (SchedulerException ex) {
            throw new DiezelComponentException(ComponentType.SCHEDULER, "Could not create DiezelScheduler. Scheduled jobs will not be available.", ex);
        }
    }

    public void start() throws  DiezelException {
        try {
            if (scheduler == null)
                createScheduler();

            if (!scheduler.isStarted()) {
                scheduler.start();
            }

        } catch (SchedulerException ex) {
            throw new DiezelComponentException(ComponentType.SCHEDULER, "Unable to start scheduler.", ex);
        }
    }

    public void stop(boolean shutdown) throws DiezelException {
        try {
            if (scheduler == null)
                return;
            if (scheduler.isStarted() || scheduler.isInStandbyMode()) {
                if (shutdown)
                    scheduler.shutdown();
                else
                    scheduler.standby();
            }
        }
        catch (SchedulerException ex)
        {
            throw new DiezelComponentException(ComponentType.SCHEDULER, "Unable to stop scheduler.", ex);
        }
    }

    private void addDiezelJobs() throws DiezelException {
        try {
            scheduler.scheduleJob(createJobDetail(SystemMetricsCollectionJob.class, "SystemMetricsCollectionJob"),
                    createRepeatingTrigger("SystemMetricsCollectionJobTrigger", 60));
        } catch (SchedulerException ex) {
            throw new DiezelComponentException(ComponentType.SCHEDULER, "Unable to add metrics job to scheduler.", ex);
        }
    }

    public JobDetail createJobDetail(Class executingJob, String name) {
        return newJob(executingJob)
                .withIdentity(name)
                .build();
    }

    public Trigger createRepeatingTrigger(String name, int numSeconds) {
        return createRepeatingTrigger(name, null, numSeconds);
    }

    public Trigger createRepeatingTrigger(String name, @Nullable String group, int numSeconds) {
        return newTrigger()
                .withIdentity(name, group)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(numSeconds)
                        .repeatForever())
                .build();
    }
}
