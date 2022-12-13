package problems;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FirstProblemTest {

    @Test
    @DisplayName("Should count correctly")
    void first_problem(){
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
}