
package co.databeast.conveyor.job;

import co.databeast.conveyor.Manifest;
import co.databeast.conveyor.exceptions.JobFailureException;
import co.databeast.conveyor.exceptions.TaskFailureException;
import co.databeast.conveyor.task.Task;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.getenv;

@Data
@Slf4j
public class DefaultJob implements Job {

    private final String name;
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public String name() {
        return null;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void start(Manifest manifest) throws JobFailureException {
        log.info("Starting {} job ({})", name, manifest.getBuildIdentifier());

        File workspace;
        try {
            workspace = createWorkspace(manifest.getBuildIdentifier(), name);
        } catch (IOException ioException) {
            throw new JobFailureException("IO Exception while creating local workspace", ioException);
        }

        runTasks(workspace, manifest);
        if (getenv("conveyor.workspace.preserve") == null) {
            try {
                log.debug("Deleting workspace {}", workspace);
                FileUtils.deleteDirectory(workspace);
                log.debug("Completed deleting workspace {}", workspace);
            } catch (IOException e) {
                throw new JobFailureException("Unable to delete workspace", e);
            }
        } else {
            log.debug("Preserving workspace {}", workspace);
        }
    }

    private File createWorkspace(String buildIdentifier, String jobName) throws IOException {
        File workspace = Files.createTempDirectory(buildIdentifier + "_" + jobName)
                              .toFile();
        log.debug("Created job workspace {}", workspace);
        return workspace;
    }

    private void runTasks(File workspace, Manifest manifest) {
        for (Task task : tasks) {
            try {
                task.start(manifest, workspace);
            } catch (TaskFailureException taskFailureException) {
                log.error("Uh oh, task failure!", taskFailureException);
            }
        }
    }

    public static Job job(String name, Task... tasks) {
        Job job = new DefaultJob(name);
        Arrays.stream(tasks)
              .forEach(job::addTask);
        return job;
    }
}
