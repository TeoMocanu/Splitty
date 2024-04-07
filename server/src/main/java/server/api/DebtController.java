package server.api;

import commons.Debt;
import commons.primaryKeys.DebtKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.DebtRepository;
import server.implementations.EventServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/api/debts")
public class DebtController {
    private final DebtRepository repo;
    private final EventServiceImplementation eventServiceImplementation;


    @Autowired
    public DebtController(DebtRepository repo, EventServiceImplementation eventServiceImplementation) {
        this.repo = repo;
        this.eventServiceImplementation = eventServiceImplementation;
    }


    @GetMapping("/getByEventId/{id}")
    public ResponseEntity<List<Debt>> getAllFromEvent(@PathVariable("id") long idEvent){
        if(idEvent <= 0 || eventServiceImplementation.getEventById(idEvent) == null)
            return ResponseEntity.badRequest().build();
        List<Debt> debts = repo.findAllByEventId(idEvent);
        if (debts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(debts);
    }

    @PostMapping("/add")
    public ResponseEntity<Debt> addDebt(@RequestBody Debt debt){
        long idEvent = debt.getEventId();
        if(idEvent <= 0 || eventServiceImplementation.getEventById(idEvent) == null)
            return ResponseEntity.badRequest().build();
        Debt saved = repo.save(debt);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Debt>> getAll() {
        List<Debt> debts = repo.findAll();
        if (debts.isEmpty()) {
            return ResponseEntity.noContent().build();
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

    @PostMapping("/edit")
    public ResponseEntity<Debt> editDebt(@RequestBody Debt debt) {
        DebtKey key = debt.getDebtKey();
        //Debt debttt = repo.findById(key).orElse(null);
        if (repo.findById(key).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Debt updatedDebt = repo.save(debt);
        return ResponseEntity.ok(updatedDebt);
    }
}
