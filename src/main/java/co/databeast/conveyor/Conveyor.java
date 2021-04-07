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

    public Conveyor stage(Stage stage) {
        this.stages.add(stage);
        return this;
    }

    public void start() {
        log.info("starting Conveyor {}", name);
        stages.forEach(Stage::start);
    }

    public static Conveyor conveyor(String name, Stage... stages) {
        Conveyor conveyor = new Conveyor(name);
        Arrays.stream(stages).forEach(stage -> conveyor.stage(stage));
        return conveyor;
    }

}
