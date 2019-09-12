package dedovich.sergey.scheduled.lock.scheduledlock.scheduled;

import dedovich.sergey.scheduled.lock.scheduledlock.model.Task;
import dedovich.sergey.scheduled.lock.scheduledlock.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduledTask {
    private static final String INSTANCE_ID = UUID.randomUUID().toString();

    private final TaskRepository taskRepository;

    @Scheduled(cron = "*/5 * * * * *")
    public void withoutLock() {
        log.info("Running task without lock, instance id: [{}]...", INSTANCE_ID);

        task();
    }

    /**
     * Do not forget to create shedlock table:
     *
     CREATE TABLE [dbo].[shedlock](
     [name] [varchar](64) NOT NULL,
     [lock_until] [datetime] NULL,
     [locked_at] [datetime] NULL,
     [locked_by] [varchar](255) NOT NULL,
     CONSTRAINT [PK_shedlock] PRIMARY KEY CLUSTERED
     (
     [name] ASC
     )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
     ) ON [PRIMARY]
     *
     */
    @Scheduled(cron = "*/5 * * * * *")
    @SchedulerLock(name = "scheduledTask", lockAtMostForString = "4000", lockAtLeastForString = "4000")
    public void withSchedulerLock() {
        log.info("Running task with scheduler lock, instance id: [{}]...", INSTANCE_ID);

        task();
    }

    private void task() {
        Task task = new Task();
        task.setInstanceId(INSTANCE_ID);

        taskRepository.save(task);
    }
}
