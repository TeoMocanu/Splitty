package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DebtNewTest {

    private Debt debt;
    private Event event;
    private Participant debtor;
    private Participant creditor;

    @BeforeEach
    void setUp() {
        event = mock(Event.class);
        when(event.getId()).thenReturn(1L);

        debtor = mock(Participant.class);
        when(debtor.getId()).thenReturn(2L);

        creditor = mock(Participant.class);
        when(creditor.getId()).thenReturn(3L);

        debt = new Debt(event, debtor, creditor, 100.0);
    }

    @Test
    void testConstructor() {
        assertAll(
                () -> assertEquals(1L, debt.getEventId()),
                () -> assertEquals(event, debt.getEvent()),
                () -> assertEquals(debtor, debt.getDebtor()),
                () -> assertEquals(creditor, debt.getCreditor()),
                () -> assertEquals(100.0, debt.getAmount())
        );
    }

    @Test
    void testSetAndGetDebtor() {
        Participant newDebtor = mock(Participant.class);
        debt.setDebtor(newDebtor);
        assertEquals(newDebtor, debt.getDebtor());
    }

    @Test
    void testSetAndGetCreditor() {
        Participant newCreditor = mock(Participant.class);
        debt.setCreditor(newCreditor);
        assertEquals(newCreditor, debt.getCreditor());
    }

    @Test
    void testSetAndGetAmount() {
        debt.setAmount(200.0);
        assertEquals(200.0, debt.getAmount());
    }

    @Test
    void testAddAmount() {
        debt.addAmount(50.0);
        assertEquals(150.0, debt.getAmount());
    }

    @Test
    void testEqualsAndHashcode() {
        Debt sameDebt = new Debt(event, debtor, creditor, 100.0);
        Debt differentDebt = new Debt(event, debtor, creditor, 200.0);

        assertAll("Testing equals and hashCode",
                () -> assertEquals(debt, sameDebt),
                () -> assertNotEquals(debt, differentDebt),
                () -> assertEquals(debt.hashCode(), sameDebt.hashCode()),
                () -> assertNotEquals(debt.hashCode(), differentDebt.hashCode())
        );
    }

    @Test
    void testToString() {
        assertNotNull(debt.toString());
    }
}
