package co.databeast.conveyor;

import co.databeast.conveyor.exceptions.StageFailureException;
import co.databeast.conveyor.stage.Stage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Slf4j
public class Conveyor {

    private final String name;
    private final List<Stage> stages = new ArrayList<>();

    public void addStage(Stage stage) {
        this.stages.add(stage);
    }

    public void start() {
        this.start(null);
    }

    public void start(String buildIdentifier) {
        if (StringUtils.isEmpty(buildIdentifier)) {
            buildIdentifier = UUID.randomUUID().toString();
        }
        log.info("starting Conveyor {} ({})", name, buildIdentifier);
        for (Stage stage : stages) {
            try {
                stage.start(buildIdentifier);
            } catch (StageFailureException e) {
                log.error("Stage failure occurred", e);
            }
        }
    }

    public static Conveyor conveyor(String name, Stage... stages) {
        Conveyor conveyor = new Conveyor(name);
        Arrays.stream(stages)
              .forEach(conveyor::addStage);
        return conveyor;
    }

}
