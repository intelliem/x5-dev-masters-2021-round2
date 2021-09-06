import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Условия
 * Вам дан испорченный XML-документ, в котором потеряна часть символов (например, пробелы, кавычки). При этом известно, что в для каждого тега документа верно следующее:
 *
 * если у него есть вложенные теги, то первый из них - не испорчен
 * все вложенные на одном уровне теги всегда относятся к одному типу данных
 * каждый тег имеет уникальный атрибут id
 * Кроме того, известно что корневой тег не испорчен.
 *
 * Вам необходимо восстановить из этого документа полезные данные с сохранением их структуры. Полезными данными считается значение атрибута value каждого тега, у которого есть этот атрибут, и его удалось восстановить.
 *
 * Формат входных данных
 * На вход (stdin) поступает XML файл.
 *
 * Пример испорченного файла:
 *
 * <?xml version="1.0" encoding="utf-8"?>
 * <root>
 *   <group id="1" name="A">
 *     <item name="a1" id="2" value="a 1"/>
 *     <itemname=a2id=3value=a2/>
 *     <itemname=a3id=4value=a3/>
 *   </group>
 *   <groupid=5name=B>
 *     <group id="6" name="B1">
 *         <file id="7" value="b11"/>
 *         <fileid=8value=b12/>
 *     </group>
 *   </group>
 * </root>
 *
 * Формат выходных данных
 * На выход нужно выдать восстановленные данные в формате
 *
 * parent_id - id родительского узла в дереве тегов
 * id - идентификатор тега
 * value - полезные данные (может отсутствовать)
 * Пример вывода для приведенного выше файла:
 *
 * 0 1
 * 1 2 a 1
 * 1 3 a2
 * 1 4 a3
 * 0 5
 * 5 6
 * 6 7 b11
 * 6 8 b12
 */
public class TaskD {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> xmlLines = new ArrayList<>();
        String xmlLine;
        while ((xmlLine = br.readLine()) != null) {
            xmlLines.add(xmlLine);
        }
        br.close();

        System.out.println(xmlLines);
    }

    private static final class Data {

        /**
         * id родительского узла в дереве тего
         */
        private final int parentId;
        /**
         * идентификатор тега
         */
        private final int id;

        private final String value;

        public Data(int parentId, int id, String value) {
            this.parentId = parentId;
            this.id = id;
            this.value = value;
        }
    }
}
