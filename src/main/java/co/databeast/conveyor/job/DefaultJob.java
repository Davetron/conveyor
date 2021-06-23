
package co.databeast.conveyor.job;

import co.databeast.conveyor.exceptions.JobFailureException;
import co.databeast.conveyor.exceptions.TaskFailureException;
import co.databeast.conveyor.task.Task;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Slf4j
public class DefaultJob implements Job{

    private final String name;
    private String buildIdentifier;
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public String name() {
        return null;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void start(String buildIdentifier) throws JobFailureException {
        log.info("Starting {} job ({})", name, buildIdentifier);
        this.buildIdentifier = buildIdentifier;

        File workspace;
        try {
            workspace = createWorkspace(buildIdentifier, name);
        } catch (IOException ioException) {
            throw new JobFailureException("IO Exception while creating local workspace", ioException);
        }

        try {
            runTasks(workspace);
        } finally {
            log.debug("Deleting workspace {}", workspace);
            workspace.deleteOnExit();
        }
    }

    private File createWorkspace(String buildIdentifier, String jobName) throws IOException {
        File workspace = Files.createTempDirectory(buildIdentifier + "_" + jobName).toFile();
        log.debug("Created job workspace {}", workspace);
        return workspace;
    }

    private void runTasks(File workspace) {
        for (Task task : tasks) {
            try {
                task.start(buildIdentifier, workspace);
            } catch (TaskFailureException taskFailureException) {
                log.error("Uh oh, task failure!", taskFailureException);
            }
        }
    }

    public static Job job(String name, Task... tasks) {
        Job job = new DefaultJob(name);
        Arrays.stream(tasks).forEach(job::addTask);
        return job;
    }
}
