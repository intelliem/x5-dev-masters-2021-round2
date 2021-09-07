import sets.SetPartitionGenerator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Условия
 * Дано множество абстрактных объектов. Для каждого объекта известно множество характеристик. Наборы характеристик у различных объектов могут частично или полностью совпадать. Необходимо выделить такие группы объектов, характеристики которых в значительной степени похожи друг на друга, и определить набор характеристик для каждой группы.
 * <p>
 * Наборы характеристик в каждой группе должны быть уникальными. Объект может быть отнесен только к одной группе. Таким образом группы не пересекаются ни характеристиками, ни объектами. Пустые группы недопустимы.
 * <p>
 * Мера сходства объекта с группой определяется как отношение K = I / U , где
 * <p>
 * I - количество характеристик в пересечении множества характеристик группы с множеством характеристик объекта
 * U - количество характеристик в объединении множества характеристик группы с множеством характеристик объекта
 * Разбиение на группы должно быть выполнено таким образом, чтобы сумма мер сходства всех объектов со "своей" группой была максимальной.
 * <p>
 * Рассмотрим пример
 * <p>
 * Входные данные (см. описание формата входных данных в отдельном разделе):
 * <p>
 * 4 2
 * 1 1 1
 * 2 1 2
 * 3 1 3
 * 4 2 1 3
 * <p>
 * Необходимо выделить 2 группы и провести кластеризацию 4 объектов. Множество всех возможных характеристик - { 1, 2, 3 } можно разбить на 2 группы тремя способами:
 * <p>
 * {1}, {2, 3}
 * {2}, {1, 3}
 * {3}, {1, 2}
 * Рассчитаем меру сходства с возможными группами для каждого объекта:
 * <p>
 * Для разбиения {1}, {2, 3}:
 * <p>
 * {1} -> {1}: K1 = 1 / 1 = 1.00;   {1} -> {2, 3}: K2 = 0 / 3 = 0.00
 * {2} -> {1}: K1 = 0 / 2 = 0.00;   {2} -> {2, 3}: K2 = 1 / 2 = 0.50
 * {3} -> {1}: K1 = 0 / 2 = 0.00;   {3} -> {2, 3}: K2 = 1 / 2 = 0.50
 * {1, 3} -> {1}: K1 = 1 / 2 = 0.50;   {1, 3} -> {2, 3}: K2 = 1 / 3 = 0.34
 * Исходя из меры сходства, 1-й и 4-й объекты относим к группе {1}, а 2-й и 3-й - к {2, 3}. Подсчитаем суммарную меру сходства:
 * <p>
 * S = (1.00 + 0.50) + (0.50 + 0.50) = 2.50
 * <p>
 * Опустив подробные расчеты, покажем суммарные меры сходства для остальных разбиений:
 * <p>
 * Для разбиения {2}, {1, 3}: S = 3.00
 * Для разбиения {3}, {1, 2}: S = 2.50
 * Окончательным ответом будет второе разбиение. Выводим список объектов и состав групп, к которым отнесены эти объекты (см. описание формата выходных данных в отдельном разделе):
 * <p>
 * 1 1 3
 * 2 2
 * 3 1 3
 * 4 1 3
 * <p>
 * <p>
 * Формат входных данных
 * Первая строка содержит два целых числа:
 * <p>
 * n - Количество объектов (от 1 до 100000)
 * k - Количество требуемых групп (кластеров) (от 2 до 1000)
 * На следующих n строках подается список объектов – каждый на отдельной строке. Строка начинается с двух параметров:
 * <p>
 * id – идентификатор объекта (целое число от 1 до 100000 включительно)
 * m – количество характеристик (целое, от 1 до 1000)
 * Далее следует список из m целочисленных (от 1 до 1000) идентификаторов характеристик через пробел
 * <p>
 * Формат выходных данных
 * Выход состоит из списка строк, каждая строка соответствует одному объекту и имеет формат:
 * <p>
 * id - идентификатор объекта
 * grp - Список идентификаторов характеристик группы, к которой отнесен этот объект
 * Сортировка строк должна быть выполнена по возрастанию идентификатора объекта.
 * <p>
 * Сортировка характеристик группы в каждой строке - во возрастанию идентификатора характеристики
 * <p>
 * Примеры
 * Входные данные:
 * 6 2
 * 1 4 1 2 3 4
 * 2 3 1 2 3
 * 3 5 1 2 3 4 5
 * 4 2 2 3
 * 5 3 3 4 5
 * 6 3 1 4 5
 * Выходные данные:
 * 1 1 2 3
 * 2 1 2 3
 * 3 1 2 3
 * 4 1 2 3
 * 5 4 5
 * 6 4 5
 * Входные данные:
 * 4 2
 * 1 1 1
 * 2 1 2
 * 3 1 3
 * 4 2 1 3
 * <p>
 * <p>
 * Выходные данные:
 * 1 1 3
 * 2 2
 * 3 1 3
 * 4 1 3
 */
