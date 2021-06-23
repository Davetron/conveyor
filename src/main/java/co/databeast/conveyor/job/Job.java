
package co.databeast.conveyor.job;

import co.databeast.conveyor.exceptions.JobFailureException;
import co.databeast.conveyor.task.Task;

public interface Job {

    String name();

    void addTask(Task task);

    default void start(String buildIdentifier) throws JobFailureException {
        System.out.println("Running an empty job!");
    }
}
