package co.databeast.conveyor;

import lombok.Builder;
import lombok.Singular;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Builder
public class Conveyor {

    private static Logger log = LoggerFactory.getLogger(Conveyor.class);

    // Give your pipeline a name
    private String name;

    // Give it some jobs to do
    @Singular
    private List<Stage> stages;

    public void start() {
        log.info("starting {} pipeline", name);
        stages.forEach(Stage::start);
    }

}
