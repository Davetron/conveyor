
package co.databeast.conveyor;

import co.databeast.conveyor.task.Task;
import co.databeast.conveyor.task.TaskFailureException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Slf4j
public class Job {

    private final String name;
    private final List<Task> tasks = new ArrayList<>();

    public Job task(Task task) {
        tasks.add(task);
        return this;
    }

    public void start() {
        log.info("starting {} job", name);
        Object result = null;
        for (Task task : tasks) {
            try {
                result = task.run(result);
            } catch (TaskFailureException taskFailureException) {
                log.error("Uh oh, task failure alert!", taskFailureException);
            }
        }
    }

    public static Job job(String name, Task... tasks) {
        Job job = new Job(name);
        Arrays.stream(tasks).forEach(task -> job.task(task));
        return job;
    }
}
