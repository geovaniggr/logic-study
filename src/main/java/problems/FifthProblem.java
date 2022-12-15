package problems;

import utils.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;

public class FifthProblem {

    record ProblemDetails(Integer numberOfStacks, List<String> lines, List<String> moves) {
        static ProblemDetails from(List<String> lines) {
            var divisor = IntStream.range(0, lines.size()).filter(index -> lines.get(index).isEmpty()).findFirst().getAsInt();

            var crates = lines.subList(0, divisor - 1); // don't get the stack index line
            var moves = lines.subList(divisor + 1, lines.size()); // don't get the empty line
            var numberOfStacks = Character.getNumericValue(
                    lines
                            .get(divisor - 1) // get the stack index line
                            .chars()
                            .filter(Character::isDigit)
                            .max()
                            .getAsInt()
            );

            return new ProblemDetails(numberOfStacks, crates, moves);
        }
    }

    record Crate(Character character) {
        public static List<Crate> from(String line) {
            var numberOfStacks = (line.length() / 4) + 1;
            return IntStream
                    .iterate(1, index -> index + 4) // there's "]_[" elements that we need to pass
                    .limit(numberOfStacks)
                    .map(line::charAt)
                    .mapToObj(codepoint -> new Crate((char) codepoint))
                    .toList();
        }

        public Boolean isEmpty() {
            return character == null || Character.isSpaceChar(character);
        }
    }

    record CargoCrane(List<ArrayList<Crate>> stacks) {
        static CargoCrane from(List<String> lines, Integer numberOfStacks) {
            // TODO: Possible refactor
            // on create crates already put in the stack
            var crates = lines.stream().map(Crate::from).toList();

            var stacks = IntStream
                    .range(0, numberOfStacks)
                    .mapToObj(__ -> new ArrayList<Crate>())
                    .toList();

            IntStream
                    .range(0, crates.size())
                    .forEach(stackIndex ->
                            IntStream
                                    .range(0, crates.get(stackIndex).size())
                                    .forEach(crateIndex -> {
                                        var crate = crates.get(stackIndex).get(crateIndex);
                                        if (!crate.isEmpty()) {
                                            stacks.get(crateIndex).add(crate);
                                        }
                                    })
                    );

            return new CargoCrane(stacks);
        }

        public void moveCrate(Integer fromIndex, Integer toIndex){
            var from = stacks.get(fromIndex);
            var to = stacks.get(toIndex);
            var element = from.remove(0);
            to.add(0, element);
        }

        public void executeAnAction(Action action){
            IntStream
                .range(0, action.times())
                .forEach(time -> moveCrate(action.from() - 1, action.to() - 1));
        }
        public String cranesOnTop(){
            return stacks
                .stream()
                .filter(not(ArrayList::isEmpty))
                .map(stack -> stack.get(0))
                .map(crate -> crate.character().toString())
                .collect(joining(""));
        }


    }

    record Action(Integer times, Integer from, Integer to) {
        // could be (\\d+){3}
        private static final Pattern PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

        public Action(MatchResult result) {
            this(
                    parseInt(result.group(1)),
                    parseInt(result.group(2)),
                    parseInt(result.group(3))
            );
        }

        public static Action from(String line) {
            return PATTERN
                    .matcher(line)
                    .results()
                    .findFirst()
                    .map(Action::new)
                    .orElse(null);
        }
    }

    public static String solve(Stream<String> stream) {
        var lines = stream.toList();
        var problem = ProblemDetails.from(lines);
        var cargo = CargoCrane.from(problem.lines(), problem.numberOfStacks());

        problem
                .moves()
                .stream()
                .map(Action::from)
                .forEach(cargo::executeAnAction);

        return cargo.cranesOnTop();
    }

    public static void main(String[] args) {
        // need to refactor this exercise...
    }


}
