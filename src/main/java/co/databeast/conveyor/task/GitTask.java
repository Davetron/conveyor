package co.databeast.conveyor.task;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

import static java.lang.System.getenv;

@Data
@Slf4j
public class GitTask implements Task {

    private String name = "Git Task";
    private final String repositoryURI;
    private final String branch;

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object start(Object input, File workspace) throws TaskFailureException {
        try {
            log.info("Cloning {} ({} branch) into {}", repositoryURI, branch, workspace);
            return Git.cloneRepository()
               .setURI(repositoryURI)
               .setDirectory(workspace)
               .setCredentialsProvider(getCredentials())
               .call();
        } catch (GitAPIException gitAPIException) {
            throw new TaskFailureException("Git exception while checking out repository", gitAPIException);
        }
    }

    public static GitTask gitCheckout(String repositoryURI, String branch) {
        return new GitTask(repositoryURI, branch);
    }

    public static GitTask gitCheckout(String repositoryURI) {
        return gitCheckout(repositoryURI, "main");
    }

    private CredentialsProvider getCredentials() {
        if (getenv("git.token") != null) {
            return new UsernamePasswordCredentialsProvider(getenv("git.token"), "");
        } else if (getenv("git.username") != null && getenv("git.password") != null) {
            return new UsernamePasswordCredentialsProvider(
                    getenv("git.username"),
                    getenv("git.password"));
        } else {
            throw new RuntimeException("No git credential properties set.");
        }
    }

}
