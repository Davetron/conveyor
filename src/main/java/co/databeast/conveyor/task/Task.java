package co.databeast.conveyor.task;

public interface Task {

    String name();

    default Object run(Object input) throws TaskFailureException {
        System.out.println("Running an empty task!");
        return null;
    }

}
