
package co.databeast.conveyor.job;

import co.databeast.conveyor.exceptions.JobFailureException;
import co.databeast.conveyor.task.Task;

public interface Job {

    String name();

    void addTask(Task task);

    default void start() throws JobFailureException {
        System.out.println("Running an empty job!");
    }
}
