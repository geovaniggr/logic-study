package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Solver;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SecondProblemTest {

    private final Solver reader = new Solver("inputs");
    @Test
    @DisplayName("Should return correctly points")
    void solve() {
        var total = SecondProblem.execute(Stream.of(
    "A Y",
            "A Y",
            "B Z",
            "C X"
        ));
        assertEquals(32, total);
    }

    @Test
    @DisplayName("Should count correclty points for input")
    void solve_input(){
        var total = reader.forProblem("problem-2", SecondProblem::execute);
        assertEquals(9759, total);
    }
}