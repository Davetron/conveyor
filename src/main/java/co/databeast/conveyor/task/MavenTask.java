package co.databeast.conveyor.task;

import co.databeast.conveyor.Manifest;
import co.databeast.conveyor.exceptions.TaskFailureException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Data
@Slf4j
public class MavenTask implements Task {

    private final String command;

    @Override
    public String name() {
        return null;
    }

    @Override
    public Object start(Manifest manifest, File workspace) throws TaskFailureException {

        String location = workspace.getAbsolutePath();
        log.info("Starting MavenTask [{}] in {}", command, location);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(new File(location));
            if (isWindows()) {
                builder.command("cmd.exe", "/c", "mvnw.cmd " + command);
            } else {
                builder.command("sh", "-c", "mvnw " + command);
            }

            Process process = builder.inheritIO().start();
            int exitCode = process.waitFor();
            assert exitCode == 0;
        } catch (Exception e) {
            throw new TaskFailureException("Exception occurred while executing Maven task", e);
        }

        return null;
    }

    public static MavenTask maven(String command) {
        return new MavenTask(command);
    }

    private boolean isWindows() {
        return System.getProperty("os.name")
                     .toLowerCase()
                     .startsWith("windows");
    }

}
