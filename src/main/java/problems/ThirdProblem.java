package problems;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

public class ThirdProblem {
    private static final String PROBLEMS_FOLDER = "./problems/";

    record Rucksack(Set<Character> first, Set<Character> second){
        private static Integer UPPERCASE_OFFSET = 26;
        public Rucksack(String word){
            this(
                stringToSet(word.substring(0, word.length() /2 )),
                stringToSet(word.substring(word.length() / 2))
            );
        }

        private static Set<Character> stringToSet(String substring){
            return substring
                    .chars()
                    .mapToObj( value -> (char) value ) // it's not possible create a stream using .toCharArray()
                    .collect(Collectors.toSet());
        }

        private Set<Character> intersection(){
            final var intersection = new HashSet<>(first);
            intersection.retainAll(second);

            return intersection;
        }
    }

    private static Integer calculateDuplicateFromRucksack(Set<Character> intersection){
        return intersection
                .stream()
                .mapToInt( character -> Character.isUpperCase(character)
                            ? Rucksack.UPPERCASE_OFFSET + (character % 32)
                            : character % 32
                )
                .sum();
    }
    
    public static Integer solve(Stream<String> lines){
        return lines
                .map(Rucksack::new)
                .map(Rucksack::intersection)
                .mapToInt(ThirdProblem::calculateDuplicateFromRucksack)
                .sum();
    }

    public static void main(String[] args) {

        try (
            var lines = lines(Path.of(PROBLEMS_FOLDER, "problem-number-3.txt").toAbsolutePath())
        ) {

            var total = solve(lines);

            System.out.println(total);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer execute(Stream<String> input){
        return solve(input);
    }
}
