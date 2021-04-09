package co.databeast.conveyor.task;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.cli.MavenCli;

import java.io.File;

@Data
@Slf4j
public class MavenTask implements Task {

    private MavenCli maven = new MavenCli();
    private final String command;

    @Override
    public String name() {
        return null;
    }

    @Override
    public Object start(Object input, File workspace) throws TaskFailureException {
        String location = workspace.getAbsolutePath();
        System.setProperty("maven.multiModuleProjectDirectory", location);
        maven.doMain(tokeniseCommand(command), location, System.out, System.out);
        return null;
    }

    public static MavenTask maven(String command) {
        return new MavenTask(command);
    }

    private String[] tokeniseCommand(String command) {
        return command.split("\\s+");
    }
}
