package server.implementations;

import commons.Expense;
import commons.primaryKeys.ExpenseKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.ExpenseRepository;
import server.services.ExpenseService;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImplementation implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public ExpenseServiceImplementation(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getAllExpensesFromEvent(Long eventId) {
        return expenseRepository.findByEventId(eventId);
    }

    @Override
    public ResponseEntity<Expense> getExpenseById(ExpenseKey id) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);
        return expenseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Expense> addExpense(Expense expense) {
        if (expense == null) {
            return ResponseEntity.badRequest().build();
        }
        Expense added = expenseRepository.save(expense);
        return ResponseEntity.ok(added);
    }

    @Override
    public ResponseEntity<Expense> editExpense(ExpenseKey id, Expense expense) {
        if (expense == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Expense updatedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(updatedExpense);
    }

    @Override
    public ResponseEntity<Void> deleteExpense(ExpenseKey id) {
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Expense> findByEventIdAndPayerId(Long eid, Long pid) {
        return expenseRepository.findByEventIdAndPayerId(eid, pid);
    }

    public List<Expense> findByEventIdAndDebtorsId(Long eid, Long pid) {
        return expenseRepository.findByEventIdAndDebtorsId(eid, pid);
    }
}
