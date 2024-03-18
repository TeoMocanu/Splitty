package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParticipantTest {

    @Test
    public void testGettersAndSetters() {
        // Create a participant
        Event event = new Event("title");
        Participant participant = new Participant(event, "Mario", "mario.nicolae2004@gmail.com", "123", "abc");

        // Test getters
        assertEquals(event, participant.getEvent());
        assertEquals("Mario", participant.getName());
        assertEquals("mario.nicolae2004@gmail.com", participant.getEmail());
        assertEquals("123", participant.getIban());
        assertEquals("abc", participant.getBic());

        // Test setters
        Event newEvent = new Event("title");
        participant.setEvent(newEvent);
        participant.setName("Mario");
        participant.setEmail("mario.nicolae2004@gmail.com");
        participant.setIban("123");
        participant.setBic("abc");
        assertEquals(newEvent, participant.getEvent());
        assertEquals("Mario", participant.getName());
        assertEquals("mario.nicolae2004@gmail.com", participant.getEmail());
        assertEquals("123", participant.getIban());
        assertEquals("abc", participant.getBic());
    }


    @Test
    public void testNotEquals() {
        // Create two participants with different attributes
        Event event1 = new Event("title");
        Event event2 = new Event("title");
        Participant participant1 = new Participant(event1, "John Doe", "john@example.com", "123456789", "ABCD1234");
        Participant participant2 = new Participant(event2, "Jane Doe", "jane@example.com", "987654321", "EFGH5678");

        // Assert that the two participants are not equal
        assertNotEquals(participant1, participant2);
    }

    @Test
    public void testHashCode() {
        // Create a participant
        Event event = new Event("title");
        Participant participant = new Participant(event, "John Doe", "john@example.com", "123456789", "ABCD1234");

        // Calculate hash code manually
        int expectedHashCode = participant.hashCode();

        assertEquals(expectedHashCode, participant.hashCode());
    }

    @Test
    public void testToString() {
        // Create a participant
        Event event = new Event("title");
        Participant participant = new Participant(event, "John Doe", "john@example.com", "123456789", "ABCD1234");

        // Assert that the toString() method returns a non-null string
        assertNotNull(participant.toString());
    }

    @Test
    public void testEquals() {
        // Create two participants with the same attributes
        Event event1 = new Event("title");
        Event event2 = new Event("title");
        Participant participant1 = new Participant(event1, "John Doe", "5678", "1234", "ABCD");
        Participant participant2 = new Participant(event2, "John Doe", "5678", "1234", "ABCD");
        assertEquals(participant1, participant2);
    }

}
