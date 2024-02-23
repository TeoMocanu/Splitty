package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DebtTest {

    @Test
    void getDebtor() {
        Debt debt = new Debt("Matei", "Andrei", 200.00);
        assertEquals(debt.getDebtor(),"Matei");
    }

    @Test
    void setDebtor() {
        Debt debt = new Debt("Matei", "Andrei", 200.00);
        debt.setDebtor("Mario");
        assertEquals(debt.getDebtor(),"Mario");
    }

    @Test
    void getCreditor() {
        Debt debt = new Debt("Matei", "Andrei", 200.00);
        assertEquals(debt.getCreditor(),"Andrei");
    }

    @Test
    void setCreditor() {
        Debt debt = new Debt("Matei", "Andrei", 200.00);
        debt.setCreditor("Mario");
        assertEquals(debt.getCreditor(),"Mario");
    }

    @Test
    void getAmount() {
        Debt debt = new Debt("Matei", "Andrei", 200.00);
        assertEquals(debt.getAmount(),200);
    }

    @Test
    void setAmount() {
        Debt debt = new Debt("Matei", "Andrei", 200.00);
        debt.setAmount(100.00);
        assertEquals(debt.getAmount(),100.00);
    }

    @Test
    void testEquals() {
        Debt debt1 = new Debt("Matei", "Andrei", 200.00);
        Debt debt2 = new Debt("Matei", "Andrei", 200.00);
        assertTrue(debt1.equals(debt2));
    }

    @Test
    void testHashCode() {
        Debt debt1 = new Debt("Matei", "Andrei", 200.00);
        Debt debt2 = new Debt("Matei", "Andrei", 200.00);
        assertEquals(debt1.hashCode(),debt2.hashCode());
    }

    @Test
    void testToString() {
        Debt debt = new Debt("Matei", "Andrei", 200.00);
        assertEquals(debt.toString(),"Debt{debtor='Matei', creditor='Andrei', amount=200.0}");
    }
}