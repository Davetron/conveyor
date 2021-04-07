package co.databeast.conveyor;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class Task {

    private String name;

    private 

    public void start() {
        log.info("starting {} task", name);
        tasks.forEach(Task::start);
    }

}
