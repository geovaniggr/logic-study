package problems;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

public class SecondProblem {
    private static final String PROBLEMS_FOLDER = "./problems/";

    record Tuple(GameAction opponent, GameAction user) {
        public Integer points(){
            return user.points(opponent);
        }
    }

    enum GameAction {
        ROCK(1) {
            @Override
            public Boolean won(GameAction other) {
                return SCISSOR.equals(other);
            }

            @Override
            public Boolean lose(GameAction other) {
                return PAPER.equals(other);
            }
        },
        PAPER(2) {
            @Override
            public Boolean won(GameAction other) {
                return ROCK.equals(other);
            }

            @Override
            public Boolean lose(GameAction other) {
                return SCISSOR.equals(other);
            }
        },
        SCISSOR(3) {
            @Override
            public Boolean won(GameAction other) {
                return PAPER.equals(other);
            }

            @Override
            public Boolean lose(GameAction other) {
                return ROCK.equals(other);
            }
        };
        private static Integer WON_POINTS = 6;
        private static Integer DRAW_POINTS  = 3;
        private Integer value;

        GameAction(Integer value) {
            this.value = value;
        }

        public abstract Boolean won(GameAction other);
        public abstract Boolean lose(GameAction other);

        public Integer points(GameAction opponent) {
            if (won(opponent)) return value + WON_POINTS;
            if (lose(opponent)) return value;

            return value + DRAW_POINTS;
        }

        public static Tuple from(String coddedOpponent, String coddedUser) {
            var opponent = switch (coddedOpponent) {
                case "A" -> ROCK;
                case "B" -> PAPER;
                case "C" -> SCISSOR;
                default -> null;
            };

            var user = switch (coddedUser) {
                case "X" -> ROCK;
                case "Y" -> PAPER;
                case "Z" -> SCISSOR;
                default -> null;
            };

            return new Tuple(opponent, user);
        }
    }

    public static Integer solve(Stream<String> lines){
        return lines.map(line -> {
                    var codded = line.split("\s+");
                    return GameAction.from(codded[0], codded[1]);
                })
                .mapToInt(Tuple::points)
                .sum();
    }

    public static Integer execute(Stream<String> input){
        return solve(input);
    }
    public static void main(String[] args) {
        try (
                var lines = lines(Path.of(PROBLEMS_FOLDER, "problem-number-2.txt").toAbsolutePath());
        ) {

            var total = solve(lines);

            System.out.println("O total de pontos obtido foi: %d".formatted(total));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
