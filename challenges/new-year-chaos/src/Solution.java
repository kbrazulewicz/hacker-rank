import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {
    public static int minimumBribes(List<Integer> q) {
        int totalBribes = 0;
        int position = 1;
        int max = 0;
        Set<Integer> set = new HashSet<>();

        for (int number : q) {
            int offset = number - position;
            if (offset > 2) {
                return -1;
            } else {
                IntStream.range(max + 1, number).forEach(set::add);
                set.remove(number);
                totalBribes += set.stream().filter(s -> s < number).count();
                max = Math.max(max, number);
            }
            position++;
        }

        return totalBribes;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {

//        assert  3 == Result.minimumBribes(Arrays.asList(2, 1, 5, 3, 4));
//        assert -1 == Result.minimumBribes(Arrays.asList(2, 5, 1, 3, 4));
//
//        assert -1 == Result.minimumBribes(Arrays.asList(5,1,2,3,7,8,6,4));
//        assert  7 == Result.minimumBribes(Arrays.asList(1,2,5,3,7,8,6,4));
//
//        assert  4 == Result.minimumBribes(Arrays.asList(1,2,5,3,4,7,8,6));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> q = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList());

                int r = Result.minimumBribes(q);
                System.out.println(r < 0 ? "Too chaotic" : r);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
    }
}
