package commons;

import commons.primaryKeys.DebtKey;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Constructor;

public class DebtKeyTest {

    @Test
    public void testConstructorAndAccessors() {
        long eventId = 1L;
        long id = 10L;

        DebtKey debtKey = new DebtKey(eventId, id);

        assertAll("Constructor should set all properties",
                () -> assertEquals(eventId, debtKey.getEventId(), "Correct eventId"),
                () -> assertEquals(id, debtKey.getId(), "Correct id")
        );
    }

    @Test
    public void testProtectedConstructor() throws Exception {
        Constructor<DebtKey> constructor = DebtKey.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        DebtKey debtKey = constructor.newInstance();

        assertAll("Protected constructor - default properties",
                () -> assertEquals(0L, debtKey.getEventId(), "Default eventId"),
                () -> assertEquals(0L, debtKey.getId(), "Default id")
        );
    }

    @Test
    public void testSetters() {
        long eventId = 1L;
        long id = 10L;

        DebtKey debtKey = new DebtKey(eventId, id);

        debtKey.setEventId(eventId);
        debtKey.setId(id);

        assertAll("Setters should set the properties",
                () -> assertEquals(eventId, debtKey.getEventId(), "Set eventId"),
                () -> assertEquals(id, debtKey.getId(), "Set id")
        );
    }

    @Test
    public void testEqualsSameReference() {
        DebtKey debtKey1 = new DebtKey(1L, 10L);
        DebtKey debtKey2 = debtKey1;

        assertEquals(debtKey1, debtKey2, "equals should return true for same reference");
    }

    @Test
    public void testEqualsSameValues() {
        DebtKey debtKey1 = new DebtKey(1L, 10L);
        DebtKey debtKey2 = new DebtKey(1L, 10L);

        assertEquals(debtKey1, debtKey2, "equals should return true for same values");
    }

    @Test
    public void testEqualsDifferentValues() {
        DebtKey debtKey1 = new DebtKey(1L, 10L);
        DebtKey debtKey2 = new DebtKey(2L, 20L);

        assertNotEquals(debtKey1, debtKey2, "equals should return false for different values");
    }

    @Test
    public void testEqualsNull() {
        DebtKey debtKey = new DebtKey(1L, 10L);

        assertNotEquals(null, debtKey, "equals should return false for null");
    }

    @Test
    public void testEqualsDifferentClass() {
        DebtKey debtKey = new DebtKey(1L, 10L);
        String notADebtKey = "not a DebtKey";

        assertNotEquals(debtKey, notADebtKey, "equals should return false for different classes");
    }

    @Test
    public void testHashCodeConsistency() {
        DebtKey debtKey = new DebtKey(1L, 10L);
        int hashCode1 = debtKey.hashCode();
        int hashCode2 = debtKey.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testHashCodeEquality() {
        DebtKey debtKey1 = new DebtKey(1L, 10L);
        DebtKey debtKey2 = new DebtKey(1L, 10L);

        assertEquals(debtKey1.hashCode(), debtKey2.hashCode());
    }
}
