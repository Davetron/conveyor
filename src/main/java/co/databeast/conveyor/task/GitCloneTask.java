package co.databeast.conveyor.task;

import co.databeast.conveyor.Manifest;
import co.databeast.conveyor.exceptions.TaskFailureException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

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
    public Object start(Manifest manifest, File workspace) throws TaskFailureException {
        Git git;
        try {
            log.info("Cloning {} ({} branch) into {}", repositoryURI, branch, workspace);
            cloneCommand.setDirectory(workspace);
            git = cloneCommand.call();
            manifest.getComponentVersions().put(repositoryURI, getCommitHash(git, branch));
            git.close();
            return git;
        } catch (GitAPIException | IOException exception) {
            throw new TaskFailureException("Git exception while checking out repository", exception);
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

    private String getCommitHash(Git git, String ref) throws IOException {
        Repository repository = git.getRepository();
        Ref head = repository.exactRef(ref);
        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(head.getObjectId());
        return commit.getId().getName();
    }

}
