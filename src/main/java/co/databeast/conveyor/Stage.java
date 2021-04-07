package co.databeast.conveyor;

import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Slf4j
public class Stage {

    private String name;

    @Singular
    private List<Job> jobs;

    public void start() {
        log.info("starting {} stage", name);
        jobs.forEach(Job::start);
    }

}
