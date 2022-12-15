package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Solver;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ThirdProblemTest {
    private final Solver reader = new Solver("inputs");

    @Test
    @DisplayName("Should count correctly the rucksack elements of test input")
    void solve() {

        var result = ThirdProblem.solve(
                Stream.of(
                        "vJrwpWtwJgWrhcsFMMfFFhFp",
                        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                        "PmmdzqPrVvPwwTWBwg",
                        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                        "ttgJtRGJQctTZtZT",
                        "CrZsJsPPZsGzwwsLwLmpwMDw"
                )
        );

        assertThat(result).isEqualTo(157);

    }

    @Test
    @DisplayName("Should count correclty the rucksack elements of input")
    void solve_input(){
        var result = reader.forProblem("problem-3", ThirdProblem::solve);
        assertThat(result).isEqualTo(7_917);
    }
}