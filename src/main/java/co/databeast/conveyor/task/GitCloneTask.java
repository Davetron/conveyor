package co.databeast.conveyor.task;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

import static java.lang.System.getenv;

@Data
@Slf4j
public class GitCloneTask implements Task {

    private String name = "Git Clone Task";
    private CloneCommand cloneCommand;
    private String repositoryURI;
    private String branch;

    public GitCloneTask(String repositoryURI, String branch) {
        this.repositoryURI = repositoryURI;
        this.branch = branch;
        this.cloneCommand = Git.cloneRepository()
                               .setURI(repositoryURI)
                               .setBranch(branch)
                               .setCredentialsProvider(getCredentials());
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object start(Object input, File workspace) throws TaskFailureException {
        try {
            log.info("Cloning {} ({} branch) into {}", repositoryURI, branch, workspace);
            cloneCommand.setDirectory(workspace);
            return cloneCommand.call();
        } catch (GitAPIException gitAPIException) {
            throw new TaskFailureException("Git exception while checking out repository", gitAPIException);
        }
    }

    public static GitCloneTask gitClone(String repositoryURI, String branch) {
        return new GitCloneTask(repositoryURI, branch);
    }

    public static GitCloneTask gitClone(String repositoryURI) {
        return new GitCloneTask(repositoryURI, "HEAD");
    }

    private CredentialsProvider getCredentials() {
        if (getenv("git.token") != null) {
            return new UsernamePasswordCredentialsProvider(getenv("git.token"), "");
        } else if (getenv("git.username") != null && getenv("git.password") != null) {
            return new UsernamePasswordCredentialsProvider(
                    getenv("git.username"),
                    getenv("git.password"));
        } else {
            log.warn("No git credentials set");
            return null;
        }
    }

}
