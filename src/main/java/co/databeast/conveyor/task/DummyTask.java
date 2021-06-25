package co.databeast.conveyor.task;

import co.databeast.conveyor.Manifest;
import co.databeast.conveyor.exceptions.TaskFailureException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Data
@Slf4j
public class DummyTask implements Task {

    private final String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object start(Manifest manifest, File workspace) throws TaskFailureException {
        log.info("Running dummy task {}", name);
        return null;
    }

    public static DummyTask dummyTask(String name) {
        return new DummyTask(name);
    }
}
