package commons;

import commons.primaryKeys.DebtKey;
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
    public void testConstructor() {
        assertNotNull(debt);
        assertEquals(event.getId(), debt.getEventId());
        assertEquals(event, debt.getEvent());
        assertEquals(debtor, debt.getDebtor());
        assertEquals(creditor, debt.getCreditor());
        assertEquals(50.0, debt.getAmount());
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
    @Test
    public void testSetEvent() {
        Event newEvent = new Event("newTitle");
        debt.setEvent(newEvent);
        assertEquals(newEvent, debt.getEvent());
    }
    @Test
    public void testSetId() {
        debt.setId(100L);
        assertEquals(100L, debt.getId());
    }
    @Test
    public void testEqualityAndInequality() {
        // Test equality
        assertEquals(debt, new Debt(event, debtor, creditor, 50.0));

        // Test inequality
        assertNotEquals(debt, new Debt(event, debtor, creditor, 100.0));
        assertNotEquals(debt, null);
        assertNotEquals(debt, new Object());
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
    @Test
    public void testGetDebtKey() {
        assertEquals(new DebtKey(event.getId(), debt.getId()), debt.getDebtKey());
    }
}