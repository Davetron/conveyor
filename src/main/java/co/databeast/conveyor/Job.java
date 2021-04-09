
package co.databeast.conveyor;

import co.databeast.conveyor.task.Task;
import co.databeast.conveyor.task.TaskFailureException;
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
public class Job {

    private final String name;
    private final List<Task> tasks = new ArrayList<>();

    public Job task(Task task) {
        tasks.add(task);
        return this;
    }

    public void start() throws TaskFailureException {
        log.info("Starting {} job", name);

        File workspace;
        try {
            workspace = createWorkspace(name);
        } catch (IOException ioException) {
            throw new TaskFailureException("IO Exception while creating local workspace", ioException);
        }

        try {
            runTasks(workspace);
        } finally {
            log.debug("Deleting workspace {}", workspace);
            workspace.deleteOnExit();
        }
    }

    private File createWorkspace(String jobName) throws IOException {
        File workspace = File.createTempFile("conveyor_job_" + jobName, "");
        Files.delete(workspace.toPath());
        log.debug("Created job workspace {}", workspace);
        return workspace;
    }

    private void runTasks(File workspace) {
        Object result = null;
        for (Task task : tasks) {
            try {
                result = task.start(result, workspace);
            } catch (TaskFailureException taskFailureException) {
                log.error("Uh oh, task failure!", taskFailureException);
            }
        }
    }

    public static Job job(String name, Task... tasks) {
        Job job = new Job(name);
        Arrays.stream(tasks)
              .forEach(job::task);
        return job;
    }
}
