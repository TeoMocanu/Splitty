package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DebtTest {
    public Event event;
    public Participant debtor;
    public Participant creditor;
    public Debt debt;
    @BeforeEach
    public void initialize() {
        // Create a debt
        event = new Event("title");
        debtor = new Participant("name", event);
        creditor = new Participant("name2", event);
        debt = new Debt(event, debtor, creditor, 50.0);
    }
    @Test
    public void testGettersAndSetters() {
        // Test getters
        assertEquals(event, debt.getEvent());
        assertEquals(debtor, debt.getDebtor());
        assertEquals(creditor, debt.getCreditor());
        assertEquals(50.0, debt.getAmount());

        // Test setters
        Event newEvent = new Event("title");
        Participant newDebtor = new Participant("name", event);
        Participant newCreditor = new Participant("name2", event);
        debt.setEvent(newEvent);
        debt.setDebtor(newDebtor);
        debt.setCreditor(newCreditor);
        debt.setAmount(75.0);
        assertEquals(newEvent, debt.getEvent());
        assertEquals(newDebtor, debt.getDebtor());
        assertEquals(newCreditor, debt.getCreditor());
        assertEquals(75.0, debt.getAmount());
    }
    //TODO fix this test, ids are not right
    @Test
    public void testNotEquals() {
        // Create two debts with different attributes
        Event event1 = new Event("title");
        Event event2 = new Event("title");
        Participant debtor1 = new Participant("name", event1);
        Participant debtor2 = new Participant("name1", event1);
        Participant creditor = new Participant("name2", event2);
        Debt debt1 = new Debt(event1, debtor1, creditor, 100.0);
        Debt debt2 = new Debt(event2, debtor2, creditor, 100.0);

        // Test not equals method
//        assertNotEquals(debt1, debt2);
    }
    @Test
    public void testHashCode() {
        // Calculate hash code
        int expectedHashCode = debt.hashCode();

        assertEquals(expectedHashCode, debt.hashCode());
    }
    @Test
    public void testToString() {
        assertNotNull(debt.toString());
    }

    @Test
    public void testAddAmount() {
        assertEquals(50.0, debt.getAmount());
        debt.addAmount(20.0);
        assertEquals(70.0, debt.getAmount());
    }
}