import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TaskBV3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> weights = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            weights.add(scanner.nextInt());
        }
        scanner.close();

        Collections.sort(weights);

        int result = 0;

        for (int i = 0; i < weights.size(); i += 2) {
            int currentWeight = weights.get(i);
            int nextWeight = weights.get(i + 1);
            if (currentWeight != nextWeight)
                result += nextWeight - currentWeight;
        }

        System.out.println(result);
    }
}