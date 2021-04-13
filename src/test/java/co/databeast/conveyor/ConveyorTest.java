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
                                maven("clean install"),
                                dummyTask("upload build to artifactory")
                        ),
                        job("Configuration packaging",
                                dummyTask("get config"),
                                dummyTask("Upload config to artifactory")
                        )
                ),
                stage("Test",
                        job("Azure Acceptance Test",
                                dummyTask("deploy Azure instance"),
                                //gitCheckout("https://github.com/Davetron/sample_maven.git"),
                                //maven("test"), // TODO how should params be passed/shared between tasks?
                                dummyTask("destroy Azure instance")
                        ),
                        job("Openstack Acceptance Test",  // TODO how best to optionally trigger jobs or stages?
                                dummyTask("deploy Openstack instance"),
                                //gitCheckout("https://github.com/Davetron/sample_maven.git"),
                                //maven("test"),
                                dummyTask("destroy Openstack instance")
                        )
                )
        ).start();
    }

}
