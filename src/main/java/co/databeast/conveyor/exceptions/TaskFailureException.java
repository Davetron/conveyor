package co.databeast.conveyor.exceptions;

public class TaskFailureException extends Exception {

    public TaskFailureException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public TaskFailureException(String errorMessage) {
        super(errorMessage);
    }
}
