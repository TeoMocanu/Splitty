package server.api;

import commons.Event;
import commons.Expense;
import commons.Participant;
import commons.primaryKeys.ExpenseKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.implementations.ExpenseServiceImplementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {

    @Mock
    private ExpenseServiceImplementation service;

    @InjectMocks
    private ExpenseController expenseController;

    private Expense expense1;
    private Expense expense2;
    private Event event1;
    private Event event2;
    private LocalDate date1;
    private LocalDate date2;
    private Participant participant1;
    private Participant participant2;

    private List<Expense> expenseList;
    private List<Participant> participantList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        event1 = new Event("Mario");
        event2 = new Event("Sebi");
        date1 = LocalDate.of(2024,4,5);
        date2 = LocalDate.of(2024,4,3);
        participant1 = new Participant(event1,"Mario","mario@gmail.com","123456789","987654321");
        participant2 = new Participant(event2,"Sebi","sebi@gmail.com","123456789","987654321");
        participantList.add(participant1);
        participantList.add(participant2);

        expense1 = new Expense(event1,date1,participant1,participantList,"Shopping",120,"euro");
        expense2 = new Expense(event2,date2,participant2,participantList,"Cinema",130,"dollar");
        expenseList = Arrays.asList(expense1, expense2);

        service = mock(ExpenseServiceImplementation.class);
        expenseController = new ExpenseController(service);
    }

    @Test
    void getAllExpensesTest() {
        when(service.getAllExpenses()).thenReturn(expenseList);
        List<Expense> results = expenseController.getAllExpenses();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(expenseList, results);
        verify(service).getAllExpenses();
    }

    @Test
    void getAllExpensesFromEventTest() {
        Long eventId = 1L;
        when(service.getAllExpensesFromEvent(eventId)).thenReturn(expenseList);
        List<Expense> results = expenseController.getAllExpensesFromEvent(eventId);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(expenseList, results);
        verify(service).getAllExpensesFromEvent(eventId);
    }


    @Test
    void getExpenseByIdTest() {
        long eventId = 1L;
        long expenseId = 100L;
        ExpenseKey key = new ExpenseKey(eventId, expenseId);

        when(service.getExpenseById(eventId, expenseId)).thenReturn(ResponseEntity.ok(expense1));
        ResponseEntity<Expense> response = expenseController.getExpenseById(eventId, expenseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expense1, response.getBody());
        verify(service).getExpenseById(eventId, expenseId);
    }

    @Test
    void addExpenseTest() {
        when(service.addExpense(expense1)).thenReturn(ResponseEntity.ok(expense1));
        ResponseEntity<Expense> response = expenseController.addExpense(expense1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expense1, response.getBody());
        verify(service).addExpense(expense1);
    }

    @Test
    void editExpenseTest() {
        long eventId = 1L;
        long expenseId = 100L;
        ExpenseKey key = new ExpenseKey(eventId, expenseId);

        when(service.editExpense(key, expense1)).thenReturn(ResponseEntity.ok(expense1));
        ResponseEntity<Expense> response = expenseController.editExpense(key, expense1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expense1, response.getBody());
        verify(service).editExpense(key, expense1);
    }

    @Test
    void editExpenseTest1() {
        ExpenseKey key = new ExpenseKey(1L, 100L);

        event1 = new Event("Mario");
        date1 = LocalDate.of(2024,4,5);
        participant1 = new Participant(event1,"Mario","mario@gmail.com","123456789","987654321");
        participantList.add(participant1);

        expense1 = new Expense(event1,date1,participant1,participantList,"Shopping",120,"euro");
        Expense editedExpense = new Expense();
        when(service.editExpense(eq(key), any(Expense.class))).thenReturn(ResponseEntity.ok(editedExpense));
        ResponseEntity<Expense> response = expenseController.editExpense(key, editedExpense);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(editedExpense, response.getBody());
        verify(service).editExpense(eq(key), any(Expense.class));
    }

    @Test
    void deleteExpenseTest() {
        long eventId = 1L;
        long expenseId = 100L;
        ExpenseKey key = new ExpenseKey(eventId, expenseId);

        when(service.deleteExpense(eventId, expenseId)).thenReturn(ResponseEntity.noContent().build());
        ResponseEntity<Void> response = expenseController.deleteExpense(eventId, expenseId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service).deleteExpense(eventId, expenseId);
    }

    @Test
    void getAllExpensesFromEventOwedByTest() {
        Long eventId = 1L;
        Long debtorId = 15L;
        when(service.findByEventIdAndDebtorsId(eventId, debtorId)).thenReturn(expenseList);
        List<Expense> results = expenseController.getAllExpensesFromEventOwedBy(eventId, debtorId);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(expenseList, results);
        verify(service).findByEventIdAndDebtorsId(eventId, debtorId);
    }

    @Test
    void getAllExpensesFromEventPaidByTest() {
        Long eventId = 1L;
        Long payerId = 10L;
        when(service.findByEventIdAndPayerId(eventId, payerId)).thenReturn(expenseList);
        List<Expense> results = expenseController.getAllExpensesFromEventPaidBy(eventId, payerId);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(expenseList, results);
        verify(service).findByEventIdAndPayerId(eventId, payerId);
    }
}
