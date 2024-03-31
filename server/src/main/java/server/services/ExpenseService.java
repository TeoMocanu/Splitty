package server.services;

import commons.Expense;
import commons.primaryKeys.ExpenseKey;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExpenseService {

    List<Expense> getAllExpenses();

    List<Expense> getAllExpensesFromEvent(Long eventId);

    ResponseEntity<Expense> getExpenseById(ExpenseKey id);

    ResponseEntity<Expense> addExpense(Expense expense);

    ResponseEntity<Expense> editExpense(ExpenseKey id, Expense expense);

    ResponseEntity<Void> deleteExpense(ExpenseKey id);
}
