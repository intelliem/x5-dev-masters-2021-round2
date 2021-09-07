import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractObjectTest {

    @Test
    public void calculateIntersectionAndUnion1() {
        TaskC.AbstractObject abstractObject = new TaskC.AbstractObject(1, 1);
        final long intersection = abstractObject.calculateQuantityOfCharacteristicsIntersection(1);
        final long unit = abstractObject.calculateQuantityOfCharacteristicsUnion(1);

        assertEquals(1, intersection);
        assertEquals(1, unit);
    }

    @Test
    public void calculateIntersectionAndUnion2() {
        TaskC.AbstractObject abstractObject = new TaskC.AbstractObject(4, 1, 3);
        final long intersection = abstractObject.calculateQuantityOfCharacteristicsIntersection(1);
        final long unit = abstractObject.calculateQuantityOfCharacteristicsUnion(1);

        assertEquals(1, intersection);
        assertEquals(2, unit);
    }
}
