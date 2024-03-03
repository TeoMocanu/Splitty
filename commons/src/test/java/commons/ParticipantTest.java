//package commons;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ParticipantTest {
//
//    private Participant participant;
//
//    @Test
//    void testDefaultConstructor() {
//        Participant participant = new Participant();
//        assertNotNull(participant);
//    }
//
//    @Test
//    void testParameterizedConstructor() {
//        participant = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        assertEquals("John Doe", participant.getName());
//        assertEquals("john.doe@example.com", participant.getEmail());
//        assertEquals("DE89 3704 0044 0532 0130 00", participant.getIban());
//    }
//
//    @Test
//    void getId() {
//        Participant participant = new Participant("Alice", "alice@example.com", "DE02500105170648489890");
//        participant.setId(1L);
//        assertEquals(1L, participant.getId(), "The ID should be correctly retrieved.");
//    }
//
//    @Test
//    void setId() {
//        Participant participant = new Participant();
//        participant.setId(2L);
//        assertEquals(2L, participant.getId(), "The ID should be correctly set.");
//    }
//
//    @Test
//    void getName() {
//        Participant participant = new Participant("Bob", "bob@example.com", "GB29NWBK60161331926819");
//        assertEquals("Bob", participant.getName(), "The name should be correctly retrieved.");
//    }
//
//    @Test
//    void setName() {
//        Participant participant = new Participant();
//        participant.setName("Charlie");
//        assertEquals("Charlie", participant.getName(), "The name should be correctly set.");
//    }
//
//    @Test
//    void getEmail() {
//        Participant participant = new Participant("Diana", "diana@example.com", "FR1420041010050500013M02606");
//        assertEquals("diana@example.com", participant.getEmail(), "The email should be correctly retrieved.");
//    }
//
//    @Test
//    void setEmail() {
//        Participant participant = new Participant();
//        participant.setEmail("eve@example.com");
//        assertEquals("eve@example.com", participant.getEmail(), "The email should be correctly set.");
//    }
//
//    @Test
//    void getIban() {
//        Participant participant = new Participant("Frank", "frank@example.com", "DE89370400440532013000");
//        assertEquals("DE89370400440532013000", participant.getIban(), "The IBAN should be correctly retrieved.");
//    }
//
//    @Test
//    void setIban() {
//        Participant participant = new Participant();
//        participant.setIban("NL91ABNA0417164300");
//        assertEquals("NL91ABNA0417164300", participant.getIban(), "The IBAN should be correctly set.");
//    }
//
//    @Test
//    void testEqualsReflexivity() {
//        participant = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        assertTrue(participant.equals(participant));
//    }
//
//    @Test
//    void testEqualsSymmetry() {
//        participant = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        Participant other = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        assertTrue(participant.equals(other) && other.equals(participant));
//    }
//
//    @Test
//    void testEqualsTransitivity() {
//        Participant p1 = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        Participant p2 = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        Participant p3 = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        assertTrue(p1.equals(p2) && p2.equals(p3) && p1.equals(p3));
//    }
//
//    @Test
//    void testEqualsConsistency() {
//        participant = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        Participant other = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        assertEquals(participant, other);
//        other.setId(2L);
//        assertNotEquals(participant, other);
//    }
//
//    @Test
//    void testEqualsNull() {
//        participant = new Participant("John Doe", "john.doe@example.com", "DE89 3704 0044 0532 0130 00");
//        assertNotEquals(null, participant);
//    }
//
//    @Test
//    void testHashCode() {
//        Participant participant1 = new Participant("Ian", "ian@example.com", "CH9300762011623852957");
//        Participant participant2 = new Participant("Ian", "ian@example.com", "CH9300762011623852957");
//
//        assertEquals(participant1.hashCode(), participant2.hashCode(), "Two participants with the same properties should have the same hash code.");
//    }
//}
