package co.databeast.conveyor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Task {

    private final String name;

    public void start() {
        log.info("starting Task {}", name);
    }

    public static Task task(String name) {
        return new Task(name);
    }

}
