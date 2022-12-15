package problems;

import org.junit.jupiter.api.Test;
import utils.Solver;

import static org.junit.jupiter.api.Assertions.*;

class FourthProblemTest {

    private final Solver reader = new Solver("inputs");
    @Test
    void solve() {
        var result = reader.forProblem("problem-4", FourthProblem::solve);

        assertEquals(567, result);

    }
}