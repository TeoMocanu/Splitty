package commons;

import commons.primaryKeys.ParticipantKey;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import static org.junit.jupiter.api.Assertions.*;

public class ParticipantKeyTest {

    @Test
    public void testParameterizedConstructor() {
        long eventId = 10L;
        long id = 20L;

        ParticipantKey participantKey = new ParticipantKey(eventId, id);

        assertAll("Constructor should set all properties",
                () -> assertEquals(eventId, participantKey.getEventId(), "Correct eventId"),
                () -> assertEquals(id, participantKey.getId(), "Correct id")
        );
    }

    @Test
    public void testProtectedConstructor() throws Exception {
        Constructor<ParticipantKey> constructor = ParticipantKey.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        ParticipantKey participantKey = constructor.newInstance();

        assertAll("Protected constructor - default properties",
                () -> assertEquals(0L, participantKey.getEventId(), "Default eventId"),
                () -> assertEquals(0L, participantKey.getId(), "Default id")
        );
    }

    @Test
    public void testSetEventId() {
        ParticipantKey participantKey = new ParticipantKey(10L, 20L);
        participantKey.setEventId(300L);
        assertEquals(300L, participantKey.getEventId(), "Correct eventId");
    }

    @Test
    public void testSetId() {
        ParticipantKey participantKey = new ParticipantKey(10L, 20L);
        participantKey.setId(300L);
        assertEquals(300L, participantKey.getId(), "Correct id");
    }

    @Test
    public void testEqualsSameReference() {
        ParticipantKey participantKey1 = new ParticipantKey(10L, 20L);
        ParticipantKey participantKey2 = participantKey1;

        assertEquals(participantKey1, participantKey2, "Equals should return true for the same reference");
    }

    @Test
    public void testEqualsSameValues() {
        ParticipantKey participantKey1 = new ParticipantKey(10L, 20L);
        ParticipantKey participantKey2 = new ParticipantKey(10L, 20L);

        assertEquals(participantKey1, participantKey2, "Equals should return true for objects with the same eventId and id");
    }

    @Test
    public void testEqualsDifferentValues() {
        ParticipantKey participantKey1 = new ParticipantKey(10L, 20L);
        ParticipantKey participantKey2 = new ParticipantKey(10L, 21L);

        assertNotEquals(participantKey1, participantKey2, "Equals should return false for objects with different eventId or id");
    }

    @Test
    public void testEqualsNull() {
        ParticipantKey participantKey = new ParticipantKey(10L, 20L);

        assertNotEquals(null, participantKey, "Equals should return false when compared with null");
    }

    @Test
    public void testEqualsDifferentClass() {
        ParticipantKey participantKey = new ParticipantKey(10L, 20L);
        String notAParticipantKey = "not a ParticipantKey";

        assertNotEquals(participantKey, notAParticipantKey, "Equals should return false when compared with a different type");
    }

    @Test
    public void testHashCodeConsistency() {
        ParticipantKey participantKey = new ParticipantKey(10L, 20L);
        int hashCode1 = participantKey.hashCode();
        int hashCode2 = participantKey.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testHashCodeEquality() {
        ParticipantKey participantKey1 = new ParticipantKey(10L, 20L);
        ParticipantKey participantKey2 = new ParticipantKey(10L, 20L);

        assertEquals(participantKey1.hashCode(), participantKey2.hashCode());
    }
}
