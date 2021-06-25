package co.databeast.conveyor.task;

import co.databeast.conveyor.Manifest;
import co.databeast.conveyor.exceptions.TaskFailureException;

import java.io.File;

public interface Task {

    String name();

    default Object start(Manifest manifest, File workspace) throws TaskFailureException {
        System.out.println("Running an empty task!");
        return null;
    }

}
