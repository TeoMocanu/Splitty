package commons;

import commons.primaryKeys.ExpenseKey;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Constructor;

public class ExpenseKeyTest {

    @Test
    public void testParameterizedConstructor() {
        long eventId = 1L;
        long id = 10L;

        ExpenseKey expenseKey = new ExpenseKey(eventId, id);

        assertAll("Constructor should set all properties",
                () -> assertEquals(eventId, expenseKey.getEventId(), "Correct eventId"),
                () -> assertEquals(id, expenseKey.getId(), "Correct id")
        );
    }

    @Test
    public void testProtectedConstructor() throws Exception {
        Constructor<ExpenseKey> constructor = ExpenseKey.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        ExpenseKey expenseKey = constructor.newInstance();

        assertAll("Protected constructor - default properties",
                () -> assertEquals(0L, expenseKey.getEventId(), "Default eventId"),
                () -> assertEquals(0L, expenseKey.getId(), "Default id")
        );
    }

    @Test
    public void testSetEventId() {
        ExpenseKey expenseKey = new ExpenseKey(1L, 10L);
        expenseKey.setEventId(100L);
        assertEquals(100L, expenseKey.getEventId(), "Correct eventId");
    }

    @Test
    public void testSetId() {
        ExpenseKey expenseKey = new ExpenseKey(1L, 10L);
        expenseKey.setId(100L);
        assertEquals(100L, expenseKey.getId(), "Correct Id");
    }

    @Test
    public void testEquals() {
        ExpenseKey key1 = new ExpenseKey(100L, 200L);
        ExpenseKey key2 = new ExpenseKey(100L, 200L);
        ExpenseKey key3 = new ExpenseKey(101L, 201L);

        assertEquals(key1, key2);
        assertNotEquals(key1, key3);
        assertNotEquals(null, key1);
        assertNotEquals(key1, new Object());
    }

    @Test
    public void testHashCode() {
        ExpenseKey key1 = new ExpenseKey(100L, 200L);
        ExpenseKey key2 = new ExpenseKey(100L, 200L);
        ExpenseKey key3 = new ExpenseKey(101L, 201L);

        assertEquals(key1.hashCode(), key2.hashCode());
        assertNotEquals(key1.hashCode(), key3.hashCode());
    }
}
