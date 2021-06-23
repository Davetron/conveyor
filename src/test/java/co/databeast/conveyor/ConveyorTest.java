package co.databeast.conveyor;

import co.databeast.conveyor.job.DefaultJob;
import co.databeast.conveyor.job.Job;
import co.databeast.conveyor.stage.DefaultStage;
import co.databeast.conveyor.task.GitCloneTask;
import co.databeast.conveyor.task.MavenTask;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.junit.Test;

import static co.databeast.conveyor.Conveyor.conveyor;
import static co.databeast.conveyor.job.DefaultJob.job;
import static co.databeast.conveyor.stage.DefaultStage.stage;
import static co.databeast.conveyor.task.DummyTask.dummyTask;
import static co.databeast.conveyor.task.GitCloneTask.gitClone;
import static co.databeast.conveyor.task.MavenTask.maven;

public class ConveyorTest {

    public static final String REPOSITORY_URI = "https://github.com/Davetron/sample_maven.git";

    @Test
    public void dslExampleTest() {
        conveyor("Pipeline",
                stage("Build",
                        job("Application Build",
                                gitClone(REPOSITORY_URI),
                                maven("clean deploy")
                        ),
                        job("Configuration packaging",
                                dummyTask("get config"),
                                dummyTask("Upload config to artifactory")
                        )
                ),
                stage("Test",
                        job("Azure Acceptance Test",
                                dummyTask("deploy Azure instance"),
                                //maven("test"), // TODO how should params be passed/shared between tasks?
                                dummyTask("destroy Azure instance")
                        ),
                        job("Openstack Acceptance Test",  // TODO how best to optionally trigger jobs or stages?
                                dummyTask("deploy Openstack instance"),
                                //maven("test"),
                                dummyTask("destroy Openstack instance")
                        )
                )
        ).start();


    }

    @Test
    public void fullFatTest() {
        Conveyor conveyor = new Conveyor("Full fat Pipeline");
        DefaultStage defaultStage = new DefaultStage("Full fat Build");
        Job job = new DefaultJob("Full fat Application build");
        job.addTask(new GitCloneTask(REPOSITORY_URI, "HEAD"));
        job.addTask(new MavenTask("clean install"));
        defaultStage.addJob(job);
        conveyor.addStage(defaultStage);
        conveyor.start();
    }

    @Test
    public void mixedUsage() {

        // Do some detailed task setup to be passed into the conveyor dsl call
        GitCloneTask gitCloneTask = new GitCloneTask(REPOSITORY_URI, "HEAD");
        gitCloneTask.getCloneCommand().setTimeout(10000);
        gitCloneTask.getCloneCommand().setProgressMonitor(new TextProgressMonitor());

        conveyor("Pipeline",
                stage("Build",
                        job("Application Build",
                                dummyTask("task 1"),
                                gitCloneTask,
                                maven("clean install"),
                                dummyTask("upload build to artifactory")
                        )
                )
        ).start();

    }

}
