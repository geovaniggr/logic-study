package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class FirstProblemTest {

    @Test
    @DisplayName("Should count correctly")
    void first_problem(){
        var elf = FirstProblem.execute("""
        1000
        2000
        3000
            
        4000
            
        5000
        6000
            
        7000
        8000
        9000
            
        10000
        """);

        assertEquals(24_000, elf.calories());
        assertEquals(3, elf.resources().size());
    }
}