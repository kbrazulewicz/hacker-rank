import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'cavityMap' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts STRING_ARRAY grid as parameter.
     */

    public static List<String> cavityMap(List<String> grid) {
        // Write your code here
        int size = grid.size();
        int[][] cavityMap = grid.stream().map(
                s -> s.chars().map(c -> c - '0').toArray()
        ).toArray(int[][]::new);

        for (int x = 1; x < size - 1; x++) {
            for (int y = 1; y < size - 1; y++) {
                int maxNeighbour =
                        Stream.of(
                                cavityMap[x - 1][y],
                                cavityMap[x + 1][y],
                                cavityMap[x][y - 1],
                                cavityMap[x][y + 1]
                        ).max(Comparator.naturalOrder()).get();

                if (maxNeighbour < cavityMap[x][y]) {
                    cavityMap[x][y] = Integer.MAX_VALUE;
                }
            }
        }

        Function<int[], String> conversionFunction = intArray -> Arrays.stream(intArray)
                .mapToObj(i -> i == Integer.MAX_VALUE ? "X" : String.valueOf(i))
                .collect(joining());

        return Arrays.stream(cavityMap).map(conversionFunction).collect(toList());
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> grid = IntStream.range(0, n).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        List<String> result = Result.cavityMap(grid);

        bufferedWriter.write(
                result.stream()
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}