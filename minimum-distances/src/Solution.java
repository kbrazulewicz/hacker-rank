import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'minimumDistances' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY a as parameter.
     */

    public static int MAX_I = 100000;

    public static int minimumDistances(List<Integer> a) {
        // Write your code here
        int minimumDistance = Integer.MAX_VALUE;
        int[] lastPosition = new int[MAX_I];

        int position = 1;
        for (int ai : a) {
            if (lastPosition[ai] != 0) {
                minimumDistance = Math.min(minimumDistance, position - lastPosition[ai]);
            }
            lastPosition[ai] = position;
            position++;
        }

        return minimumDistance == Integer.MAX_VALUE ? -1 : minimumDistance;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        int result = Result.minimumDistances(a);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
