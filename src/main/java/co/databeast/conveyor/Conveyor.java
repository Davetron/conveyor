package co.databeast.conveyor;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Conveyor {

    private final String name;
    private final List<Stage> stages = new ArrayList<>();

    public Conveyor(String name) {
        this.name = name;
    }

    public Conveyor stage(Stage stage) {
        this.stages.add(stage);
        return this;
    }

    public void start() {
        log.info("starting Conveyor {}", name);
        stages.forEach(Stage::start);
    }

}
