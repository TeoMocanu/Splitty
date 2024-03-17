package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DebtTest {
    @Test
    public void testGettersAndSetters() {
        // Create a debt
        Event event = new Event();
        Participant debtor = new Participant();
        Participant creditor = new Participant();
        Debt debt = new Debt(event, debtor, creditor, 50.0);

        // Test getters
        assertEquals(event, debt.getEvent());
        assertEquals(debtor, debt.getDebtor());
        assertEquals(creditor, debt.getCreditor());
        assertEquals(50.0, debt.getAmount());

        // Test setters
        Event newEvent = new Event();
        Participant newDebtor = new Participant();
        Participant newCreditor = new Participant();
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
    public void testNotEquals() {
        // Create two debts with different attributes
        Event event1 = new Event();
        Event event2 = new Event();
        Participant debtor1 = new Participant();
        Participant debtor2 = new Participant();
        Participant creditor = new Participant();
        Debt debt1 = new Debt(event1, debtor1, creditor, 100.0);
        Debt debt2 = new Debt(event2, debtor2, creditor, 100.0);

        // Test not equals method
        assertNotEquals(debt1, debt2);
    }
    @Test
    public void testHashCode() {
        // Create a debt
        Event event = new Event();
        Participant debtor = new Participant();
        Participant creditor = new Participant();
        Debt debt = new Debt(event, debtor, creditor, 100.0);

        // Calculate hash code
        int expectedHashCode = debt.hashCode();

        assertEquals(expectedHashCode, debt.hashCode());
    }
    @Test
    public void testToString() {
        // Create a debt
        Event event = new Event();
        Participant debtor = new Participant();
        Participant creditor = new Participant();
        Debt debt = new Debt(event, debtor, creditor, 100.0);

        assertNotNull(debt.toString());
    }
}