package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecondProblemTest {
    @Test
    @DisplayName("Should return correctly points")
    void first() {
        var total = SecondProblem.execute("""
        A Y
        A Y
        B Z
        C X
        """);

        assertEquals(32, total);
    }
}