package problems;

import org.junit.jupiter.api.Test;
import utils.Solver;

import static org.junit.jupiter.api.Assertions.*;

class FifthProblemTest {

    @Test
    void solve_for_input() {
        final var solver = new Solver("inputs");
        final var result = solver.forProblem("problem-5", FifthProblem::solve);

        assertEquals("ZWHVFWQWW", result);
    }
}