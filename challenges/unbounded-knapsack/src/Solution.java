import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.lang.Integer.max;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'unboundedKnapsack' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER k
     *  2. INTEGER_ARRAY arr
     */

    public static int unboundedKnapsack(int k, List<Integer> arr) {
        int currentSum = 0;
        int closestSum = 0;

        final TreeSet<Integer> sortedValues = new TreeSet<>(arr);
        final Deque<Integer> knapsack = new LinkedList<>();

        // largest item
        int currentValue = sortedValues.last();

        while (currentSum != k) {
            knapsack.add(currentValue);
            currentSum += currentValue;
            if (currentSum <= k) {
                closestSum = max(currentSum, closestSum);
            } else {
                Integer newCurrentValue = null;
                while (newCurrentValue == null && !knapsack.isEmpty()) {
                    int lastKnapsackValue = knapsack.removeLast();
                    currentSum -= lastKnapsackValue;
                    newCurrentValue = sortedValues.lower(lastKnapsackValue);
                }
                if (newCurrentValue != null) {
                    currentValue = newCurrentValue;
                } else break;
            }
        }

        return closestSum;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());
        while (t-- != 0) {
            String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            int n = Integer.parseInt(firstMultipleInput[0]);

            int k = Integer.parseInt(firstMultipleInput[1]);

            List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

            int result = Result.unboundedKnapsack(k, arr);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
