package co.databeast.conveyor;

import org.junit.Test;

import static co.databeast.conveyor.Conveyor.conveyor;
import static co.databeast.conveyor.task.DummyTask.dummyTask;
import static co.databeast.conveyor.Job.job;
import static co.databeast.conveyor.Stage.stage;
import static co.databeast.conveyor.task.GitTask.gitCheckout;

public class ConveyorTest {

    @Test
    public void dslExampleTest() {

        conveyor("Pipeline",
                stage("Build",
                        job("Job 1",
                                dummyTask("task 1"),
                                gitCheckout("https://github.com/Davetron/conveyor.git")
                        )
                )
        ).start();
    }

}
