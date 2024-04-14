package server.services;

import commons.Expense;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExpenseService {

    List<Expense> getAllExpenses();

    List<Expense> getAllExpensesFromEvent(Long eventId);

    ResponseEntity<Expense> getExpenseById(Long eventId, Long id);

    ResponseEntity<Expense> addExpense(Expense expense);

    ResponseEntity<Expense> editExpense(Expense expense);

    ResponseEntity<Void> deleteExpense(Long eid, Long id);

    List<Expense> findByEventIdAndPayerId(Long eid, Long pid);

    List<Expense> findByEventIdAndDebtorsId(Long eid, Long pid);
}
