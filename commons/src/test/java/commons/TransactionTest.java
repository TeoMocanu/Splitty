package commons;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
/*
    @Test
    void getPayer() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        assertEquals(transaction.getPayer(),"Mario");
    }

    @Test
    void setPayer() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        transaction.setPayer("Alex");
        assertEquals(transaction.getPayer(),"Alex");
    }

    @Test
    void getPayee() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        assertEquals(transaction.getPayee(),"Mihai");
    }

    @Test
    void setPayee() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        transaction.setPayee("Alex");
        assertEquals(transaction.getPayee(),"Alex");
    }

    @Test
    void getAmount() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        assertEquals(transaction.getAmount(),150);
    }

    @Test
    void setAmount() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        transaction.setAmount(200);
        assertEquals(transaction.getAmount(),200);
    }

    @Test
    void getCurrency() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        assertEquals(transaction.getCurrency(),"$");
    }

    @Test
    void setCurrency() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        transaction.setCurrency("€");
        assertEquals(transaction.getCurrency(),"€");
    }

    @Test
    void getDescription() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        assertEquals(transaction.getDescription(),"money for shopping");
    }

    @Test
    void setDescription() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        transaction.setDescription("money for pizza");
        assertEquals(transaction.getDescription(),"money for pizza");
    }

    @Test
    void getTimestamp() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        assertEquals(transaction.getTimestamp(),specificTimestamp);
    }

    @Test
    void setTimestamp() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        LocalDateTime specificTimestamp1 = LocalDateTime.of(2023, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        transaction.setTimestamp(specificTimestamp1);
        assertEquals(transaction.getTimestamp(),specificTimestamp1);
    }

    @Test
    void testEquals() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        LocalDateTime specificTimestamp1 = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction1 = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        assertTrue(transaction1.equals(transaction));
    }

    @Test
    void testHashCode() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        LocalDateTime specificTimestamp1 = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction1 = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        assertEquals(transaction.hashCode(),transaction1.hashCode());
    }

    @Test
    void testToString() {
        LocalDateTime specificTimestamp = LocalDateTime.of(2024, 2, 23, 20, 04);
        Transaction transaction = new Transaction("Mario", "Mihai", 150, "$", "money for shopping", specificTimestamp);
        assertEquals(transaction.toString(),"Transaction{payer='Mario', payee='Mihai', amount=150.0, currency='$', description='money for shopping', timestamp=2024-02-23T20:04}");
    }

*/
}