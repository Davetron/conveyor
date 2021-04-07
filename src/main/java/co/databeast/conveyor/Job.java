
package co.databeast.conveyor;

import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Slf4j
public class Job {

    private String name;

    @Singular
    private List<Task> tasks;

    public void start() {
        log.info("starting {} job", name);
        tasks.forEach(Task::start);
    }
}
