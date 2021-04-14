package co.databeast.conveyor.stage;

import co.databeast.conveyor.exceptions.JobFailureException;
import co.databeast.conveyor.job.Job;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Slf4j
public class DefaultStage implements Stage {

    private final String name;
    private final List<Job> jobs = new ArrayList<>();

    @Override
    public String name() {
        return name;
    }

    public void addJob(Job job) {
        jobs.add(job);
    }

    public void start() {
        log.info("starting Stage {}", name);
        try {
            for (Job job : jobs) {
                job.start();
            }
        } catch (JobFailureException taskFailureException) {
            log.error("Uh oh, job failure!", taskFailureException);
        }
    }

    public static Stage stage(String name, Job... jobs) {
        Stage stage = new DefaultStage(name);
        Arrays.stream(jobs)
              .forEach(stage::addJob);
        return stage;
    }

}
