package co.databeast.conveyor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Slf4j
public class Stage {

    private final String name;
    private final List<Job> jobs = new ArrayList<>();

    public Stage job(Job job) {
        jobs.add(job);
        return this;
    }

    public void start() {
        log.info("starting Stage {}", name);
        jobs.forEach(Job::start);
    }

    public static Stage stage(String name, Job... jobs) {
        Stage stage = new Stage(name);
        Arrays.stream(jobs).forEach(job -> stage.job(job));
        return stage;
    }

}
