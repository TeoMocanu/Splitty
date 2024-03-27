package server.api;

import commons.Debt;
import commons.primaryKeys.DebtKey;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.DebtRepository;

import java.util.List;

@RestController
@RequestMapping("/api/debt")
public class DebtController {
    private final DebtRepository repo;
    private final EventController eventController;

    public DebtController(DebtRepository repo, EventController eventController) {
        this.repo = repo;
        this.eventController = eventController;
    }

    @GetMapping("/getByEventId/{id}")
    public ResponseEntity<List<Debt>> getAll(@PathVariable("id") long idEvent){
        if(idEvent <= 0 || eventController.getEventIdById(idEvent) == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Debt> addDebt(@RequestBody Debt debt){
        long idEvent = debt.getEventId();
        if(idEvent <= 0 || eventController.getEventIdById(idEvent) == null)
            return ResponseEntity.badRequest().build();
        Debt saved = repo.save(debt);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Debt>> getAllDebts() {
        List<Debt> debts = repo.findAll();
        if (debts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(debts);
    }

    @GetMapping("/getByIds/{eid}/{id}")
    public ResponseEntity<Debt> getDebtByIds(@PathVariable("eid") long eid, @PathVariable("id") long id) {
        DebtKey key = new DebtKey(eid, id);
        if (repo.findById(key).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repo.findById(key).get());
    }

    @DeleteMapping("/delete/{eid}/{id}")
    public ResponseEntity<Debt> deleteDebt(@PathVariable("eid") long eid, @PathVariable("id") long id) {
        DebtKey key = new DebtKey(eid, id);
        if (repo.findById(key).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        repo.deleteById(key);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{eid}/{id}")
    public ResponseEntity<Debt> updateDebt(@PathVariable("eid") long eid, @PathVariable("id") long id, @RequestBody Debt debt) {
        DebtKey key = new DebtKey(eid, id);
        if (repo.findById(key).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Debt updatedDebt = repo.save(debt);
        return ResponseEntity.ok(updatedDebt);
    }
}
