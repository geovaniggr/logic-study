package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Solver;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FirstProblemTest {

    private final Solver solver = new Solver("inputs");

    @Test
    @DisplayName("Should count correctly")
    void solve(){
        var elf = FirstProblem.execute(Stream.of(
    "1000",
            "2000",
            "3000",
            "",
            "4000",
            "",
            "5000",
            "6000",
            "",
            "7000",
            "8000",
            "9000",
            "",
            "10000"
        ));

        assertThat(elf.calories()).isEqualTo(24_000);
        assertThat(elf.resources()).hasSize(3);
    }

    @Test
    @DisplayName("Should count correctly the input")
    void solve_input(){
        var elf = solver.forProblem("problem-1", FirstProblem::solve);
        assertThat(elf.calories()).isEqualTo(68292);
    }


}