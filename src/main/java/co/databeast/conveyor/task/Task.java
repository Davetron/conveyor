package co.databeast.conveyor.task;

import java.io.File;

public interface Task {

    String name();

    default Object start(Object input, File workspace) throws TaskFailureException {
        System.out.println("Running an empty task!");
        return null;
    }

}
