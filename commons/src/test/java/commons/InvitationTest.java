package commons;

import commons.emails.Invitation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvitationTest {

    @Test
    public void testParameterizedConstructor() {
        String email = "mario@gmail.com";
        String event = "Shopping";
        Long code = 100L;

        Invitation invitation = new Invitation(email, event, code);

        assertAll("Constructor should set all properties",
                () -> assertEquals(email, invitation.getEmail()),
                () -> assertEquals(event, invitation.getEvent()),
                () -> assertEquals(code, invitation.getCode())
        );
    }

    @Test
    public void testDefaultConstructor() {
        Invitation invitation = new Invitation();

        assertAll("Default constructor - properties null",
                () -> assertNull(invitation.getEmail()),
                () -> assertNull(invitation.getEvent()),
                () -> assertNull(invitation.getCode())
        );
    }

    @Test
    public void testGetEmail() {
        String email = "mario@gmail.com";
        Invitation invitation = new Invitation(email, "Shopping", 100L);

        assertEquals(email, invitation.getEmail(), "Correct Email");
    }

    @Test
    public void testGetEvent() {
        String event = "Shopping";
        Invitation invitation = new Invitation("mario@gmail.com", event, 100L);

        assertEquals(event, invitation.getEvent(), "Correct Event");
    }

    @Test
    public void testGetCode() {
        Long code = 100L;
        Invitation invitation = new Invitation("mario@gmail.com", "Shopping", code);

        assertEquals(code, invitation.getCode(), "Correct code");
    }
}
