package server.api;

import commons.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ExpenseRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/getAll")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable("id") Long id) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);
        return expenseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addExpense")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        if (expense == null) {
            return ResponseEntity.badRequest().build();
        }
        Expense added = expenseRepository.save(expense);
        return ResponseEntity.ok(added);
    }

    @PutMapping("/editExpense/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("id") Long id, @RequestBody Expense expense) {
        if (expense == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Expense updatedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/deleteExpense/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("id") Long id) {
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
