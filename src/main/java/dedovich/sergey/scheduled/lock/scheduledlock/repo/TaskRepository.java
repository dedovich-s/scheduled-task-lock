package dedovich.sergey.scheduled.lock.scheduledlock.repo;

import dedovich.sergey.scheduled.lock.scheduledlock.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
