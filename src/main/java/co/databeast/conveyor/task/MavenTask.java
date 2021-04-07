package co.databeast.conveyor.task;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.cli.MavenCli;

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
    public Object run(Object input) throws TaskFailureException {
        File file = (File) input;
        String location = file.getAbsolutePath();
        MavenCli maven = new MavenCli();
        maven.doMain(new String[]{command}, location, System.out, System.out);
        return null;
    }

    public static MavenTask maven(String command) {
        return new MavenTask(command);
    }
}
