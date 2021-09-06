import java.util.*;
import java.util.stream.Collectors;

public class TaskBV1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }
        scanner.close();

        Map<Integer, List<Integer>> boxesGropedByWeight = Arrays.stream(weights)
                .boxed()
                .collect(Collectors.groupingBy(w -> w));

        int result = 0;

        TreeSet<Integer> uniqueWeights = new TreeSet<>(boxesGropedByWeight.keySet());

        for (Integer uniqueWeight : uniqueWeights) {
            List<Integer> pallet1 = boxesGropedByWeight.get(uniqueWeight);
            if (pallet1.size() != 2) {
                Integer nextUniqueWeight = uniqueWeights.higher(uniqueWeight);
                if (nextUniqueWeight == null)
                    break;
                List<Integer> pallet2 = boxesGropedByWeight.get(nextUniqueWeight);
                if (pallet2.size() != 2) {
                    result += nextUniqueWeight - uniqueWeight;
                }
            }
        }

        System.out.println(result);
    }
}