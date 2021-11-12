import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

class Result {

    /*
     * Complete the 'absolutePermutation' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     */

    public static List<Integer> absolutePermutation(int n, int k) {
        // Write your code here
        if (k * 2 > n) {
            return List.of(-1);
        }

        int[] permutation = new int[n];
        boolean[] usedNumbers = new boolean[n + 1];

        for (int i = 1; i <= k; i++) {
            int candidate = i + k;
            if (!usedNumbers[candidate]) {
                permutation[i - 1] = candidate;
                usedNumbers[candidate] = true;
            } else {
                return List.of(-1);
            }
        }
        for (int i = n - k + 1; i <= n; i++) {
            int candidate = i - k;
            if (!usedNumbers[candidate]) {
                permutation[i - 1] = candidate;
                usedNumbers[candidate] = true;
            } else {
                return List.of(-1);
            }
        }
        for (int i = k + 1; i < n - k + 1; i++) {
            int candidate1 = i - k;
            int candidate2 = i + k;
            if (!usedNumbers[candidate1]) {
                permutation[i - 1] = candidate1;
                usedNumbers[candidate1] = true;
            } else if (!usedNumbers[candidate2]) {
                permutation[i - 1] = candidate2;
                usedNumbers[candidate2] = true;
            } else {
                return List.of(-1);
            }
        }

        return Arrays.stream(permutation)
                .boxed()
                .collect(Collectors.toList());
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int k = Integer.parseInt(firstMultipleInput[1]);

                List<Integer> result = Result.absolutePermutation(n, k);

                bufferedWriter.write(
                        result.stream()
                                .map(Object::toString)
                                .collect(joining(" "))
                                + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
