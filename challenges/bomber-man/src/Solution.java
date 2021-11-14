import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'bomberMan' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. STRING_ARRAY grid
     */
    static final class GridPosition {
        private final int x;
        private final int y;

        GridPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static Stream<GridPosition> neighbours(int[][] area, GridPosition gp) {
        return Stream.of(
                        new GridPosition(gp.x, gp.y - 1),
                        new GridPosition(gp.x, gp.y + 1),
                        new GridPosition(gp.x - 1, gp.y),
                        new GridPosition(gp.x + 1, gp.y)
                )
                .filter(n -> n.y >= 0 && n.y < area.length)
                .filter(n -> n.x >= 0 && n.x < area[n.y].length);
    }

    public static Stream<GridPosition> entireGrid(int[][] area) {
        return IntStream.range(0, area.length)
                .mapToObj(y -> IntStream.range(0, area[y].length)
                        .mapToObj(x -> new GridPosition(x, y)))
                .flatMap(gp -> gp);
    }

    public static List<String> bomberMan(int n, List<String> grid) {
        int[][] area = grid.stream()
                .map(row -> row.chars().map(c -> c == '.' ? 0 : 3).toArray())
                .toArray(int[][]::new);

        for (int i = 1; i <= Math.min(n, 3 + ((n - 3) % 4)); i++) {
            // check for bombs that will explode this epoch and clean their non-exploding neighbourhood
            entireGrid(area)
                    .filter(gp -> area[gp.y][gp.x] == 1)
                    .forEach(gp -> {
                        neighbours(area, gp)
                                .filter(neighbour -> area[neighbour.y][neighbour.x] > 1)
                                .forEach(neighbour -> area[neighbour.y][neighbour.x] = 0);
                    });
            // count down
            entireGrid(area)
                    .filter(gp -> area[gp.y][gp.x] > 0)
                    .forEach(gp -> area[gp.y][gp.x]--);

            // bomber activation (in even epochs)
            if (i % 2 == 0) {
                entireGrid(area)
                        .filter(gp -> area[gp.y][gp.x] == 0)
                        .forEach(gp -> area[gp.y][gp.x] = 3);
            }
        }

        return Arrays.stream(area)
                .map(row -> Arrays.stream(row)
                        .mapToObj(i -> i == 0 ? "." : "O")
                        .collect(joining()))
                .collect(toList());
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r = Integer.parseInt(firstMultipleInput[0]);

        int c = Integer.parseInt(firstMultipleInput[1]);

        int n = Integer.parseInt(firstMultipleInput[2]);

        List<String> grid = IntStream.range(0, r).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        List<String> result = Result.bomberMan(n, grid);

        bufferedWriter.write(
                result.stream()
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
