package problems;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class FourthProblem {
    record DupleSection(Set<Integer> first, Set<Integer> second){
        public DupleSection(String firstSection, String secondSection){
            this(
                    parse(firstSection),
                    parse(secondSection)
            );
        }

        private static Set<Integer> parse(String sectionAsString){
            var section = Arrays
                    .stream(sectionAsString.split("-"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            return IntStream
                    .rangeClosed(section[0], section[1])
                    .boxed()
                    .collect(toSet());

        }

        private Boolean hasFullyOverlap(){
            return (first.size() >= second.size())
                ? first.containsAll(second)
                : second.containsAll(first);
        }
    }

    public static Integer count(Stream<String> lines){

        return Math.toIntExact(
                lines
                .map(line -> {
                    var sections = line.split(",");
                    return new DupleSection(sections[0], sections[1]);
                })
                .filter(DupleSection::hasFullyOverlap)
                .count()
        );
    }


    public static void main(String[] args) {
        var total = count(Stream.of(
                "2-4,6-8",
                "2-3,4-5",
                "5-7,7-9",
                "2-8,3-7",
                "6-6,4-6",
                "2-6,4-8"
        ));

        System.out.println(total);
    }
}
