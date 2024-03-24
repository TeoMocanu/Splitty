package server.api;

import commons.Expense;
import commons.primaryKeys.ExpenseKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ExpenseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/getAll")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @GetMapping("/getAllExpensesFromEvent/{eid}")
    public List<Expense> getAllExpensesFromEvent(@PathVariable("eid") Long eid) {
        return expenseRepository.findByEventId(eid);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable("id") ExpenseKey id) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);
        return expenseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*@GetMapping("/getAllExpensesFromEvent/{eid}")
    public List<Expense> getAllExpensesFromEvent(@PathVariable("eid") Long eid) {
        List<Expense> ret = new ArrayList<>();
        for(Expense e : expenseRepository.findAll()){
            if(e.getEvent().getId() == eid){
                ret.add(e);
            }
        }
        return ret;
    }*/

    @PostMapping("/addExpense")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        if (expense == null) {
            return ResponseEntity.badRequest().build();
        }
        Expense added = expenseRepository.save(expense);
        return ResponseEntity.ok(added);
    }

    @PutMapping("/editExpense/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("id") ExpenseKey id, @RequestBody Expense expense) {
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
    public ResponseEntity<Void> deleteExpense(@PathVariable("id") ExpenseKey id) {
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
