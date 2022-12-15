package problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SixthProblem {
    static final class Frame {
        private static int INITIAL_INDEX = 0;
        private final List<Character> frame;
        private final HashSet<Character> validator = new HashSet<>();

        Frame(List<Character> frame) {
            this.frame = frame;
        }

        public Frame() {
            this(new ArrayList<>(4));
        }

        public void moveFrameToRight(char character) {
            if(!hasSpaceInFrame()){
                frame.remove(INITIAL_INDEX);
            }
            frame.add(character);
        }

        private boolean hasSpaceInFrame(){
            return frame.size() < 4;
        }

        private boolean hasEqualsCharactersInDataStream(){
            validator.clear();
            validator.addAll(frame);
            return validator.size() != frame.size();
        }

        public boolean consume(int codepoint) {
            var character = (char) codepoint;
            var hasCharacter = frame.contains(character);
            var hasCharacterOrSpace = hasCharacter || hasSpaceInFrame();

            moveFrameToRight(character);

            var shouldConsumeNext = hasCharacterOrSpace || hasEqualsCharactersInDataStream();

            return shouldConsumeNext;
        }

        public Integer findIndexInDataStream(IntStream stream) {
            return Math.toIntExact(
                stream.takeWhile(this::consume).count() + 1
            );
        }
    }

    public static List<Integer> solve(Stream<String> lines){
        return lines.map( line -> {
            var frame = new Frame();
            return frame.findIndexInDataStream(line.chars());
        })
        .toList();
    }

    public static void main(String[] args) {
        var index = solve(Stream.of(
                "bvwbjplbgvbhsrlpgdmjqwftvncz",
                "nppdvjthqldpwncqszvftbrmjlhg",
                "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
                "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
        ));
        System.out.println(index);
    }


}
