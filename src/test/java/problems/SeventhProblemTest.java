package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Solver;

import static org.assertj.core.api.Assertions.assertThat;

class SeventhProblemTest {

    @Test
    @DisplayName("[Part-1]: Should return the correctly size of folders")
    void solve() {
        final var solver = new Solver("inputs");
        final var walker = solver.forProblem("problem-7", SeventhProblem::solve);

        assertThat(walker.filterFolderAtLeastSize(100_000L)).isEqualTo(1543140L);
    }

    @Test
    @DisplayName("[Part-2]: Should return the best folder to remove")
    void solve_for_part_2() {
        final var solver = new Solver("inputs");
        final var walker = solver.forProblem("problem-7", SeventhProblem::solve);

        assertThat(walker.findBestDirectoryToRemove()).extracting(SeventhProblem.Directory::size).isEqualTo(1_117_448);
    }

}