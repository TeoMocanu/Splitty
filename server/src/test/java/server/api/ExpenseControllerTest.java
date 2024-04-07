package server.api;

import commons.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.implementations.ExpenseServiceImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ExpenseControllerTest {
    public int nextInt;
    private TestExpenseRepository repo;

    private ExpenseServiceImplementation sut;

    @BeforeEach
    public void setup() {
        repo = new TestExpenseRepository();
        sut = new ExpenseServiceImplementation(repo);
    }

    @Test
    public void cannotAddNullExpense() {
        var actual = sut.addExpense(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void databaseIsUsed() {
        sut.addExpense(getExpense());
        //repo.calledMethods.contains("save");
    }

    private static Expense getExpense() {
        return new Expense();
    }
}
