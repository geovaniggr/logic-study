package problems;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Solver;

import static org.junit.jupiter.api.Assertions.*;

class SixthProblemTest {

    @Test
    @DisplayName("Should return the correctly index for input")
    void solve() {
        final var solver = new Solver("inputs");
        final var result = solver.forProblem("problem-6", SixthProblem::solve);

        Assertions.assertThat(result).first().isEqualTo(1343);
    }
}