import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
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

        Map<Integer, Set<Integer>> groups = splitByGroups(groupQuantity, setOfCharacteristics);
    }

    private static Map<Integer, Set<Integer>> splitByGroups(int groupQuantity, Set<Integer> setOfCharacteristics) {
        Map<Integer, Set<Integer>> groups = new HashMap<>();

        for (Integer characteristic : setOfCharacteristics) {
            groups.put(characteristic, setOfCharacteristics.stream().filter(v -> !v.equals(characteristic)).collect(Collectors.toSet()));
        }

        return groups;
    }

    public static class AbstractObject {

        private final int id;
        private final List<Integer> characteristics;

        public AbstractObject(String rawInput) {
            List<Integer> values = Arrays.stream(rawInput.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            this.id = values.get(0);
            this.characteristics = values.subList(2, values.size());
        }

        public int getId() {
            return id;
        }

        public List<Integer> getCharacteristics() {
            return characteristics;
        }

        @Override
        public String toString() {
            return "AbstractObject{" +
                    "id=" + id +
                    ", characteristics=" + characteristics +
                    '}';
        }
    }
}
