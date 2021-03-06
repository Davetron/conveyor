package co.databeast.conveyor.stage;

import co.databeast.conveyor.Manifest;
import co.databeast.conveyor.exceptions.StageFailureException;
import co.databeast.conveyor.job.Job;

public interface Stage {

    String name();

    void addJob(Job job);

    default void start(Manifest manifest) throws StageFailureException {
        System.out.println("Running an empty Stage!");
    }

}
