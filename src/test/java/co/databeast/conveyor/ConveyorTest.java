package co.databeast.conveyor;

import org.junit.Test;

import static co.databeast.conveyor.Conveyor.conveyor;
import static co.databeast.conveyor.task.DummyTask.dummyTask;
import static co.databeast.conveyor.Job.job;
import static co.databeast.conveyor.Stage.stage;
import static co.databeast.conveyor.task.GitTask.gitCheckout;
import static co.databeast.conveyor.task.MavenTask.maven;

public class ConveyorTest {

    @Test
    public void dslExampleTest() {

        conveyor("Pipeline",
                stage("Build",
                        job("Application Build",
                                dummyTask("task 1"),
                                gitCheckout("https://github.com/Davetron/sample_maven.git"),
                                maven("clean install")
                        )
                )
        ).start();
    }

}
