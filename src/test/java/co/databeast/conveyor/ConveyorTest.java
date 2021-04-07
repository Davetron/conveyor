package co.databeast.conveyor;

import org.junit.Test;
import co.databeast.conveyor.ImmutableConveyorBelt;

import static co.databeast.conveyor.Conveyor.conveyor;
import static co.databeast.conveyor.Job.job;
import static co.databeast.conveyor.Stage.stage;
import static co.databeast.conveyor.Task.task;

public class ConveyorTest {

    @Test
    public void test() {
        new Conveyor("pipeline1")
                .stage(new Stage("Build")
                        .job(new Job("Build")
                                .task(new Task("Git checkout"))
                                .task(new Task("Maven build"))
                                .task(new Task("Upload artefact"))
                        )
                )
                .stage(new Stage("Test")
                        .job(new Job("Azure Acceptance Test")
                                .task(new Task("Launch Azure Instance"))
                                .task(new Task("Run Acceptance Tests"))
                        )
                        .job(new Job("AWS Acceptance Test")
                                .task(new Task("Launch AWS Instance"))
                                .task(new Task("Run Acceptance Tests"))
                        )
                )
                .start();
    }

    @Test
    public void immutableTest() {
        ImmutableConveyorBelt.builder()
                             .name("Pipeline")
                             .addStage(new Stage("Stage 1")
                                     .job(new Job("job1")
                                             .task(new Task("get the source"))))
                             .build()
                             .start();
    }


    @Test
    public void asd() {
        conveyor("Pipeline",
                stage("Stage 1",
                        job("Job 1",
                                task("task 1"),
                                task("task 2"),
                                task("task 3")
                        )
                )
        ).start();
    }

}
