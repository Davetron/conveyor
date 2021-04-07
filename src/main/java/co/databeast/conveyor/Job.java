
package co.databeast.conveyor;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Job {

    private final String name;
    private final List<Task> tasks = new ArrayList<>();

    public Job(String name) {
        this.name = name;
    }

    public Job task(Task task) {
        tasks.add(task);
        return this;
    }

    public void start() {
        log.info("starting {} job", name);
        tasks.forEach(Task::start);
    }
}
