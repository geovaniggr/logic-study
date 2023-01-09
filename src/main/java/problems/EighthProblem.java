package problems;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.joining;

public class EighthProblem {

    record Grid(int[][] grid){
        public static Grid from(List<String> lines){
            return new Grid(lines
                .stream()
                .map(line -> line.chars().map(Character::getNumericValue).toArray())
                .toArray(int[][]::new)
            );
        }

        private Integer numberOfRows(){
            return grid.length;
        }

        private Integer numberOfColumns(){
            return grid[0].length;
        }

        private boolean isVisibleFromHorizontal(Integer height, Integer rowIndex, Integer columnIndex){
            IntPredicate isTaller = (index) -> height > grid[rowIndex][index];

            var isTallerThanLeftNeighborhood = IntStream
                    .range(0, columnIndex)
                    .allMatch(isTaller);

            var isTallerThanRightNeighborhood = IntStream
                    .range(columnIndex + 1, numberOfColumns())
                    .allMatch(isTaller);

            return isTallerThanLeftNeighborhood || isTallerThanRightNeighborhood;
        }

        private boolean isVisibleFromVertical(Integer height, Integer rowIndex, Integer columnIndex){

            IntPredicate isTaller = (index) -> height > grid[index][columnIndex];

            var isTallerThanTopNeighborhood = IntStream
                    .range(0, rowIndex)
                    .allMatch(isTaller);

            var isTallerThanBottomNeighborhood = IntStream
                    .range(rowIndex + 1, numberOfRows())
                    .allMatch(isTaller);

            return isTallerThanTopNeighborhood || isTallerThanBottomNeighborhood;
        }

        private boolean isVisible(Integer height, Integer rowIndex, int columnIndex){
            return isVisibleFromHorizontal(height, rowIndex, columnIndex) || isVisibleFromVertical(height, rowIndex, columnIndex);
        }

        public List<List<Long>> findHighestScenicScore(){
            return IntStream
            .range(1, numberOfRows() -1)
            .mapToObj(rowIndex -> {
                return IntStream
                        .range(1, numberOfColumns() - 1)
                        .mapToObj(columnIndex -> {
                           final var tree = grid[rowIndex][columnIndex];
                           return calculateScenicScore(tree, rowIndex, columnIndex);
                        })
                        .toList();
            })
            .toList();
        }

        private Integer clamp(Integer value){
            var floor = 0;
            var ceil = numberOfColumns();

            if(value < floor) return floor;
            if(value > ceil) return ceil;

            return value;
        }

        private long calculateScenicScore(int tree, int rowIndex, int columnIndex) {
            return 0L;
        }

        public Integer findVisibleTreeFromGrid(){
            var innerVisibleTrees = IntStream.range(1, numberOfRows() - 1)
                .map(rowIndex -> {
                    return toIntExact(IntStream.range(1, numberOfColumns() - 1)
                            .filter(columnIndex -> {
                                final var tree = grid[rowIndex][columnIndex];
                                return isVisible(tree, rowIndex, columnIndex);
                            })
                            .count());
                })
                .sum();

            var borderTrees = (numberOfRows() * 2 ) + (numberOfColumns() * 2) - 4;

            return toIntExact(borderTrees + innerVisibleTrees);
        }

        @Override
        public String toString() {
            return Arrays
                    .stream(grid)
                    .map(row -> Arrays.stream(row).mapToObj(String::valueOf).collect(joining(" ")))
                    .collect(joining("\n"));
        }
    }

    public static Integer solve(Stream<String> lines){
        var grid = Grid.from(lines.toList());
        var highestScenicScore = grid.findHighestScenicScore();
        System.out.println(highestScenicScore);
        return 0;
    }

    public static void main(String[] args) {
        solve(Stream.of(
        "30373",
                "25512",
                "65332",
                "33549",
                "35390"
        ));
    }
}
