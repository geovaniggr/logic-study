package problems;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ThirdProblemTest {

    @Test
    @DisplayName("Should count correctly the rucksack elements")
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
}