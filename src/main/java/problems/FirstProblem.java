package problems;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.util.Comparator.comparingInt;

public class FirstProblem {
    record Elf(List<Integer> resources, Integer calories){}

    private static final String PROBLEMS_FOLDER = "./problems/";

    private static List<List<String>> initial(){
        List<List<String>> initial = new ArrayList<>();
        initial.add(new ArrayList<>());

        return initial;
    }

    private static Elf buildElfFromResource(List<String> resource) {
        var elements = resource.stream().mapToInt(str -> Integer.valueOf(str)).boxed().toList();
        return new Elf(
                elements,
                elements.stream().reduce(0, Integer::sum)
        );
    }

    private static List<List<String>> splitByEmptyLIne(List<List<String>> accumulator, String line) {
        if (line.isEmpty()) {
            accumulator
                    .add(new ArrayList<>());
        } else {
            accumulator
                    .get(accumulator.size() - 1)
                    .add(line);
        }

        return accumulator;
    }
    public static Elf solve(Stream<String> lines){
        return lines.reduce(
                        initial(),
                        FirstProblem::splitByEmptyLIne,
                        (first, second) -> new ArrayList<>() // ignore because it's a merge function that'll never happen
                )
                .stream()
                .map(FirstProblem::buildElfFromResource)
                .max(comparingInt(Elf::calories))
                .get();
    }

    public static void main(String[] args) {
        try(
                var lines = lines(Path.of(PROBLEMS_FOLDER, "problem-number-1.txt").toAbsolutePath())
        ) {
            solve(lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Elf execute(Stream<String> input){
        return solve(input);
    }
}
