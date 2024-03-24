package server.api;

import commons.Debt;
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

    @GetMapping("/event/{id}")
    public ResponseEntity<List<Debt>> getAll(@PathVariable("id") long idEvent){
        if(idEvent <= 0 || eventController.getEventIdById(idEvent) == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping("/event/{id}")
    public ResponseEntity<Debt> addDebt(@PathVariable("id") long idEvent, @RequestBody Debt debt){
        if(idEvent <= 0 || eventController.getEventIdById(idEvent) == null)
            return ResponseEntity.badRequest().build();
        Debt saved = repo.save(debt);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/getAll")
    public List<Debt> getAllDebts() {
        return repo.findAll();
    }
}
