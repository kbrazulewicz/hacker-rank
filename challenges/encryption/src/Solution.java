import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Result {

    /*
     * Complete the 'encryption' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String encryption(String s) {
        int sLength = s.length();
        final int columns = (int) Math.ceil(Math.sqrt(sLength));
        final int rows;

        if (columns * (columns - 1) < sLength) {
            rows = columns;
        } else {
            rows = columns - 1;
        }

        return IntStream.range(0, columns)
                .mapToObj(c -> IntStream.range(0, rows)
                        .map(r -> r * columns + c)
                        .filter(i -> i < sLength)
                        .mapToObj(s::charAt)
                        .collect(Collector.of(
                                StringBuilder::new,
                                StringBuilder::append,
                                StringBuilder::append,
                                StringBuilder::toString)))
                .collect(Collectors.joining(" "));
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        String result = Result.encryption(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
