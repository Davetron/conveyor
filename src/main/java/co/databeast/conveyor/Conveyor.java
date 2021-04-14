package co.databeast.conveyor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Slf4j
public class Conveyor {

    private final String name;
    private final List<Stage> stages = new ArrayList<>();

    public void addStage(Stage stage) {
        this.stages.add(stage);
    }

    public void start() {
        log.info("starting Conveyor {}", name);
        stages.forEach(Stage::start);
    }

    public static void conveyor(String name, Stage... stages) {
        Conveyor conveyor = new Conveyor(name);
        Arrays.stream(stages).forEach(conveyor::addStage);
        conveyor.start();
    }

}
