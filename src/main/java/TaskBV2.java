import java.util.*;
import java.util.stream.Collectors;

/**
 * Условия
 * На складе лежат n коробок с яблоками (четное число).
 *
 * Кладовщик хочет сформировать n/2 паллет. Каждая паллета должна состоять ровно из двух коробок. Две коробки могут устойчиво лежать на паллете только тогда, когда их вес одинаков.
 *
 * В коробки можно докладывать яблоки, каждое из которых увеличивает вес коробки на единицу.
 *
 * Кладовщик хочет знать, какое минимальное количество яблок необходимо доложить в коробки, чтобы сформировать ровно n/2 паллет (то есть каждая пара коробок должна лежать на паллете). Ваша задача — найти это количество.
 *
 * Формат входных данных
 * Первая строка входных данных содержит одно целое число n (2 ≤ n ≤ 100) — количество коробок. Гарантируется, что n всегда является четным числом.
 *
 * Вторая строка входных данных содержит n целых чисел a1, a2, …, an (1 ≤ a[i] ≤ 100), где a[i] равно весу i-й коробки.
 *
 * Формат выходных данных
 * Выведите одно целое число — минимальное количество яблок, которое необходимо доложить в коробки, чтобы сформировать ровно n/2 паллет.
 *
 * Примеры
 * Входные данные:
 * 6
 * 5 10 2 3 14 5
 *
 *
 * Выходные данные:
 * 5
 *
 */
public class TaskBV2 {

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
        if (boxesGropedByWeight.entrySet().stream()
                .allMatch(entry -> entry.getValue().size() == 2)) {
            System.out.println(0);
        }

        Set<Integer> weightsToDelete = boxesGropedByWeight.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        for (Integer weightToDelete : weightsToDelete) {
            boxesGropedByWeight.remove(weightToDelete);
        }

        List<Integer> uniqueWeights = new ArrayList<>(boxesGropedByWeight.keySet());

        int result = 0;

        for (int i = 0; i < uniqueWeights.size(); i += 2) {
            Integer firstWeight = uniqueWeights.get(i);
            Integer secondWeight = uniqueWeights.get(i + 1);
            result += secondWeight - firstWeight;
        }

        System.out.println(result);
    }
}