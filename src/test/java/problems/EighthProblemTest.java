package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Solver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EighthProblemTest {

    @Test
    @DisplayName("[Part-1]: Should return the correctly size of folders")
    void solve() {
        final var solver = new Solver("inputs");
        final var numberOfTrees = solver.forProblem("problem-8", EighthProblem::solve);
        System.out.println(numberOfTrees);
//        assertThat(walker.filterFolderAtLeastSize(100_000L)).isEqualTo(1543140L);
    }

}