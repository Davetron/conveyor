package co.databeast.conveyor.exceptions;

public class JobFailureException extends Exception {

    public JobFailureException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public JobFailureException(String errorMessage) {
        super(errorMessage);
    }
}
