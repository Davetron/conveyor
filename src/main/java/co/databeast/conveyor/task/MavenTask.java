package co.databeast.conveyor.task;

import co.databeast.conveyor.exceptions.TaskFailureException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Data
@Slf4j
public class MavenTask implements Task {

    private final String command;

    @Override
    public String name() {
        return null;
    }

    @Override
    public Object start(String buildIdentifier, File workspace) throws TaskFailureException {

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

            Process process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor()
                     .submit(streamGobbler);
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

    private static class StreamGobbler implements Runnable {
        private final InputStream inputStream;
        private final Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                                                                  .forEach(consumer);
        }
    }
}
