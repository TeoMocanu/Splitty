//package server.api;
//
//import commons.Expense;
//import commons.primaryKeys.ExpenseKey;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import server.database.ExpenseRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/expenses")
//public class ExpenseController {
//
//    @Autowired
//    private final ExpenseRepository expenseRepository;
//
//    public ExpenseController(ExpenseRepository expenseRepository) {
//        this.expenseRepository = expenseRepository;
//    }
//
//    @GetMapping("/getAll")
//    public List<Expense> getAllExpenses() {
//        return expenseRepository.findAll();
//    }
//
//    @GetMapping("/getAllExpensesFromEvent/{eid}")
//    public List<Expense> getAllExpensesFromEvent(@PathVariable("eid") Long eid) {
//        return expenseRepository.findByEventId(eid);
//    }
//
//    @GetMapping("/getById/{id}")
//    public ResponseEntity<Expense> getExpenseById(@PathVariable("id") ExpenseKey id) {
//        Optional<Expense> expenseOptional = expenseRepository.findById(id);
//        return expenseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    /*@GetMapping("/getAllExpensesFromEvent/{eid}")
//    public List<Expense> getAllExpensesFromEvent(@PathVariable("eid") Long eid) {
//        List<Expense> ret = new ArrayList<>();
//        for(Expense e : expenseRepository.findAll()){
//            if(e.getEvent().getId() == eid){
//                ret.add(e);
//            }
//        }
//        return ret;
//    }*/
//
//    @PostMapping("/addExpense")
//    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
//        if (expense == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        Expense added = expenseRepository.save(expense);
//        return ResponseEntity.ok(added);
//    }
//
//    @PostMapping("/editExpense/{id}")
//    public ResponseEntity<Expense> updateExpense(@PathVariable("id") ExpenseKey id, @RequestBody Expense expense) {
//        if (expense == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        if (!expenseRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        Expense updatedExpense = expenseRepository.save(expense);
//        return ResponseEntity.ok(updatedExpense);
//    }
//
//    @DeleteMapping("/deleteExpense/{id}")
//    public ResponseEntity<Void> deleteExpense(@PathVariable("id") ExpenseKey id) {
//        if (!expenseRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        expenseRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}

package server.api;

import commons.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.implementations.ExpenseServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private final ExpenseServiceImplementation service;

    public ExpenseController(ExpenseServiceImplementation service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<Expense> getAllExpenses() {
        return service.getAllExpenses();
    }

    @GetMapping("/getAllExpensesFromEvent/{eid}")
    public List<Expense> getAllExpensesFromEvent(@PathVariable("eid") Long eid) {
        return service.getAllExpensesFromEvent(eid);
    }

    @GetMapping("/getById/{eid}/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable("eid") Long eid, @PathVariable("id") Long id) {
        //Optional<Expense> expenseOptional = expenseRepository.findById(id);
        //return expenseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        return service.getExpenseById(eid, id);
    }

    @GetMapping("/getAllExpensesFromEventPaidBy/{eid}/{pid}")
    public List<Expense> getAllExpensesFromEventPaidBy(@PathVariable("eid") Long eid, @PathVariable("pid") Long pid) {
        return service.findByEventIdAndPayerId(eid, pid);
    }

    @GetMapping("/getAllExpensesFromEventOwedBy/{eid}/{pid}")
    public List<Expense> getAllExpensesFromEventOwedBy(@PathVariable("eid") Long eid, @PathVariable("pid") Long pid) {
        return service.findByEventIdAndDebtorsId(eid, pid);
    }

    @PostMapping("/addExpense")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        return service.addExpense(expense);
    }

    @PutMapping("/editExpense")
    public ResponseEntity<Expense> editExpense(@RequestBody Expense expense) {
        System.out.println("lets GOOOOO");
        return service.editExpense(expense);
    }

    @DeleteMapping("/deleteExpense/{eid}/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("eid") Long eid, @PathVariable("id") Long id) {
        return service.deleteExpense(eid, id);
    }
}
