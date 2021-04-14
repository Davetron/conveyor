package co.databeast.conveyor.exceptions;

public class StageFailureException extends Exception {

    public StageFailureException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public StageFailureException(String errorMessage) {
        super(errorMessage);
    }
}
