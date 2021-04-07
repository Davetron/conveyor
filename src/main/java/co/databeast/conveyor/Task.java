package co.databeast.conveyor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task {

    private final String name;

    public Task(String name) {
        this.name = name;
    }

    public void start() {
        log.info("starting Task {}", name);
    }

}
