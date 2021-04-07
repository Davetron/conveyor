package co.databeast.conveyor;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Stage {

    private final String name;
    private final List<Job> jobs = new ArrayList<>();

    public Stage(String name) {
        this.name = name;
    }

    public Stage job(Job job) {
        jobs.add(job);
        return this;
    }

    public void start() {
        log.info("starting Stage {}", name);
        jobs.forEach(Job::start);
    }

}