public class TaskC {

    private static final Comparator<Map.Entry<Integer, Double>> COMPARE_BY_SIMILARITY_SUM = Map.Entry.comparingByValue();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] firstLine = br.readLine().split(" ");

        int objectsQuantity = Integer.parseInt(firstLine[0]);
        int groupQuantity = Integer.parseInt(firstLine[1]);
        List<AbstractObject> objects = new ArrayList<>();

        for (int i = 0; i < objectsQuantity; i++) {
            objects.add(new AbstractObject(br.readLine()));
        }
        br.close();

        Set<Integer> setOfCharacteristics = objects.stream()
                .flatMap(o -> o.getCharacteristics().stream())
                .collect(Collectors.toSet());

        final Map<Integer, List<Group>> partitionedGroups = partitionIntoGroupOfCharacteristics(groupQuantity, setOfCharacteristics);
//        partitionedGroups.forEach((method, groups) -> System.out.println("Method:" + method + " Groups: " + groups));

        Map<Integer, Double> partitionedSimilaritySums = new HashMap<>(partitionedGroups.size());
        Map<Integer, List<AbstractObjectSimilarity>> partitionedObjectsSimilarities = new HashMap<>(partitionedGroups.size());

        partitionedGroups.forEach((methodNumber, groups) -> {
            Map<AbstractObject, List<GroupSimilarity>> objectsWithGroupSimilarities = new HashMap<>();

            for (AbstractObject object : objects) {
                List<GroupSimilarity> groupSimilarities = new ArrayList<>();
                objectsWithGroupSimilarities.put(object, groupSimilarities);
                for (Group group : groups) {
                    double similarity = object.calculateSimilarityWithGroup(group);
                    groupSimilarities.add(new GroupSimilarity(group, similarity));
                }
            }

            final List<AbstractObjectSimilarity> objectsSimilarities = objectsWithGroupSimilarities.entrySet().stream()
                    .map(entry -> {
                        final GroupSimilarity maxGroupSimilarity = entry.getValue().stream()
                                .max(GroupSimilarity.COMPARE_BY_SIMILARITY).get();
                        return new AbstractObjectSimilarity(entry.getKey(), maxGroupSimilarity);
                    })
                    .collect(Collectors.toList());

            partitionedObjectsSimilarities.put(methodNumber, objectsSimilarities);
            partitionedSimilaritySums.put(methodNumber, objectsSimilarities.stream().map(o -> o.groupSimilarity.similarity).reduce(0D, Double::sum));
        });

        final Map.Entry<Integer, Double> partitionResult = partitionedSimilaritySums.entrySet().stream()
                .max(COMPARE_BY_SIMILARITY_SUM).get();

        final List<AbstractObjectSimilarity> objectSimilarities = partitionedObjectsSimilarities.get(partitionResult.getKey());
        objectSimilarities.forEach(AbstractObjectSimilarity::print);
    }

    private static Map<Integer, List<Group>> partitionIntoGroupOfCharacteristics(int groupQuantity, Set<Integer> setOfCharacteristics) {

        Map<Integer, List<Group>> groupOfCharacteristics = new HashMap<>();

        final SetPartitionGenerator<Integer> generator = new SetPartitionGenerator<>(setOfCharacteristics);

        int method = 1;
        for (List<List<Integer>> setOfGroup : generator) {
            if (setOfGroup.size() == groupQuantity) {
                final List<Group> groups = new ArrayList<>();
                groupOfCharacteristics.put(method, groups);

                for (List<Integer> characteristics : setOfGroup) {
                    groups.add(new Group(characteristics));
                }
                groups.sort(Group.COMPARE_BY_CHARACTERISTICS_SIZE);

                method++;
            }
        }

        return groupOfCharacteristics;
    }

    public static class AbstractObject {

        private final int id;
        private final List<Integer> characteristics;

        public AbstractObject(String rawInput) {
            List<Integer> values = Arrays.stream(rawInput.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            this.id = values.get(0);
            this.characteristics = values.subList(2, values.size());
        }

        public AbstractObject(int id, List<Integer> characteristics) {
            this.id = id;
            this.characteristics = characteristics;
        }

        public AbstractObject(int id, Integer... characteristics) {
            this.id = id;
            this.characteristics = List.of(characteristics);
        }

        public int getId() {
            return id;
        }

        public List<Integer> getCharacteristics() {
            return characteristics;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AbstractObject object = (AbstractObject) o;
            return id == object.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "AbstractObject{" +
                    "id=" + id +
                    ", characteristics=" + characteristics +
                    '}';
        }

        public double calculateSimilarityWithGroup(Group group) {
            double intersection = calculateQuantityOfCharacteristicsIntersection(group.getCharacteristics());
            double union = calculateQuantityOfCharacteristicsUnion(group.getCharacteristics());
            return intersection / union;
        }

        public double calculateSimilarityWithGroup(Integer characteristicOfGroup) {
            double intersection = calculateQuantityOfCharacteristicsIntersection(characteristicOfGroup);
            double union = calculateQuantityOfCharacteristicsUnion(characteristicOfGroup);
            return intersection / union;
        }

        public double calculateSimilarityWithGroup(Set<Integer> characteristicsOfGroup) {
            double intersection = calculateQuantityOfCharacteristicsIntersection(characteristicsOfGroup);
            double union = calculateQuantityOfCharacteristicsUnion(characteristicsOfGroup);
            return intersection / union;
        }

        private long calculateQuantityOfCharacteristicsIntersection(Collection<Integer> characteristicsOfGroup) {
            final Map<Integer, Long> intersection = characteristics.stream()
                    .filter(characteristicsOfGroup::contains)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            return intersection.values().size();
        }

        private long calculateQuantityOfCharacteristicsUnion(Collection<Integer> characteristicsOfGroup) {
            final ArrayList<Integer> characteristics = new ArrayList<>(this.characteristics);
            characteristics.addAll(characteristicsOfGroup);

            final Map<Integer, Long> union = characteristics.stream()
                    .distinct()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            return union.values().size();
        }

        public long calculateQuantityOfCharacteristicsIntersection(Integer characteristicOfGroup) {
            final Map<Integer, Long> intersection = characteristics.stream()
                    .filter(characteristicOfGroup::equals)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            return intersection.values().size();
        }

        public long calculateQuantityOfCharacteristicsUnion(Integer characteristicOfGroup) {
            final ArrayList<Integer> characteristics = new ArrayList<>(this.characteristics);
            characteristics.add(characteristicOfGroup);

            final Map<Integer, Long> union = characteristics.stream()
                    .distinct()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            return union.values().size();
        }


    }

    public static class AbstractObjectSimilarity {

        private final AbstractObject abstractObject;
        private final GroupSimilarity groupSimilarity;

        public AbstractObjectSimilarity(AbstractObject abstractObject, GroupSimilarity groupSimilarity) {
            this.abstractObject = abstractObject;
            this.groupSimilarity = groupSimilarity;
        }

        public void print() {
            System.out.printf("%s %s%n", abstractObject.getId(), groupSimilarity.printCharacteristics());
        }
    }

    public static class Group {

        public static Comparator<Group> COMPARE_BY_CHARACTERISTICS_SIZE = Comparator.comparing(group -> group.getCharacteristics().size());

        private final List<Integer> characteristics;

        public Group(List<Integer> characteristics) {
            this.characteristics = characteristics;
        }

        public List<Integer> getCharacteristics() {
            return characteristics;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "characteristics=" + characteristics +
                    '}';
        }
    }

    private static class GroupSimilarity {

        private static final Comparator<GroupSimilarity> COMPARE_BY_SIMILARITY = Comparator.comparing(GroupSimilarity::getSimilarity);

        private final Group group;
        private final double similarity;

        public GroupSimilarity(Group group, double similarity) {
            this.group = group;
            this.similarity = similarity;
        }

        public Group getGroup() {
            return group;
        }

        public double getSimilarity() {
            return similarity;
        }

        public String printCharacteristics() {
            return group.characteristics.stream().map(String::valueOf).collect(Collectors.joining(" "));
        }
    }
}
