package co.databeast.conveyor;

import org.junit.Test;

public class ConveyorTest {

    @Test
    public void test() {
        new Conveyor("pipeline1")
                .stage(new Stage("Build")
                        .job(new Job("Build")
                                .task(new Task("Git checkout"))))
                .start();
    }

}
