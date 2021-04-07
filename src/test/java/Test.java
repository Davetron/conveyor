import co.databeast.conveyor.Conveyor;
import org.eclipse.jgit.api.Git;

public class Test {

    public static void main(String[] args) {

        Conveyor.builder()
                .name("pipeline1")
                .git(new Git())
    }
}
