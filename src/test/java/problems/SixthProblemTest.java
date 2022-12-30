package problems;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Solver;

class SixthProblemTest {

    @Test
    @DisplayName("[Part-1]: Should return the correctly index for input")
    void solve() {
        final var solver = new Solver("inputs");
        final var result = solver.forProblem("problem-6", SixthProblem::solve);

        Assertions.assertThat(result).first().isEqualTo(1343);
    }

    @Test
    @DisplayName("[Part-2]: Should return the correctly index for input")
    void solve_for_part_2() {
        final var solver = new Solver("inputs");
        final var result = solver.forProblem("problem-6", problem -> SixthProblem.solve(problem, 14));

        Assertions.assertThat(result).first().isEqualTo(2193);
    }
}