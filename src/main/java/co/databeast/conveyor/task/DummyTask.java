package co.databeast.conveyor.task;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class DummyTask implements Task {

    private final String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object run(Object input) throws TaskFailureException {
        log.info("Running dummy task");
        return null;
    }

    public static DummyTask dummyTask(String name) {
        return new DummyTask(name);
    }
}
