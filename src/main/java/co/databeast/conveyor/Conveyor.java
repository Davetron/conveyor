package co.databeast.conveyor;

import co.databeast.conveyor.exceptions.StageFailureException;
import co.databeast.conveyor.stage.DefaultStage;
import co.databeast.conveyor.stage.Stage;
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
        for (Stage stage : stages) {
            try {
                stage.start();
            } catch (StageFailureException e) {
                log.error("Stage failure occurred", e);
            }
        }
    }

    public static void conveyor(String name, Stage... stages) {
        Conveyor conveyor = new Conveyor(name);
        Arrays.stream(stages).forEach(conveyor::addStage);
        conveyor.start();
    }

}
